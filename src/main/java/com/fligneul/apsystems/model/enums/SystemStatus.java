package com.fligneul.apsystems.model.enums;

import com.google.gson.annotations.SerializedName;

public enum SystemStatus {
    @SerializedName("1")
    GREEN(1),
    
    @SerializedName("2")
    YELLOW(2),
    
    @SerializedName("3")
    RED(3),
    
    @SerializedName("4")
    GREY(4);

    private final int value;

    SystemStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
