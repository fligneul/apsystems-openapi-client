package com.fligneul.apsystems;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApSystemsClientBuilderTest {

    @Test
    void testMissingAppIdThrowsException() {
        ApSystemsClientBuilder builder = new ApSystemsClientBuilder().appSecret("secret");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testMissingAppSecretThrowsException() {
        ApSystemsClientBuilder builder = new ApSystemsClientBuilder().appId("id");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testSuccessfulBuild() {
        ApSystemsClient client = new ApSystemsClientBuilder()
                .appId("id")
                .appSecret("secret")
                .build();
        assertNotNull(client);
    }

    @Test
    void testAsyncBuild() {
        ApSystemsAsyncClient client = new ApSystemsClientBuilder()
                .appId("id")
                .appSecret("secret")
                .buildAsync();
        assertNotNull(client);
    }

    @Test
    void testCustomBaseUrl() {
        ApSystemsClientBuilder builder = new ApSystemsClientBuilder()
                .appId("id")
                .appSecret("secret")
                .baseUrl("https://custom.api.com");
        
        // Internal state is harder to test without reflection, but we check if build succeeds
        assertNotNull(builder.build());
    }
}
