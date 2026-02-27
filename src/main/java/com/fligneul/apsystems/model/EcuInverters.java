package com.fligneul.apsystems.model;

import com.fligneul.apsystems.model.enums.EcuType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EcuInverters {
    @SerializedName("eid")
    private String eid;
    
    @SerializedName("type")
    private EcuType type;
    
    @SerializedName("timezone")
    private String timezone;
    
    @SerializedName("inverter")
    private List<InverterDevice> inverters;

    public String getEid() { return eid; }
    public void setEid(String eid) { this.eid = eid; }
    public EcuType getType() { return type; }
    public void setType(EcuType type) { this.type = type; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public List<InverterDevice> getInverters() { return inverters; }
    public void setInverters(List<InverterDevice> inverters) { this.inverters = inverters; }

    public static class InverterDevice {
        @SerializedName("uid")
        private String uid;
        
        @SerializedName("type")
        private String type;

        public String getUid() { return uid; }
        public void setUid(String uid) { this.uid = uid; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
