package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class StorageData {
    @SerializedName("mode") private String mode;
    @SerializedName("soc") private String soc;
    @SerializedName("time") private String time;
    @SerializedName("discharge") private String discharge;
    @SerializedName("charge") private String charge;
    @SerializedName("produced") private String produced;
    @SerializedName("consumed") private String consumed;
    @SerializedName("exported") private String exported;
    @SerializedName("imported") private String imported;

    // Getters and Setters
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getDischarge() { return discharge; }
    public void setDischarge(String discharge) { this.discharge = discharge; }
    public String getCharge() { return charge; }
    public void setCharge(String charge) { this.charge = charge; }
    public String getProduced() { return produced; }
    public void setProduced(String produced) { this.produced = produced; }
    public String getConsumed() { return consumed; }
    public void setConsumed(String consumed) { this.consumed = consumed; }
    public String getExported() { return exported; }
    public void setExported(String exported) { this.exported = exported; }
    public String getImported() { return imported; }
    public void setImported(String imported) { this.imported = imported; }

    public Double getSocAsDouble() { return parseDouble(soc); }
    public Double getDischargeAsDouble() { return parseDouble(discharge); }
    public Double getChargeAsDouble() { return parseDouble(charge); }
    public Double getProducedAsDouble() { return parseDouble(produced); }
    public Double getConsumedAsDouble() { return parseDouble(consumed); }
    public Double getExportedAsDouble() { return parseDouble(exported); }
    public Double getImportedAsDouble() { return parseDouble(imported); }

    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
