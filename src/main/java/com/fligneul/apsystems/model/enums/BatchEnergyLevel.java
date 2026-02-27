package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Valid energy levels for Inverter Batch API.
 * Supports: POWER, ENERGY.
 */
public enum BatchEnergyLevel {
    @SerializedName("power")
    POWER("power"),
    
    @SerializedName("energy")
    ENERGY("energy");

    private final String value;
    BatchEnergyLevel(String value) { this.value = value; }
    @Override public String toString() { return value; }
}
