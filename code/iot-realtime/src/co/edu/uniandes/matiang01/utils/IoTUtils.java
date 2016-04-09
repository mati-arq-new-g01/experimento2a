package co.edu.uniandes.matiang01.utils;

import co.edu.uniandes.matiang01.iot.TemperatureSensor;

public class IoTUtils {

	public static String getData(String factory, String data) {
		//Factory - Sesortype
		String rs = "";
		if(!factory.isEmpty()){
			try{
			TemperatureSensor sensor = new TemperatureSensor(data.split(";")[0].split(":")[1]);
			rs =sensor.getData();
			}catch(Exception e){
				Log.error("getData fail: "+data);
			}
			return rs ;
		}
		return rs;
	}

}
