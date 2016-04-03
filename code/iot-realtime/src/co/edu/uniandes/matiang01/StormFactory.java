package co.edu.uniandes.matiang01;

import co.edu.uniandes.matiang01.storm.WordCountTopology;

public class StormFactory implements IoTFactory{

	String job;
	String properties;
	public StormFactory(String job, String properties) {
		this.job = job;
		this.properties = properties;
	}

	@Override
	public void run() throws Exception {
		if(job.equals("temperature")){
			WordCountTopology.run(properties);
		}
	}

	public static String help() {
		return "\t\t--storm params [1=job] [2=propertiesFile] -> \t --storm temperature default_config.properties\n";
	}

}
