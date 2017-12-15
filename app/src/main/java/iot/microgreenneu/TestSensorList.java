package iot.microgreenneu;
import java.net.URL;
import java.util.List;
import java.util.Scanner; 
 
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
public class TestSensorList {
    static public void main(String[] args) throws Exception {
        String content = new Scanner(new URL("http://62.178.0.55:4567/sensorlist")
                .openStream(), "UTF-8").useDelimiter("\\A")
                .next();
        Gson gson = new Gson();
        List<Sensor> list = gson.fromJson(content, new TypeToken<List<Sensor>>() {
        }.getType());
        for(Sensor sensor:list) {
            System.out.println("Sensor name           : " + sensor.getName());
            System.out.println("Internal sensor number: " + sensor.getId());
            System.out.println("Sensor type           : " + sensor.getType().name());
            System.out.println("Description           : " + sensor.getDescription());
            for(SensorInfo info:sensor.getSensorInfo()) {
                System.out.println("    Subsensor Id      : " +  info.getIndex());
                System.out.println("    Unit              : " +  info.getUnit().name());
                System.out.println("    Description       : " +  info.getDescription());
 
            }
            System.out.println("\n");
        }
    }
}