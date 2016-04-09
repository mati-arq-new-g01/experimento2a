package co.edu.uniandes.matiang01.iot;

import java.util.Date;

import co.edu.uniandes.matiang01.iot.model.MongoDate;
import co.edu.uniandes.matiang01.iot.model.Temperature;
import co.edu.uniandes.matiang01.utils.GSonUtils;

public class TemperatureSensor implements Sensor{
	
	private String value;
	
	public TemperatureSensor(String value) {
		this.value = value;
	}

	public String getData(){
		MongoDate date = new MongoDate();
		date.setDate(new Date());
		
		Temperature t = new Temperature(value,"C", "Temperature",date);
		return GSonUtils.serialize(t);
		
	}

}
