package com.fligneul.apsystems.auth;

import com.fligneul.apsystems.exception.ApSystemsException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class ApSystemsAuthInterceptor implements Interceptor {
    private final String appId;
    private final String appSecret;

    public ApSystemsAuthInterceptor(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = UUID.randomUUID().toString().replace("-", "");
        String signatureMethod = "HmacSHA256";
        
        String requestPath = originalRequest.url().encodedPath();
        String lastSegment = requestPath.substring(requestPath.lastIndexOf('/') + 1);
        String httpMethod = originalRequest.method().toUpperCase();
        
        // stringToSign = X-CA-Timestamp + "/" + X-CA-Nonce + "/" + X-CA-AppId + "/" + RequestPath + "/" + HTTPMethod + "/" + X-CA-Signature-Method
        // Manual 2.2.2 says: stringToSign = X-CA-Timestamp + "/" + X-CA-Nonce + "/" + X-CA-AppId + "/" + RequestPath + "/" + HTTPMethod + "/" + X-CA-Signature-Method
        // Note: RequestPath is "The last segment of the path"
        String stringToSign = timestamp + "/" + nonce + "/" + appId + "/" + lastSegment + "/" + httpMethod + "/" + signatureMethod;
        
        String signature = calculateSignature(stringToSign, appSecret);
        
        Request authenticatedRequest = originalRequest.newBuilder()
                .header("X-CA-AppId", appId)
                .header("X-CA-Timestamp", timestamp)
                .header("X-CA-Nonce", nonce)
                .header("X-CA-Signature-Method", signatureMethod)
                .header("X-CA-Signature", signature)
                .build();
        
        return chain.proceed(authenticatedRequest);
    }

    private String calculateSignature(String stringToSign, String secret) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] appSecretBytes = secret.getBytes(StandardCharsets.UTF_8);
            hmacSha256.init(new SecretKeySpec(appSecretBytes, "HmacSHA256"));
            byte[] hash = hmacSha256.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new ApSystemsException("Failed to calculate signature", e);
        }
    }
}
