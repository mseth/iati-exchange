package org.dg.iati.api.util;

import java.util.HashMap;

public class ConfigInstance {
	
	private static ConfigInstance singleton	= null;

	
	private HashMap<String, String> cache;

	private  ConfigInstance() {
		
		this.cache			= new HashMap<String, String>();
		
	}
	
	public static ConfigInstance getInstance() {
		if ( singleton == null ) {
			singleton	= new ConfigInstance();
		}
		return singleton;
	}

	
	public synchronized String get(String key) {
		String result 	= this.cache.get(key);
		if (result == null) {
			result	= IatiUtils.getPropertyValue(key);
			cache.put(key, result);
		}
		return result;
	}
	

}
