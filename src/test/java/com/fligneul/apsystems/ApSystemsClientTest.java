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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest
public class ApSystemsClientTest {

    private ApSystemsClient client;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        client = new ApSystemsClientBuilder()
                .appId("testAppId")
                .appSecret("testAppSecret")
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .build();
    }

    @Test
    void testGetSystemDetails() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"sid\": \"SYNC_SID\"}}")));

        SystemDetails details = client.getSystemDetails("SYNC_SID");
        assertNotNull(details);
        assertEquals("SYNC_SID", details.getSid());
    }

    @Test
    void testGetInverters() {
        stubFor(get(urlMatching("/user/api/v2/systems/inverters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": [{\"eid\": \"E1\"}]}")));

        List<EcuInverters> inverters = client.getInverters("SID");
        assertNotNull(inverters);
        assertEquals(1, inverters.size());
    }

    @Test
    void testGetEnergyInPeriod() {
        stubFor(get(urlMatching("/user/api/v2/systems/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"values\": [10.0]}}")));

        EnergyData data = client.getEnergyInPeriod("SID", SystemEnergyLevel.DAILY, "2024-02");
        assertNotNull(data);
        assertEquals(10.0, data.getValues().get(0));
    }

    @Test
    void testGetMeters() {
        stubFor(get(urlMatching("/user/api/v2/systems/meters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": [\"M1\"]}")));

        List<String> meters = client.getMeters("SID");
        assertEquals(1, meters.size());
        assertEquals("M1", meters.get(0));
    }

    @Test
    void testGetEcuEnergySummary() {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/ecu/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"today\": \"5.0\"}}")));

        EnergySummary summary = client.getEcuEnergySummary("SID", "EID");
        assertEquals(5.0, summary.getTodayAsDouble());
    }

    @Test
    void testMeterEnergySummary() {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/meter/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"today\": {\"consumed\": \"1.5\"}}}")));

        MeterSummary summary = client.getMeterEnergySummary("SID", "EID");
        assertEquals("1.5", summary.getToday().getConsumed());
    }

    @Test
    void testInverterSummary() {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"d1\": \"2.0\"}}")));

        InverterSummary summary = client.getInverterEnergySummary("SID", "UID");
        assertEquals(2.0, summary.getD1AsDouble());
    }

    @Test
    void testInverterBatchEnergy() {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/batch/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"values\": [1.1]}}")));

        EnergyData data = client.getInverterBatchEnergy("SID", "EID", BatchEnergyLevel.ENERGY, "2024-02-26");
        assertEquals(1.1, data.getValues().get(0));
    }

    @Test
    void testStorageLatestPower() {
        stubFor(get(urlMatching("/installer/api/v2/systems/.*/devices/storage/latest/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 0, \"data\": {\"soc\": \"85\"}}")));

        StorageData data = client.getStorageLatestPower("SID", "EID");
        assertEquals(85.0, data.getSocAsDouble());
    }

    @Test
    void testSyncApiErrorHandling() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"code\": 2001}"))); // Invalid App Account

        ApSystemsApiException ex = assertThrows(ApSystemsApiException.class, () -> {
            client.getSystemDetails("SID");
        });
        assertEquals(2001, ex.getCode());
        assertTrue(ex.getMessage().contains("Invalid application account"));
    }

    @Test
    void testSyncHttpErrorHandling() {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withStatus(404)));

        ApSystemsException ex = assertThrows(ApSystemsException.class, () -> {
            client.getSystemDetails("SID");
        });
        assertTrue(ex.getMessage().contains("404"));
    }

    @Test
    void testSyncNetworkErrorHandling() {
        ApSystemsClient faultyClient = new ApSystemsClientBuilder()
                .appId("id")
                .appSecret("secret")
                .baseUrl("http://localhost:1/") 
                .build();

        assertThrows(ApSystemsException.class, () -> {
            faultyClient.getSystemDetails("SID");
        });
    }
}
