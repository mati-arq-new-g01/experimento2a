package co.edu.uniandes.matiang01.iot.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Temperature {

	private String value;
	private String valueType;
	private String sensorType;
	private MongoDate createdAt;
	
	public Temperature() {
	}

	
	
	public Temperature(String value, String valueType, String sensorType,MongoDate createdAt) {
		this.value = value;
		this.valueType = valueType;
		this.sensorType = sensorType;
		this.createdAt = createdAt;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public MongoDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(MongoDate createdAt) {
		this.createdAt = createdAt;
	}
}
