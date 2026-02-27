package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class MeterSummary {
    @SerializedName("today")
    private MeterEnergyData today;
    
    @SerializedName("month")
    private MeterEnergyData month;
    
    @SerializedName("year")
    private MeterEnergyData year;
    
    @SerializedName("lifetime")
    private MeterEnergyData lifetime;

    public MeterEnergyData getToday() { return today; }
    public void setToday(MeterEnergyData today) { this.today = today; }
    public MeterEnergyData getMonth() { return month; }
    public void setMonth(MeterEnergyData month) { this.month = month; }
    public MeterEnergyData getYear() { return year; }
    public void setYear(MeterEnergyData year) { this.year = year; }
    public MeterEnergyData getLifetime() { return lifetime; }
    public void setLifetime(MeterEnergyData lifetime) { this.lifetime = lifetime; }
}
