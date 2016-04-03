package co.edu.uniandes.matiang01.kafkapub;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import co.edu.uniandes.matiang01.constants.Constants;

public class KafkaPub {
      
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Producer<String, String> kafkaProducer;
	
	
	public void connect(String zkConnect)  {
		
		Properties props = new Properties();
		props.put("zk.connect", zkConnect);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("metadata.broker.list", Constants.METADATA_BROKER_LIST);
		props.put("request.required.acks", "1");
		props.put("producer.type", "sync");
		props.put("compression.codec", "2");
		
		ProducerConfig config = new ProducerConfig(props);
		kafkaProducer = new Producer<String, String>(config);
	}

	
	public void sendMessage(String topic, String message)  {
		@SuppressWarnings("unused")
		KeyedMessage<String,String> messagek = new <String,String>KeyedMessage<String, String>(topic,message);
		kafkaProducer.send(messagek);
	}
	
	public static void main(String[] args) {
		KafkaPub pub = new KafkaPub();
		String zkConnect = Constants.KAFKA_ZOOKEEPER_CONNECT;
		String topic = "iotdogs";
		String message = "mqtt to kafka";
		if(args != null && args.length > 0){
			zkConnect =args[0];
			if(args.length > 1){
				topic =args[1];
				if(args.length > 2){
					message = args[2];
				}
			}
		}
		
		pub.connect(zkConnect);
		pub.sendMessage(topic, message);
	}

}