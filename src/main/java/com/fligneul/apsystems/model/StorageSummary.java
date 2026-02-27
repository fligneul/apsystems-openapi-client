package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class StorageSummary {
    @SerializedName("today") private StorageData today;
    @SerializedName("month") private StorageData month;
    @SerializedName("year") private StorageData year;
    @SerializedName("lifetime") private StorageData lifetime;

    public StorageData getToday() { return today; }
    public void setToday(StorageData today) { this.today = today; }
    public StorageData getMonth() { return month; }
    public void setMonth(StorageData month) { this.month = month; }
    public StorageData getYear() { return year; }
    public void setYear(StorageData year) { this.year = year; }
    public StorageData getLifetime() { return lifetime; }
    public void setLifetime(StorageData lifetime) { this.lifetime = lifetime; }
}
