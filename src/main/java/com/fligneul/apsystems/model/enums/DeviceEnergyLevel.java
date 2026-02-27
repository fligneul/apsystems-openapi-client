package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Valid energy levels for Device-level API (ECU, Meter, Inverter, Storage).
 * Supports: MINUTELY, HOURLY, DAILY, MONTHLY, YEARLY.
 */
public enum DeviceEnergyLevel {
    @SerializedName("minutely")
    MINUTELY("minutely"),

    @SerializedName("hourly")
    HOURLY("hourly"),
    
    @SerializedName("daily")
    DAILY("daily"),
    
    @SerializedName("monthly")
    MONTHLY("monthly"),
    
    @SerializedName("yearly")
    YEARLY("yearly");

    private final String value;
    DeviceEnergyLevel(String value) { this.value = value; }
    @Override public String toString() { return value; }
}
