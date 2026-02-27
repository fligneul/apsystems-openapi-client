package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class MeterEnergyData {
    @SerializedName("consumed")
    private String consumed;
    
    @SerializedName("exported")
    private String exported;
    
    @SerializedName("imported")
    private String imported;
    
    @SerializedName("produced")
    private String produced;

    public String getConsumed() { return consumed; }
    public void setConsumed(String consumed) { this.consumed = consumed; }
    public String getExported() { return exported; }
    public void setExported(String exported) { this.exported = exported; }
    public String getImported() { return imported; }
    public void setImported(String imported) { this.imported = imported; }
    public String getProduced() { return produced; }
    public void setProduced(String produced) { this.produced = produced; }
}
