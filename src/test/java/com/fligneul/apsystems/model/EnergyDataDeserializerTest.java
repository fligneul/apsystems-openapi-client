package com.fligneul.apsystems.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EnergyDataDeserializerTest {
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(EnergyData.class, new EnergyDataDeserializer())
                .create();
    }

    @Test
    void testSimpleListDeserialization() {
        String json = "[\"1.23\", \"4.56\", \"7.89\"]";
        EnergyData data = gson.fromJson(json, EnergyData.class);

        assertNotNull(data);
        assertEquals(3, data.getValues().size());
        assertEquals(1.23, data.getValues().get(0));
        assertEquals(4.56, data.getValues().get(1));
    }

    @Test
    void testMinutelyObjectDeserialization() {
        String json = "{" +
                "\"time\": [\"10:00\", \"10:05\"]," +
                "\"energy\": [\"0.1\", \"0.2\"]," +
                "\"power\": [\"100.0\", \"200.0\"]," +
                "\"today\": \"5.5\"" +
                "}";
        EnergyData data = gson.fromJson(json, EnergyData.class);

        assertNotNull(data);
        assertEquals(2, data.getValues().size());
        assertEquals(0.1, data.getValues().get(0));
        assertEquals("10:00", data.getTimes().get(0));
        assertEquals(100.0, data.getPower().get(0));
        assertEquals(5.5, data.getToday());
    }

    @Test
    void testChannelDataDeserialization() {
        String json = "{" +
                "\"e1\": [\"1.1\", \"1.2\"]," +
                "\"e2\": [\"2.1\", \"2.2\"]," +
                "\"dc_p1\": [\"10.0\", \"11.0\"]" +
                "}";
        EnergyData data = gson.fromJson(json, EnergyData.class);

        assertNotNull(data);
        assertNotNull(data.getChannelData());
        assertTrue(data.getChannelData().containsKey("e1"));
        assertTrue(data.getChannelData().containsKey("e2"));
        assertTrue(data.getChannelData().containsKey("dc_p1"));
        
        // Convenience check: e1 should be mapped to values if values was empty
        assertEquals(1.1, data.getValues().get(0));
    }
}
