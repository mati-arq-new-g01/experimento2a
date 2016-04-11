package co.edu.uniandes.matiang01.storm;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import storm.kafka.KafkaSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import co.edu.uniandes.matiang01.storm.bolt.BoltBuilder;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.storm.bolt.WordCount;
import co.edu.uniandes.matiang01.storm.spout.SpoutBuilder;

public class WordCountTopology {
	

  //Entry point for the topology
  public static void run(String properties) throws Exception {
  //Used to build the topology
    TopologyBuilder builder = new TopologyBuilder();
    

    Properties configs = new Properties();
    SpoutBuilder spoutBuilder = null;
    BoltBuilder boltBuilder = null;
	try {
		InputStream input = null;
		input = new FileInputStream(properties);
		configs.load(input);
		//configs.load(Topology.class.getResourceAsStream("default_config.properties"));
		spoutBuilder = new SpoutBuilder(configs);
		boltBuilder = new BoltBuilder(configs);
	} catch (Exception ex) {
		ex.printStackTrace();
		System.exit(0);
	}
    
	KafkaSpout kafkaSpout =  spoutBuilder.buildKafkaSpout();
	
	
	//set the kafkaSpout to topology
	//parallelism-hint for kafkaSpout - defines number of executors/threads to be spawn per container
	int kafkaSpoutCount = Integer.parseInt(configs.getProperty(Keys.KAFKA_SPOUT_COUNT));
	builder.setSpout("kafkaSpout", kafkaSpout, kafkaSpoutCount);
	
	//Add the spout, with a name of 'spout'
	//and parallelism hint of 5 executors
	//builder.setSpout("spout", new RandomSentenceSpout(), 5);
    
    
    //Add the SplitSentence bolt, with a name of 'split'
    //and parallelism hint of 8 executors
    //shufflegrouping subscribes to the spout, and equally distributes
    //tuples (sentences) across instances of the SplitSentence bolt
   
	//builder.setBolt("split", new SplitSentence(), 2).shuffleGrouping("kafkaSpout");
    
	//Add the counter, with a name of 'count'
    //and parallelism hint of 12 executors
    //fieldsgrouping subscribes to the split bolt, and
    //ensures that the same word is sent to the same instance (group by field 'word')
    builder.setBolt("count", new WordCount(), 2).shuffleGrouping("kafkaSpout");

    MongodbBolt mongoBolt = boltBuilder.buildTemperatureBolt();
    builder.setBolt("countgroup",mongoBolt,12).shuffleGrouping("count");
    
    //new configuration
    Config conf = new Config();
    conf.setDebug(true);

		// Cap the maximum number of executors that can be spawned
		// for a component to 3
		conf.setMaxTaskParallelism(3);
		// LocalCluster is used to run locally
		LocalCluster cluster = new LocalCluster();
		// submit the topology
		cluster.submitTopology("word-count", conf, builder.createTopology());
  }
}