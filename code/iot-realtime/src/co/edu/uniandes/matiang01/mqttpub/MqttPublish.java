package co.edu.uniandes.matiang01.mqttpub;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import co.edu.uniandes.matiang01.constants.Constants;

public class MqttPublish {

    public static void send(String topic, String msg) {

        int qos             = 2;
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(Constants.MQTT_BROKER, Constants.MQTT_ARDUINO_CLIENT_ID, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+Constants.MQTT_BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Publishing message: "+msg);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            sampleClient.disconnect();
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
		new MqttPublish().send("iotdogs","C:22;F:46");
	}
}