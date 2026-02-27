package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Valid energy levels for System-level API.
 * Supports: HOURLY, DAILY, MONTHLY, YEARLY.
 */
public enum SystemEnergyLevel {
    @SerializedName("hourly")
    HOURLY("hourly"),
    
    @SerializedName("daily")
    DAILY("daily"),
    
    @SerializedName("monthly")
    MONTHLY("monthly"),
    
    @SerializedName("yearly")
    YEARLY("yearly");

    private final String value;
    SystemEnergyLevel(String value) { this.value = value; }
    @Override public String toString() { return value; }
}
