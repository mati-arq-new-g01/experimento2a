package co.edu.uniandes.matiang01.storm.bolt;

import java.util.Map;

import org.bson.Document;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import co.edu.uniandes.matiang01.utils.Log;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


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
		
		Document mongoDoc = getMongoDocForInput(input);
		try{
			mongoDB.getCollection(collection).insertOne(mongoDoc);
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
	
	public Document  getMongoDocForInput(Tuple input) {
		Document doc = new Document();
		String content = (String) input.getString(0);
		String[] parts = content.trim().split(";");
		Log.info("Received in MongoDB bolt "+content);
		try {
			for(String part : parts) {
				String[] subParts = part.split(":");
				String fieldName = subParts[0];
				String value = subParts[1];
				Log.info("document "+" field:"+fieldName+" value:"+value);
				doc.append(fieldName, value);
			}
		} catch(Exception e) {
			
		}
		return doc;
	}
	
	public static void main(String[] args) {

		MongodbBolt m = new MongodbBolt("ds059165.mlab.com", 59165, "gatos", "temperature","test","test");
		m.prepare(null, null, null);
		m.insert("hola");
		System.out.println("");
	}


	private void insert(String string) {
		Document doc = new Document();
		doc.append("hola", "quetal");
		mongoDB.getCollection(collection).insertOne(doc);
	}

}