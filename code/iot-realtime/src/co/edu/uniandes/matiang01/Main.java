package co.edu.uniandes.matiang01;


public class Main {
	
	public static void main(String[] args)  {
		
		CommandBuilder command = new CommandBuilder(args);
		if(args != null && args.length > 0){
			try {
				command.exec();
			} catch (Exception e) {
				System.out.println(command.toString());
			}
		}
	}

}
