package com.uniandes.mascotas.bolt;

import org.apache.commons.lang.mutable.MutableInt;

import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BoltPersistencia extends MongodbBolt {
	

	public BoltPersistencia(String urlConnection, String db, String collection) {
		super(urlConnection, db, collection);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public void execute(Tuple tuple) {
		final String word = tuple.getStringByField("word");
		final MutableInt count = (MutableInt) tuple.getValueByField("count");
		
			String content = (String) tuple.getString(0);
			Log.info("mensaje: "+content);	
			content = IoTUtils.getPetData( content,count.toString());
			Log.info("mensaje json: "+content);	
			if(content!= null){
				DBObject mongoDoc = getMongoDocForInput(content);
	
				try{
					DB database = mongoClient.getDB(db);
					DBCollection dbCollection = database.getCollection(collection);
					dbCollection.insert(mongoDoc);
					collector.ack(tuple);
				}catch(Exception e) {
					e.printStackTrace();
					collector.fail(tuple);
				}		
			}
		
		}
}
