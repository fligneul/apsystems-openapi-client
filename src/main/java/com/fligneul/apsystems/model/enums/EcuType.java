package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

public enum EcuType {
    @SerializedName("0")
    STANDARD(0),
    
    @SerializedName("1")
    WITH_METER(1),
    
    @SerializedName("2")
    WITH_STORAGE(2);

    private final int value;

    EcuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
