package com.fligneul.apsystems.auth;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApSystemsAuthInterceptorTest {

    @Test
    public void testInterceptorHeaders() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(200));
        server.start();

        String appId = "myAppId";
        String appSecret = "myAppSecret";
        
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ApSystemsAuthInterceptor(appId, appSecret))
                .build();

        client.newCall(new Request.Builder().url(server.url("/api/test")).build()).execute();

        RecordedRequest request = server.takeRequest();
        assertEquals(appId, request.getHeader("X-CA-AppId"));
        assertNotNull(request.getHeader("X-CA-Timestamp"));
        assertNotNull(request.getHeader("X-CA-Nonce"));
        assertEquals("HmacSHA256", request.getHeader("X-CA-Signature-Method"));
        assertNotNull(request.getHeader("X-CA-Signature"));

        server.shutdown();
    }
}
