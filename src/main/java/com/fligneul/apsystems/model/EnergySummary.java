package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class EnergySummary {
    @SerializedName("today")
    private String today;
    
    @SerializedName("month")
    private String month;
    
    @SerializedName("year")
    private String year;
    
    @SerializedName("lifetime")
    private String lifetime;

    public String getToday() { return today; }
    public void setToday(String today) { this.today = today; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getLifetime() { return lifetime; }
    public void setLifetime(String lifetime) { this.lifetime = lifetime; }

    public Double getTodayAsDouble() { return parseDouble(today); }
    public Double getMonthAsDouble() { return parseDouble(month); }
    public Double getYearAsDouble() { return parseDouble(year); }
    public Double getLifetimeAsDouble() { return parseDouble(lifetime); }

    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
