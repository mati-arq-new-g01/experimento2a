package com.uniandes.mascotas;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.uniandes.mascotas.bolt.BoltPersistencia;
import com.uniandes.mascotas.bolt.BoltTweetSplitter;
import com.uniandes.mascotas.bolt.BoltContador;
import com.uniandes.mascotas.spout.SpoutTweetsStreamingConsumer;

public class MascotasTopology {

	public static void run() throws AlreadyAliveException, InvalidTopologyException {
		final TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("twitterSpout", new SpoutTweetsStreamingConsumer());
		builder.setBolt("tweetSplitterBolt", new BoltTweetSplitter(), 10).shuffleGrouping("twitterSpout");
		builder.setBolt("wordCounterBolt", new BoltContador(), 10).fieldsGrouping("tweetSplitterBolt", new Fields("word"));
		builder.setBolt("countPrinterBolt", new BoltPersistencia(), 10).fieldsGrouping("wordCounterBolt", new Fields("word"));

		final Config conf = new Config();
		conf.setDebug(false);

		final LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("wordCountTopology", conf, builder.createTopology());
	}
}
