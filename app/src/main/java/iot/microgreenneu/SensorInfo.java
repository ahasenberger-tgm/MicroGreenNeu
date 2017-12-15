package iot.microgreenneu;
 
public class SensorInfo {
    public enum Unit { 
        DEGREE_CELSIUS,PERCENT,INT_VALUE,FLOAT_VALUE
    }
 
    private int index;
    private Unit unit;
    private String description;
     
    public SensorInfo(int index,Unit unit,String description) {
        this.index = index;
        this.unit = unit;
        this.description = description; 
    }
     
    public int getIndex() {
        return index;
    }
     
    public String getDescription() {
        return description;
    }
     
    public Unit getUnit() {
        return unit;
    }
}