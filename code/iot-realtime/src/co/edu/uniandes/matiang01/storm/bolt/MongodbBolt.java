package co.edu.uniandes.matiang01.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.utils.IoTUtils;
import co.edu.uniandes.matiang01.utils.Log;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


public class MongodbBolt extends BaseRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private MongoDatabase mongoDB;
	private MongoClient mongoClient;

	private String collection;
	public String host;
	public int port ;
	public String db;
	public String user;
	public String pass;

	public MongodbBolt(String host, int port, String db,String collection,String user, String pass) {
		this.host = host;
		this.port = port;
		this.db = db;
		this.collection = collection;
		this.user = user;
		this.pass = pass;
	}
	
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		String url = "mongodb://"+user+":"+pass+"@"+host+":"+port+"/"+db;
		if(user.isEmpty() || pass.isEmpty()){
			url = "mongodb://"+host+":"+port+"/"+db;
		}
		
		
		this.mongoClient = new MongoClient(
				new MongoClientURI( url )
		);
		this.mongoDB = mongoClient.getDatabase(db);
	}

	
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

	
	@Override
	public void cleanup() {
		this.mongoClient.close();
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
	
	public DBObject  getMongoDocForInput(String input) {
		  DBObject dbObject = (DBObject)JSON.parse(input);
		  return dbObject;
	}
	
	public static void main(String[] args) {

		MongodbBolt m = new MongodbBolt("ds059165.mlab.com", 59165, "gatos", "temperature","test","test");
		m.prepare(null, null, null);
		m.insert("C:22;F:46");
		System.out.println("");
	}


	private void insert(String string) {
		string = IoTUtils.getData("temperature", string);
		DBObject mongoDoc = getMongoDocForInput(string);
		DB database = mongoClient.getDB(db);
		DBCollection dbCollection = database.getCollection(collection);
		dbCollection.insert(mongoDoc);
	}

}