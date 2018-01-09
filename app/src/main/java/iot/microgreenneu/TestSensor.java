package iot.microgreenneu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
import com.google.gson.Gson;

public class TestSensor {

    public static double evalData(int sensor, double val) {

        switch (sensor) {

            case 0:
            case 1: return val/30;  //>0, 1 = 30 Grad C, 0.5 = 15 Grad C
            case 2: return val/100; //Zwischen 0 und 1, 1 = feucht, 0 = trocken
            case 3: return val/400; //>0, ~1 = feuchte erde
            default:return -1;
        }
    }

    //i = 0 -> Bodentemperatur
    //i = 1 -> Lufttemperatur
    //i = 2 -> Luftfeuchtigkeit
    //i = 3 -> Bodenfeuchte
    public static double getSensorData(int i) throws Exception{

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);

        DateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Vienna"));;
        Gson gson = new Gson();

        /*String content = new Scanner(new URL("http://62.178.0.55:4567/sensor/" + i)
                .openStream(), "UTF-8")
                .useDelimiter("\\A").next();*/

        //URL url = new URL("http://62.178.0.55:4567/sensor/"+i);
        //Scanner s = new Scanner(url.openStream(), "UTF-8");
        //String content  = s.useDelimiter("\\A").next();
        String content = "";

        switch (i) {
            case 0:
            case 1:
                content = "{\"type\":\"DS1820\",\"id\":0,\"temperature\":21.875,\"date\":\"Nov 27, 2017 6:22:40 AM\"}";
                DS1820ext ds1820 = gson.fromJson(content, DS1820ext.class);

                //temperatur
                return ds1820.getTemperature();

            case 2:
                DHT22ext dht22 = gson.fromJson(content, DHT22ext.class);

                //luftfeuchtigkeit
                return dht22.getHumidity();
            case 3:
                content = "{\"type\":\"CHIRP\",\"id\":\"1\",\"moisture\":\"365\",\"temperature\":\"21.9\",\"light\":\"41463\",\"date\":\"Nov 27, 2017 6:52:50 AM\"}";
                ChirpExt chirp =  gson.fromJson(content, ChirpExt.class);

                //bodenfeuchtigkeit
                return chirp.getMoisture();
        }
        return 0;
    }

    /*
    static public void main(String[] args) throws Exception {
        System.out.println("Lufttemperatur: "+TestSensor.getSensorData(3));
        System.out.println("Evaluierter Wert: "+TestSensor.evalData(0, TestSensor.getSensorData(3)));
    }
    */
}