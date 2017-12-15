package iot.microgreenneu;
 
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
 
import com.google.gson.Gson;
 
public class TestSensor {
	
	class ValuePair{
		
		public ValuePair(double v1, double v2) {
			this.val1 = v1;
			this.val2 = v2;
		}
		
		double val1;
		double val2;
		
		public void setVal(double v1, double v2) {
			this.val1 = v1;
			this.val2 = v2;
		}
		
		public double getVal1() {
			return this.val1;
		}
		
		public double getVal2() {
			return this.val2;
		}
	}
	
	public static double getSensorData(int i) throws Exception{
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);
         
        DateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Vienna"));;
        Gson gson = new Gson();
        
        String content = new Scanner(new URL("http://62.178.0.55:4567/sensor/" + i)
                .openStream(), "UTF-8")
                .useDelimiter("\\A").next();
        
        switch (i) {
        case 0:
        case 1: 
            DS1820ext ds1820 = gson.fromJson(content, DS1820ext.class);
            return ds1820.getTemperature();
            
            case 2:
            DHT22ext dht22 = gson.fromJson(content, DHT22ext.class);
            return dht22.getHumidity();
        case 3:
            ChirpExt chirp =  gson.fromJson(content, ChirpExt.class);
            return chirp.getMoisture();
        }
        return 0;
	}
	
    static public void main(String[] args) throws Exception {
         
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);
         
        DateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Vienna"));;
        Gson gson = new Gson();
        for (int i = 0; i < 4; ++i) {
            String content = new Scanner(new URL("http://62.178.0.55:4567/sensor/" + i)
                    .openStream(), "UTF-8")
                    .useDelimiter("\\A").next();
            switch (i) {
            case 0:
            case 1: 
                DS1820ext ds1820 = gson.fromJson(content, DS1820ext.class);
                String info;
                if(i == 0) {
                    info = "Soil temperature: ";
                } else {
                    info = "Case temperature: ";
                }
                System.out.println(sdfDate.format(ds1820.getDate()) + " - " +info 
                        + ds1820.getTemperature() +"�C");
                break;
            case 2:
                DHT22ext dht22 = gson.fromJson(content, DHT22ext.class);
                System.out.println(sdfDate.format(dht22.getDate()) + " - " + "Air temperature: " + 
                        dht22.getTemperature() +"�C / Air humidity: " + dht22.getHumidity() + "%");
                break;
            case 3:
                ChirpExt chirp =  gson.fromJson(content, ChirpExt.class);
                System.out.println(sdfDate.format(chirp.getDate()) + " - Soil moisture: " + 
                chirp.getMoisture() + " / Soil temperature: " + chirp.getTemperature() + 
                "�C /Light: " + chirp.getLight()  ); 
                break;
            }
        }
 
    }
}