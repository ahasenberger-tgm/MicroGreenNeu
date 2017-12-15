package iot.microgreenneu;
 
public class Sensor {   
    public enum Type {
        DHT22,DS1820,CHIRP
    }
     
    private String name;
    private int id;
    private int subSensor;
    private Type type;
    private String description;
    private SensorInfo[] sensorInfo;
     
    public Sensor(String name, int id,int subSensor,Type type,String description,
            SensorInfo[] sensorInfo) {
        this.name = name; 
        this.id = id;
        this.subSensor = subSensor;
        this.type = type;
        this.description = description;
        this.sensorInfo = sensorInfo;
    }
     
    public String getName() {
        return name;
    }
     
    public int getId() {
        return id;
    }
 
    public int getSubsensor() {
        return subSensor;
    }
     
    public Type getType() {
        return type;
    }
     
    public String getDescription() {
        return description;
    }
     
    public SensorInfo[] getSensorInfo() {
        return sensorInfo;
    }
     
}