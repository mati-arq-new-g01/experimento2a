package co.edu.uniandes.matiang01;

public class CommandBuilder {

	private IoTFactory factory;

	public CommandBuilder(String[] args) {
		if (args != null && args.length > 2) {
			if (equals(args[0], IoTFactory.STORM)) {
				factory = new StormFactory(args[1], args[2]);
			} else if (equals(args[0], IoTFactory.BRIDGE)) {
				factory = new BridgeFactory(args[1], args[2]);
			}
		} 
	}

	public void exec() throws Exception {
		factory.run();
	}

	private boolean equals(String a, String b) {
		return a.toLowerCase().equals(b.toLowerCase());
	}

	@Override
	public String toString() {
		return "Commands: \n --storm \tExecute topologies \n "+ StormFactory.help()
				+"--bridge \tExecute bridge between mosquitto and kafka\n"
				+ BridgeFactory.help();
	}

}
