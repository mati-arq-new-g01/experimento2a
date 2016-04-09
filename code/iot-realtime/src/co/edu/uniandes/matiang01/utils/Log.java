package co.edu.uniandes.matiang01.utils;

import org.apache.log4j.Logger;

public class Log {

	final static Logger logger = Logger.getLogger(Log.class);

	public static void info(String msg) {
		logger.info(msg);
	}

	public static void error(String msg) {
		logger.error(msg);
	}

}
