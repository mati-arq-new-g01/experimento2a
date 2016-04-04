package com.uniandes.mascotas.bolt;

import java.util.Map;

import org.apache.commons.lang.mutable.MutableInt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class BoltPersistencia extends BaseRichBolt {

	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
	}

	@Override
	public void execute(Tuple tuple) {
		final String word = tuple.getStringByField("word");
		final MutableInt count = (MutableInt) tuple.getValueByField("count");
		
		try{
                    
                    
                    
                    
                    ////bloque mongo
                    Mongo mongo = new MongoClient("localhost",27017);
                DB db;
                DBCollection tabla;
                db= mongo.getDB("mascotas");
                tabla = db.getCollection("popularidad");

                BasicDBObject document =  new BasicDBObject();
                
                
                    
                    ////termina bloque mongo
                    
                    
                    
                    
                if(word.toLowerCase().charAt(0)=='p' ){
                    System.out.println(String.format("%s===>%s", word, count.toString()));
                    
                    document.put("palabra", "'" + word + "'");
                    document.put("animal", "'perro'");
                    document.put("cuenta", count.toString());
                    tabla.insert(document);
                    
                }else if(word.toLowerCase().charAt(0)=='g' ){
                    System.out.println(String.format("%s===>%s", word, count.toString()));
                    document.put("palabra", "'" + word + "'");
                    document.put("animal", "'gato'");
                    document.put("cuenta", count.toString());
                    tabla.insert(document);
                }
                    
                  }catch(Exception e){
                    System.out.println("ERROR");      
                  }
                
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}
