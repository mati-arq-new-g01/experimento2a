package co.edu.uniandes.matiang01;

public interface IoTFactory {

	public static final String STORM = "--storm";
	public static final String BRIDGE = "--bridge";

	public void run() throws Exception; 
}
