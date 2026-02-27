package com.fligneul.apsystems;

import com.fligneul.apsystems.model.*;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test that verifies the SDK against real-life anonymized stubs
 * captured from a live APsystems account.
 */
@WireMockTest
public class ApSystemsIntegrationTest {

    private ApSystemsClient client;
    private static final String STUB_PATH = "src/test/resources/stubs/";

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        client = new ApSystemsClientBuilder()
                .appId("testAppId")
                .appSecret("testAppSecret")
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .build();
    }

    @Test
    void testRealSystemDetails() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/details/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("system_details.json"))));

        SystemDetails details = client.getSystemDetails("ANONYMIZED_ID");
        assertNotNull(details);
        assertEquals("ANONYMIZED_ID", details.getSid());
        assertEquals("Europe/Paris", details.getTimezone());
        assertEquals(1.62, details.getCapacityAsDouble());
    }

    @Test
    void testRealInverters() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/inverters/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("inverters.json"))));

        List<EcuInverters> inverters = client.getInverters("ANONYMIZED_ID");
        assertNotNull(inverters);
        assertFalse(inverters.isEmpty());
        assertEquals("ANONYMIZED_ID", inverters.get(0).getEid());
        assertEquals(2, inverters.get(0).getInverters().size());
    }

    @Test
    void testRealEcuMinutelyData() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/ecu/energy/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("ecu_minutely.json"))));

        // Use the minutely level which returns the complex object
        EnergyData data = client.getEcuEnergyInPeriod("sid", "eid", com.fligneul.apsystems.model.enums.DeviceEnergyLevel.MINUTELY, "2024-02-26");
        
        assertNotNull(data);
        assertFalse(data.getValues().isEmpty(), "Should have energy values");
        assertFalse(data.getTimes().isEmpty(), "Should have timestamps");
        assertFalse(data.getPower().isEmpty(), "Should have power values");
        assertEquals(4.67, data.getToday());
    }

    @Test
    void testRealInverterSummary() throws Exception {
        stubFor(get(urlMatching("/user/api/v2/systems/.*/devices/inverter/summary/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(readStub("inverter_summary.json"))));

        InverterSummary summary = client.getInverterEnergySummary("sid", "uid");
        assertNotNull(summary);
        assertEquals(17.12, summary.getM1AsDouble());
        assertEquals(17.16, summary.getM2AsDouble());
    }

    private String readStub(String fileName) throws IOException {
        Path path = Paths.get(STUB_PATH, fileName);
        if (!Files.exists(path)) {
            throw new RuntimeException("Stub file not found: " + path.toAbsolutePath() + 
                ". Run RealDataFetcher first!");
        }
        return new String(Files.readAllBytes(path));
    }
}
