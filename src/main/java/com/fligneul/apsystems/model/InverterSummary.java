package com.fligneul.apsystems.model;

import com.google.gson.annotations.SerializedName;

public class InverterSummary {
    @SerializedName("d1") private String d1;
    @SerializedName("m1") private String m1;
    @SerializedName("y1") private String y1;
    @SerializedName("t1") private String t1;
    
    @SerializedName("d2") private String d2;
    @SerializedName("m2") private String m2;
    @SerializedName("y2") private String y2;
    @SerializedName("t2") private String t2;
    
    @SerializedName("d3") private String d3;
    @SerializedName("m3") private String m3;
    @SerializedName("y3") private String y3;
    @SerializedName("t3") private String t3;
    
    @SerializedName("d4") private String d4;
    @SerializedName("m4") private String m4;
    @SerializedName("y4") private String y4;
    @SerializedName("t4") private String t4;

    // Getters and Setters
    public String getD1() { return d1; }
    public void setD1(String d1) { this.d1 = d1; }
    public String getM1() { return m1; }
    public void setM1(String m1) { this.m1 = m1; }
    public String getY1() { return y1; }
    public void setY1(String y1) { this.y1 = y1; }
    public String getT1() { return t1; }
    public void setT1(String t1) { this.t1 = t1; }

    public String getD2() { return d2; }
    public void setD2(String d2) { this.d2 = d2; }
    public String getM2() { return m2; }
    public void setM2(String m2) { this.m2 = m2; }
    public String getY2() { return y2; }
    public void setY2(String y2) { this.y2 = y2; }
    public String getT2() { return t2; }
    public void setT2(String t2) { this.t2 = t2; }

    public String getD3() { return d3; }
    public void setD3(String d3) { this.d3 = d3; }
    public String getM3() { return m3; }
    public void setM3(String m3) { this.m3 = m3; }
    public String getY3() { return y3; }
    public void setY3(String y3) { this.y3 = y3; }
    public String getT3() { return t3; }
    public void setT3(String t3) { this.t3 = t3; }

    public String getD4() { return d4; }
    public void setD4(String d4) { this.d4 = d4; }
    public String getM4() { return m4; }
    public void setM4(String m4) { this.m4 = m4; }
    public String getY4() { return y4; }
    public void setY4(String y4) { this.y4 = y4; }
    public String getT4() { return t4; }
    public void setT4(String t4) { this.t4 = t4; }

    public Double getD1AsDouble() { return parseDouble(d1); }
    public Double getM1AsDouble() { return parseDouble(m1); }
    public Double getY1AsDouble() { return parseDouble(y1); }
    public Double getT1AsDouble() { return parseDouble(t1); }

    public Double getD2AsDouble() { return parseDouble(d2); }
    public Double getM2AsDouble() { return parseDouble(m2); }
    public Double getY2AsDouble() { return parseDouble(y2); }
    public Double getT2AsDouble() { return parseDouble(t2); }

    public Double getD3AsDouble() { return parseDouble(d3); }
    public Double getM3AsDouble() { return parseDouble(m3); }
    public Double getY3AsDouble() { return parseDouble(y3); }
    public Double getT3AsDouble() { return parseDouble(t3); }

    public Double getD4AsDouble() { return parseDouble(d4); }
    public Double getM4AsDouble() { return parseDouble(m4); }
    public Double getY4AsDouble() { return parseDouble(y4); }
    public Double getT4AsDouble() { return parseDouble(t4); }

    private Double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
