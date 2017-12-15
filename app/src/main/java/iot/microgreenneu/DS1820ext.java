package iot.microgreenneu;
 
import java.util.Date;
 
public class DS1820ext {
    private Sensor.Type type;
    private int id;
    private float  temperature;
    private Date date;
     
    public  DS1820ext(int id,float  temperature,Date date) {
        this.type = Sensor.Type.DS1820;
        this.id = id;
        this.temperature = temperature;
        this.date = date;
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