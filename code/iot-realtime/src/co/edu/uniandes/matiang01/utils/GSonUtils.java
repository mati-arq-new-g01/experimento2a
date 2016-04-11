package co.edu.uniandes.matiang01.utils;

import java.util.Date;

import co.edu.uniandes.matiang01.iot.model.MongoDate;
import co.edu.uniandes.matiang01.iot.model.Temperature;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSonUtils {

	public static Temperature getTemperature(String data){
	
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
	    return gson.fromJson(data, Temperature.class);
	}
	
	public static String serialize(Object instance){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		return gson.toJson(instance);
	}
	
	public static void main(String[] args) {
		
		MongoDate date = new MongoDate();
		date.setDate(new Date());
		
		Temperature t = new Temperature("23","C", "Temperature",date);
		System.out.println(GSonUtils.serialize(t ));
	}
}
