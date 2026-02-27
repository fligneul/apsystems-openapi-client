package com.fligneul.apsystems;

import com.fligneul.apsystems.exception.ApSystemsApiException;
import com.fligneul.apsystems.exception.ApSystemsException;
import com.fligneul.apsystems.model.*;
import com.fligneul.apsystems.model.enums.BatchEnergyLevel;
import com.fligneul.apsystems.model.enums.DeviceEnergyLevel;
import com.fligneul.apsystems.model.enums.SystemEnergyLevel;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest
public class ApSystemsAsyncClientTest {

    private ApSystemsAsyncClient client;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        client = new ApSystemsClientBuilder()
                .appId("testAppId")
                .appSecret("testAppSecret")
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .buildAsync();
    }

    @Test
    void testGetSystemDetailsAsync() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"sid\": \"TEST_SID\"}}")));

        CompletableFuture<SystemDetails> future = client.getSystemDetails("TEST_SID");
        SystemDetails details = future.get();

        assertNotNull(details);
        assertEquals("TEST_SID", details.getSid());
    }

    @Test
    void testGetInvertersAsync() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/inverters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": [{\"eid\": \"E1\"}]}")));

        CompletableFuture<List<EcuInverters>> future = client.getInverters("SID");
        List<EcuInverters> inverters = future.get();

        assertNotNull(inverters);
        assertEquals(1, inverters.size());
        assertEquals("E1", inverters.get(0).getEid());
    }

    @Test
    void testGetEnergyInPeriodAsync() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"values\": [1.2, 3.4]}}")));

        CompletableFuture<EnergyData> future = client.getEnergyInPeriod("SID", SystemEnergyLevel.DAILY, "2024-02");
        EnergyData data = future.get();

        assertNotNull(data);
        assertEquals(2, data.getValues().size());
        assertEquals(1.2, data.getValues().get(0));
    }

    @Test
    void testInverterBatchEnergyAsync() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/batch/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"values\": [5.5]}}")));

        CompletableFuture<EnergyData> future = client.getInverterBatchEnergy("SID", "EID", BatchEnergyLevel.ENERGY, "2024-02-26");
        EnergyData data = future.get();

        assertNotNull(data);
        assertEquals(5.5, data.getValues().get(0));
    }

    @Test
    void testAsyncApiErrorHandling() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 1001}"))); // No Data

        CompletableFuture<SystemDetails> future = client.getSystemDetails("SID");
        
        ExecutionException ex = assertThrows(ExecutionException.class, future::get);
        assertTrue(ex.getCause() instanceof ApSystemsApiException);
        ApSystemsApiException apiEx = (ApSystemsApiException) ex.getCause();
        assertEquals(1001, apiEx.getCode());
        assertEquals("No data.", apiEx.getMessage());
    }

    @Test
    void testAsyncHttpErrorHandling() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withStatus(500)));

        CompletableFuture<SystemDetails> future = client.getSystemDetails("SID");
        
        ExecutionException ex = assertThrows(ExecutionException.class, future::get);
        assertTrue(ex.getCause() instanceof ApSystemsException);
        assertFalse(ex.getCause() instanceof ApSystemsApiException);
    }

    @Test
    void testAsyncNetworkErrorHandling() {
        // Use an invalid port to simulate network failure
        ApSystemsAsyncClient faultyClient = new ApSystemsClientBuilder()
                .appId("id")
                .appSecret("secret")
                .baseUrl("http://localhost:1/") // Likely no server here
                .buildAsync();

        CompletableFuture<SystemDetails> future = faultyClient.getSystemDetails("SID");
        
        ExecutionException ex = assertThrows(ExecutionException.class, future::get);
        assertTrue(ex.getCause() instanceof ApSystemsException);
        String msg = ex.getCause().getMessage();
        assertTrue(msg.contains("Network error") || msg.contains("Request failed"), "Message was: " + msg);
    }
}
