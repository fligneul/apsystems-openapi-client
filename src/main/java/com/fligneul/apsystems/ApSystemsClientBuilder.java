package com.fligneul.apsystems;

import com.fligneul.apsystems.api.ApSystemsService;
import com.fligneul.apsystems.auth.ApSystemsAuthInterceptor;
import com.fligneul.apsystems.model.EnergyData;
import com.fligneul.apsystems.model.EnergyDataDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Builder class for creating an instance of {@link ApSystemsClient}.
 * 
 * <p>Example usage:</p>
 * <pre>
 * ApSystemsClient client = new ApSystemsClientBuilder()
 *     .appId("yourAppId")
 *     .appSecret("yourAppSecret")
 *     .logging(true)
 *     .build();
 * </pre>
 */
public class ApSystemsClientBuilder {
    private static final String DEFAULT_BASE_URL = "https://api.apsystemsema.com:9282/";
    
    private String appId;
    private String appSecret;
    private String baseUrl = DEFAULT_BASE_URL;
    private boolean loggingEnabled = false;
    private OkHttpClient.Builder customOkHttpClientBuilder;

    /**
     * Sets the unique identity id for the OpenAPI account.
     * @param appId The 32-bit App ID string.
     * @return This builder instance.
     */
    public ApSystemsClientBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * Sets the password to verify the valid OpenAPI account.
     * @param appSecret The 12-bit App Secret string.
     * @return This builder instance.
     */
    public ApSystemsClientBuilder appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    /**
     * Sets the base URL for the APsystems API.
     * Defaults to {@code https://api.apsystemsema.com:9282/}.
     * @param baseUrl The base URL string.
     * @return This builder instance.
     */
    public ApSystemsClientBuilder baseUrl(String baseUrl) {
        if (baseUrl != null && !baseUrl.isEmpty()) {
            if (!baseUrl.endsWith("/")) {
                baseUrl += "/";
            }
            this.baseUrl = baseUrl;
        }
        return this;
    }

    /**
     * Enables or disables HTTP request/response logging.
     * @param enabled True to enable logging, false otherwise.
     * @return This builder instance.
     */
    public ApSystemsClientBuilder logging(boolean enabled) {
        this.loggingEnabled = enabled;
        return this;
    }

    /**
     * Optional: provide a custom OkHttpClient.Builder for advanced configuration (timeouts, proxies, etc.)
     * @param builder The custom OkHttpClient.Builder.
     * @return This builder instance.
     */
    public ApSystemsClientBuilder okHttpClientBuilder(OkHttpClient.Builder builder) {
        this.customOkHttpClientBuilder = builder;
        return this;
    }

    /**
     * Builds and returns a synchronous instance of {@link ApSystemsClient}.
     * @return A configured ApSystemsClient instance.
     * @throws IllegalStateException If appId or appSecret is not set.
     */
    public ApSystemsClient build() {
        return new ApSystemsClient(buildService());
    }

    /**
     * Builds and returns an asynchronous instance of {@link ApSystemsAsyncClient}.
     * @return A configured ApSystemsAsyncClient instance.
     * @throws IllegalStateException If appId or appSecret is not set.
     */
    public ApSystemsAsyncClient buildAsync() {
        return new ApSystemsAsyncClient(buildService());
    }

    private ApSystemsService buildService() {
        if (appId == null || appId.isEmpty()) {
            throw new IllegalStateException("appId must be set");
        }
        if (appSecret == null || appSecret.isEmpty()) {
            throw new IllegalStateException("appSecret must be set");
        }

        OkHttpClient.Builder okHttpClientBuilder = customOkHttpClientBuilder != null 
                ? customOkHttpClientBuilder 
                : new OkHttpClient.Builder();
        
        // Always add the auth interceptor
        okHttpClientBuilder.addInterceptor(new ApSystemsAuthInterceptor(appId, appSecret));

        if (loggingEnabled) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EnergyData.class, new EnergyDataDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApSystemsService.class);
    }
}
