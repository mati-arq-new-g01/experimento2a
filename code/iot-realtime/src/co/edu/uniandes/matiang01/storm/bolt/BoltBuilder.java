package co.edu.uniandes.matiang01.storm.bolt;

import java.util.Properties;

import co.edu.uniandes.matiang01.storm.Keys;

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
	
	public MongodbBolt buildMongodbBolt() {
		String host = configs.getProperty(Keys.MONGO_HOST);
		int port = Integer.parseInt(configs.getProperty(Keys.MONGO_PORT));
		String db = configs.getProperty(Keys.MONGO_DATABASE);
		String collection = configs.getProperty(Keys.MONGO_COLLECTION);
		String user = configs.getProperty(Keys.MONGO_USER);
		String pass = configs.getProperty(Keys.MONGO_PASS);
		return new MongodbBolt(host, port, db, collection,user,pass);
	}
	
	

}
