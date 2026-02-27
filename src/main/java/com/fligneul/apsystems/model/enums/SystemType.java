package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

public enum SystemType {
    @SerializedName("1")
    PV(1),
    
    @SerializedName("2")
    STORAGE(2),
    
    @SerializedName("3")
    PV_AND_STORAGE(3);

    private final int value;

    SystemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
