package com.fligneul.apsystems.api;

import com.fligneul.apsystems.ApSystemsAsyncClient;
import com.fligneul.apsystems.ApSystemsClient;
import com.fligneul.apsystems.ApSystemsClientBuilder;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest
public class ApSystemsWireMockTest {

    private ApSystemsClient client;
    private ApSystemsAsyncClient asyncClient;
    private static final String STUB_PATH = "src/test/resources/stubs/";

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        ApSystemsClientBuilder builder = new ApSystemsClientBuilder()
                .appId("testAppId")
                .appSecret("testAppSecret")
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl());
        
        client = builder.build();
        asyncClient = builder.buildAsync();
    }

    @Test
    void testSyncClientThrowsExceptionOnApiError() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 7002}"))); // Too Many Requests

        ApSystemsApiException ex = assertThrows(ApSystemsApiException.class, () -> {
            client.getSystemDetails("test");
        });
        
        assertEquals(7002, ex.getCode());
    }

    @Test
    void testSyncClientInverters() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/inverters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("inverters.json", "{\"code\": 0, \"data\": []}"))));

        List<EcuInverters> inverters = client.getInverters("test");
        assertNotNull(inverters);
    }

    @Test
    void testSyncClientEnergySummary() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("energy_summary.json", "{\"code\": 0, \"data\": {\"month\": \"1.0\"}}"))));

        EnergySummary summary = client.getEnergySummary("test");
        assertNotNull(summary);
    }

    @Test
    void testSyncClientEnergyInPeriod() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("energy_daily.json", "{\"code\": 0, \"data\": {\"values\": [1.0]}}"))));

        EnergyData data = client.getEnergyInPeriod("test", SystemEnergyLevel.DAILY, "2024-02");
        assertNotNull(data);
        assertFalse(data.getValues().isEmpty());
    }

    @Test
    void testSyncClientMeters() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/meters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": [\"METER1\"]}")));

        List<String> meters = client.getMeters("test");
        assertEquals(1, meters.size());
        assertEquals("METER1", meters.get(0));
    }

    @Test
    void testSyncClientEcuEnergySummary() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/ecu/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("ecu_summary.json", "{\"code\": 0, \"data\": {\"month\": \"1.0\"}}"))));

        EnergySummary summary = client.getEcuEnergySummary("sid", "eid");
        assertNotNull(summary);
    }

    @Test
    void testSyncClientEcuEnergyInPeriod() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/ecu/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("ecu_minutely.json", "{\"code\": 0, \"data\": {\"energy\": [1.0]}}"))));

        EnergyData data = client.getEcuEnergyInPeriod("sid", "eid", DeviceEnergyLevel.MINUTELY, "2024-02-26");
        assertNotNull(data);
    }

    @Test
    void testSyncClientInverterBatchEnergy() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/batch/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("inverter_batch_energy.json", "{\"code\": 0, \"data\": {}}"))));

        EnergyData data = client.getInverterBatchEnergy("sid", "eid", BatchEnergyLevel.ENERGY, "2024-02-26");
        assertNotNull(data);
    }

    @Test
    void testSyncClientMeterEnergySummary() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/meter/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"today\": {\"consumed\": \"1.0\"}}}")));

        MeterSummary summary = client.getMeterEnergySummary("sid", "eid");
        assertNotNull(summary);
        assertEquals("1.0", summary.getToday().getConsumed());
    }

    @Test
    void testSyncClientInverterEnergySummary() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("inverter_summary.json", "{\"code\": 0, \"data\": {\"m1\": \"1.0\"}}"))));

        InverterSummary summary = client.getInverterEnergySummary("sid", "uid");
        assertNotNull(summary);
    }

    @Test
    void testSyncClientStorageLatestPower() throws Exception {
        stubFor(get(urlMatching("/installer/api/v2/systems/.*/devices/storage/latest/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"soc\": \"90\"}}")));

        StorageData data = client.getStorageLatestPower("sid", "eid");
        assertNotNull(data);
        assertEquals(90.0, data.getSocAsDouble());
    }

    @Test
    void testSyncClientStorageEnergySummary() throws Exception {
        stubFor(get(urlMatching("/installer/api/v2/systems/.*/devices/storage/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"today\": {\"soc\": \"90\"}}}")));

        StorageSummary summary = client.getStorageEnergySummary("sid", "eid");
        assertNotNull(summary);
    }

    @Test
    void testAsyncClientReturnsCompletableFuture() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"sid\": \"TEST\"}}")));

        CompletableFuture<SystemDetails> future = asyncClient.getSystemDetails("test");
        SystemDetails details = future.get(); // wait for result
        
        assertNotNull(details);
        assertEquals("TEST", details.getSid());
    }

    @Test
    void testAsyncClientEnergyInPeriod() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"values\": [1.0]}}")));

        CompletableFuture<EnergyData> future = asyncClient.getEnergyInPeriod("test", SystemEnergyLevel.DAILY, "2024-02");
        EnergyData data = future.get();
        
        assertNotNull(data);
        assertFalse(data.getValues().isEmpty());
    }

    @Test
    void testAsyncClientHandlesError() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 7002}")));

        CompletableFuture<SystemDetails> future = asyncClient.getSystemDetails("test");
        
        ExecutionException ex = assertThrows(ExecutionException.class, future::get);
        assertTrue(ex.getCause() instanceof ApSystemsApiException);
        assertEquals(7002, ((ApSystemsApiException)ex.getCause()).getCode());
    }

    @Test
    void testHttpErrorThrowsException() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withStatus(500)));

        assertThrows(ApSystemsException.class, () -> {
            client.getSystemDetails("test");
        });
    }

    private String readStub(String fileName, String fallback) {
        try {
            return new String(Files.readAllBytes(Paths.get(STUB_PATH, fileName)));
        } catch (IOException e) {
            return fallback;
        }
    }
}
