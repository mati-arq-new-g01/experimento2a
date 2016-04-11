package com.uniandes.mascotas;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import co.edu.uniandes.matiang01.storm.bolt.BoltBuilder;
import co.edu.uniandes.matiang01.storm.bolt.MongodbBolt;
import co.edu.uniandes.matiang01.storm.spout.SpoutBuilder;

import com.uniandes.mascotas.bolt.BoltContador;
import com.uniandes.mascotas.bolt.BoltTweetSplitter;
import com.uniandes.mascotas.spout.SpoutTweetsStreamingConsumer;

public class MascotasTopology {

	public static void run(String properties) throws AlreadyAliveException, InvalidTopologyException {
		
		Properties configs = new Properties();
	    SpoutBuilder spoutBuilder = null;
	    BoltBuilder boltBuilder = null;
		try {
			InputStream input = null;
			input = new FileInputStream(properties);
			configs.load(input);
			//configs.load(Topology.class.getResourceAsStream("default_config.properties"));
			boltBuilder = new BoltBuilder(configs);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	    
		MongodbBolt mongoBolt = boltBuilder.buildPetBolt();
		
		final TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("twitterSpout", new SpoutTweetsStreamingConsumer());
		builder.setBolt("tweetSplitterBolt", new BoltTweetSplitter(), 10).shuffleGrouping("twitterSpout");
		builder.setBolt("wordCounterBolt", new BoltContador(), 10).fieldsGrouping("tweetSplitterBolt", new Fields("word"));
		builder.setBolt("countPrinterBolt", mongoBolt, 10).fieldsGrouping("wordCounterBolt", new Fields("word"));

		final Config conf = new Config();
		conf.setDebug(false);

		final LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("wordCountTopology", conf, builder.createTopology());
	}
}
