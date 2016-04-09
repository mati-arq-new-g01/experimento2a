package co.edu.uniandes.matiang01.iot.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class MongoDate{

	@SerializedName("$date")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	} 
}
