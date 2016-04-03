package co.edu.uniandes.matiang01.kafkabridge;


import java.sql.Timestamp;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import co.edu.uniandes.matiang01.constants.Constants;
import co.edu.uniandes.matiang01.kafkapub.KafkaPub;

public class MqttKafkaBridge implements MqttCallback {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private MqttClient mqttClient;
	
	private KafkaPub pub = new KafkaPub();
	private void connect(String serverURI, String clientId, String zkConnect) throws MqttException {
		mqttClient = new MqttClient(serverURI, Constants.MQTT_KAFKA_BRIDGE_CLIENT_ID);
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		mqttClient.connect(connOpts);
		
		pub.connect(zkConnect);
	}

	private void subscribe() throws MqttException {
		mqttClient.setCallback(new MqttCallback() {
			 
		    public void messageArrived(String topic, MqttMessage message) throws Exception {
		        String time = new Timestamp(System.currentTimeMillis()).toString();
		        System.out.println("\nReceived a Message!" +
		            "\n\tTime:    " + time +
		            "\n\tTopic:   " + topic +
		            "\n\tMessage: " + new String(message.getPayload()) +
		            "\n\tQoS:     " + message.getQos() + "\n");
		        pub.sendMessage(topic, new String(message.getPayload()));
		    }
		 
		    public void connectionLost(Throwable cause) {
		        System.out.println("Connection to Solace broker lost!" + cause.getMessage());
		    }
		 
		    public void deliveryComplete(IMqttDeliveryToken token) {
		    }
		 
		});
		
		mqttClient.subscribe(Constants.MQTT_ALL_TOPICS, 0);
		
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.warn("Lost connection to MQTT server", cause);
		while (true) {
			try {
				logger.info("Attempting to reconnect to MQTT server");
				logger.info("Reconnected to MQTT server, resuming");
				return;
			} catch (Exception e) {
				logger.warn("Reconnect failed, retrying in 10 seconds", e);
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		byte[] payload = message.getPayload();
		KeyedMessage<String,String> messagek = new <String,String>KeyedMessage(topic, payload);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MqttKafkaBridge bridge = new MqttKafkaBridge();
			
			String mqttBroker = Constants.MQTT_BROKER;
			String mqttKafkaZookeeper = Constants.KAFKA_ZOOKEEPER_CONNECT;
			if(args != null && args.length > 0){
				mqttBroker =args[0];
				if(args.length > 1){
					mqttKafkaZookeeper =args[1];
				}
			}
			
			bridge.connect(mqttBroker, Constants.MQTT_KAFKA_BRIDGE_CLIENT_ID, mqttKafkaZookeeper);
			bridge.subscribe();
			
		} catch (MqttException e) {
			e.printStackTrace(System.err);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
