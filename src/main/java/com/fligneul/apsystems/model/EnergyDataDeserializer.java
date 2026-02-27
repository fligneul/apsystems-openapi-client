package com.fligneul.apsystems.model;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergyDataDeserializer implements JsonDeserializer<EnergyData> {
    @Override
    public EnergyData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        EnergyData energyData = new EnergyData();

        if (json.isJsonArray()) {
            // Handle simple list of values [ "1.2", "3.4" ]
            energyData.setValues(parseNumericArray(json.getAsJsonArray()));
        } else if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            
            // Standard minutely object or normalized EnergyData object
            if (obj.has("energy")) energyData.setValues(parseNumericArray(obj.get("energy").getAsJsonArray()));
            else if (obj.has("values")) energyData.setValues(parseNumericArray(obj.get("values").getAsJsonArray()));

            if (obj.has("time")) energyData.setTimes(parseStringArray(obj.get("time").getAsJsonArray()));
            else if (obj.has("times")) energyData.setTimes(parseStringArray(obj.get("times").getAsJsonArray()));

            if (obj.has("power")) energyData.setPower(parseNumericArray(obj.get("power").getAsJsonArray()));
            if (obj.has("today")) energyData.setToday(obj.get("today").getAsDouble());

            // Handle Inverter/Storage specific channels (e1, e2, dc_p1, etc.)
            Map<String, List<Double>> channels = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                if (entry.getValue().isJsonArray() && isChannelKey(entry.getKey())) {
                    channels.put(entry.getKey(), parseNumericArray(entry.getValue().getAsJsonArray()));
                }
            }
            if (!channels.isEmpty()) {
                energyData.setChannelData(channels);
                // For convenience, if there's an 'e1' and no 'values', use 'e1' as default values
                if (energyData.getValues().isEmpty() && channels.containsKey("e1")) {
                    energyData.setValues(channels.get("e1"));
                }
            }
        }

        return energyData;
    }

    private boolean isChannelKey(String key) {
        return key.matches("^[ed][1-4]$") || key.matches("^dc_[pi][1-4]$") || key.matches("^ac_[vtp]$") || key.equals("discharge") || key.equals("charge");
    }

    private List<Double> parseNumericArray(JsonArray array) {
        List<Double> list = new ArrayList<>();
        for (JsonElement e : array) {
            try {
                list.add(e.getAsDouble());
            } catch (Exception ignored) {
                list.add(0.0);
            }
        }
        return list;
    }

    private List<String> parseStringArray(JsonArray array) {
        List<String> list = new ArrayList<>();
        for (JsonElement e : array) {
            list.add(e.getAsString());
        }
        return list;
    }
}
