package iot.microgreenneu;
 
import java.util.Date;
 
public class ChirpExt {
    private Sensor.Type type;
    private int id;
    private int moisture;
    private float  temperature;
    private int light;
    private Date date;
     
    public  ChirpExt(int id,int moisture,float temperature,int light,Date date) {
        this.type = Sensor.Type.CHIRP;
        this.id = id;
        this. moisture =  moisture;
        this.temperature = temperature;
        this.light = light;
        this.date = date;
    }
     
    public int getMoisture() {
        return moisture;
    }
     
    public int getLight() {
        return light;
    }
     
    public float getTemperature() {
        return temperature;
    }
     
    public Date getDate() {
        return date;
    }
     
    public int getId() {
        return id;
    }
     
    public Sensor.Type getType() {
        return type;
    }
}