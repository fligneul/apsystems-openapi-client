package com.fligneul.apsystems.model;

import com.fligneul.apsystems.model.enums.SystemStatus;
import com.fligneul.apsystems.model.enums.SystemType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents general information about a solar system.
 */
public class SystemDetails {
    @SerializedName("sid")
    private String sid;
    
    @SerializedName("create_date")
    private String createDate;
    
    /**
     * System size. Default unit is kW.
     */
    @SerializedName("capacity")
    private String capacity;
    
    /**
     * System type (PV, Storage, or both).
     */
    @SerializedName("type")
    private SystemType type;
    
    @SerializedName("timezone")
    private String timezone;
    
    /**
     * List of ECU IDs registered in this system.
     */
    @SerializedName("ecu")
    private List<String> ecu;
    
    /**
     * System status light (Green, Yellow, Red, Grey).
     */
    @SerializedName("light")
    private SystemStatus light;
    
    @SerializedName("authorization_code")
    private String authorizationCode;

    // Getters and Setters
    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public SystemType getType() { return type; }
    public void setType(SystemType type) { this.type = type; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public List<String> getEcu() { return ecu; }
    public void setEcu(List<String> ecu) { this.ecu = ecu; }
    public SystemStatus getLight() { return light; }
    public void setLight(SystemStatus light) { this.light = light; }
    public String getAuthorizationCode() { return authorizationCode; }
    public void setAuthorizationCode(String authorizationCode) { this.authorizationCode = authorizationCode; }

    public Double getCapacityAsDouble() {
        try {
            return capacity != null ? Double.parseDouble(capacity) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
