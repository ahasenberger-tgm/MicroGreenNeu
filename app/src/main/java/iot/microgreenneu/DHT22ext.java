package iot.microgreenneu;
import java.util.Date;

public class DHT22ext {
    private Sensor.Type type;
    private int id;
    private float  temperature;
    private float humidity;
    private Date date;
     
    public  DHT22ext(int id,float  humidity,float  temperature,Date date) {
        this.type = Sensor.Type.DHT22;
        this.id = id;
        this.humidity = humidity;
        this.temperature = temperature;
        this.date = date;
    }
     
    public float getHumidity() {
        return humidity;
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
