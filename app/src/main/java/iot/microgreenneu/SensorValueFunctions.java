package iot.microgreenneu;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
public class SensorValueFunctions {
	
	public static String getSensorValues(String sensor) throws Exception{
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);
         
        DateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Vienna"));;
		
		String content = new Scanner(new URL("http://62.178.0.55:4567/sensor/1")
                .openStream(), "UTF-8").useDelimiter("\\A")
                .next();
        Gson gson = new Gson();
        Sensor s = gson.fromJson(content, new TypeToken<Sensor>() {
        }.getType());
		
        return s.getDescription();
	}
	
    /*static public void main(String[] args) throws Exception {
        SensorValueFunctions.getSensorValues("abc");
        System.out.println("Test");
    }*/
}