package co.edu.uniandes.matiang01.mqttpub;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublish {

    public static String send(String topic, String msg) {

        int qos             = 2;
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient("tcp://157.253.226.55:1883", "androidIdClient", persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+"tcp://157.253.226.55:1883");
            sampleClient.connect(connOpts);
            System.out.println("Publishing message: "+msg);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            sampleClient.disconnect();
            return "ok";
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
            return me.getMessage();
        }
    }
    
    public static void main(String[] args) {
		new MqttPublish().send("iotcats","hi");
	}
}