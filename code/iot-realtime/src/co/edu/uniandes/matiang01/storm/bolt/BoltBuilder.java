package co.edu.uniandes.matiang01.storm.bolt;

import java.util.Properties;

import co.edu.uniandes.matiang01.storm.Keys;

import com.uniandes.mascotas.bolt.BoltPersistencia;

/**
 * @author vishnu viswanath
 * This class is used for building bolts
 */
public class BoltBuilder {
	
	public Properties configs = null;
	
	public BoltBuilder(Properties configs) {
		this.configs = configs;
	}
	
	public SinkTypeBolt buildSinkTypeBolt() {
		return new SinkTypeBolt();
	}
	
	public PersistenceTemperatureBolt buildTemperatureBolt() {
		String url = configs.getProperty(Keys.MONGO_TEMPERATURE_URL);
		String db = configs.getProperty(Keys.MONGO_TEMPERATURE_DATABASE);
		String collection = configs.getProperty(Keys.MONGO_TEMPERATURE_COLLECTION);
		return new PersistenceTemperatureBolt(url, db, collection);
	}
	
	public BoltPersistencia buildPetBolt() {
		String url = configs.getProperty(Keys.MONGO_PET_URL);
		String db = configs.getProperty(Keys.MONGO_PET_DATABASE);
		String collection = configs.getProperty(Keys.MONGO_PET_COLLECTION);
		return new BoltPersistencia(url, db, collection);
	}
	
	

}
