package com.fligneul.apsystems.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A unified wrapper for energy data that simplifies various API response formats.
 * 
 * <p>Depending on the API call and energy level, different fields will be populated:</p>
 * <ul>
 *   <li><b>Standard Period Energy (Hourly/Daily/etc.)</b>: Use {@link #getValues()} for energy values.</li>
 *   <li><b>Minutely (Power Telemetry)</b>: Use {@link #getValues()} for energy, {@link #getTimes()} for timestamps, 
 *       {@link #getPower()} for power values, and {@link #getToday()} for total daily energy.</li>
 *   <li><b>Inverter Channels</b>: Use {@link #getChannelData()} to access individual channel metrics 
 *       (e.g., "e1", "e2", "dc_p1").</li>
 * </ul>
 */
public class EnergyData {
    private List<Double> values = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<Double> power = new ArrayList<>();
    private Double today;
    private Map<String, List<Double>> channelData; // For inverter e1, e2, etc.

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<Double> getPower() {
        return power;
    }

    public void setPower(List<Double> power) {
        this.power = power;
    }

    public Double getToday() {
        return today;
    }

    public void setToday(Double today) {
        this.today = today;
    }

    public Map<String, List<Double>> getChannelData() {
        return channelData;
    }

    public void setChannelData(Map<String, List<Double>> channelData) {
        this.channelData = channelData;
    }

    @Override
    public String toString() {
        return "EnergyData{" +
                "points=" + values.size() +
                ", hasTimes=" + !times.isEmpty() +
                ", hasPower=" + !power.isEmpty() +
                ", today=" + today +
                '}';
    }
}
