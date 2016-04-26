package co.edu.uniandes.matiang01.iot.model;


public class Temperature {

	private double value;
	private String valueType;
	private String sensorType;
	private MongoDate createdAt;
	
	public Temperature() {
	}

	public Temperature(String value, String valueType, String sensorType,MongoDate createdAt) {
		this.value = Double.valueOf(value);
		this.valueType = valueType;
		this.sensorType = sensorType;
		this.createdAt = createdAt;
	}

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
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
