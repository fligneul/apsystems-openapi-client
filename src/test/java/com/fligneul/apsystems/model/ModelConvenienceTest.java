package com.fligneul.apsystems.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelConvenienceTest {

    @Test
    void testSystemDetailsCapacity() {
        SystemDetails details = new SystemDetails();
        details.setCapacity("1.23");
        assertEquals(1.23, details.getCapacityAsDouble());
        
        details.setCapacity("invalid");
        assertNull(details.getCapacityAsDouble());
    }

    @Test
    void testEnergySummaryParsing() {
        EnergySummary summary = new EnergySummary();
        summary.setToday("10.5");
        summary.setMonth("100.2");
        summary.setYear("1000.3");
        summary.setLifetime("5000.4");

        assertEquals(10.5, summary.getTodayAsDouble());
        assertEquals(100.2, summary.getMonthAsDouble());
        assertEquals(1000.3, summary.getYearAsDouble());
        assertEquals(5000.4, summary.getLifetimeAsDouble());
    }

    @Test
    void testStorageDataParsing() {
        StorageData data = new StorageData();
        data.setSoc("95");
        data.setDischarge("1.1");
        assertEquals(95.0, data.getSocAsDouble());
        assertEquals(1.1, data.getDischargeAsDouble());
    }

    @Test
    void testInverterSummaryParsing() {
        InverterSummary summary = new InverterSummary();
        summary.setD1("1.1");
        summary.setM2("2.2");
        summary.setY3("3.3");
        summary.setT4("4.4");

        assertEquals(1.1, summary.getD1AsDouble());
        assertEquals(2.2, summary.getM2AsDouble());
        assertEquals(3.3, summary.getY3AsDouble());
        assertEquals(4.4, summary.getT4AsDouble());
    }
}
