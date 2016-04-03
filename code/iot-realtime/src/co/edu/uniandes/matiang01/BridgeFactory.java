package co.edu.uniandes.matiang01;

import co.edu.uniandes.matiang01.kafkabridge.MqttKafkaBridge;

public class BridgeFactory implements IoTFactory{

	String action;
	String properties;
	
	public BridgeFactory(String action, String properties) {
		this.action = action;
		this.properties = properties;
	}
	
	@Override
	public void run() throws Exception {
		
		MqttKafkaBridge.run(properties);
	}

	public static String help() {
		return "\t\t--bridge params [1=start] [2=propertiesFile] -> \t --bridge start bridge_config.properties\n";
	}

}
