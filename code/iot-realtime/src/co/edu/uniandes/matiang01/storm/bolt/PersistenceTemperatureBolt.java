package co.edu.uniandes.matiang01.storm.bolt;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


public class PersistenceTemperatureBolt extends MongodbBolt {
	
	public PersistenceTemperatureBolt(String urlConnection, String db,
			String collection) {
		super(urlConnection, db, collection);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public void execute(Tuple input) {
		String content = (String) input.getString(0);
		content = IoTUtils.getData("temperature", content);
		Log.info("mensaje: "+content);	
		DBObject mongoDoc = getMongoDocForInput(content);
		try{
			DB database = mongoClient.getDB(db);
			DBCollection dbCollection = database.getCollection(collection);
			dbCollection.insert(mongoDoc);
			collector.ack(input);
		}catch(Exception e) {
			e.printStackTrace();
			collector.fail(input);
		}
	}

	
}