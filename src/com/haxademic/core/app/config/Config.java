package com.haxademic.core.app.config;

import java.io.IOException;
import java.util.Properties;

import com.haxademic.core.app.P;
import com.haxademic.core.debug.DebugUtil;
import com.haxademic.core.file.FileUtil;

import processing.core.PApplet;

/**
 * Helper object to extend the standard java.util.Properties 
 * class to return pre-typed data from .properties files
 */
public class Config {
	protected static Properties properties;
	protected PApplet p;
	
	// Singleton instance
	
	public static Config instance;
	
	public static Config instance() {
		if(instance != null) return instance;
		instance = new Config(P.p);
		return instance;
	}

	public Config(PApplet p) {
		super();
		this.p = p;
		properties = new Properties();
		loadDefaultPropsFile();
	}
	
	protected void loadDefaultPropsFile() {
		String defaultPropsFile = FileUtil.getFile("properties/run.properties");
		if(FileUtil.fileExists(defaultPropsFile)) {
			loadPropertiesFile(defaultPropsFile);
		}
	}
	
	public static void loadPropertiesFile(String file) {
		try {
			properties.load(P.p.createInput(file));
		} catch(IOException e) {
			DebugUtil.printErr("couldn't read run.properties config file...");
		}
	}
 
	// Getters!
	
	// string helpers
	
	public static Object setProperty(String id, String state) {
		return properties.setProperty(id, state);
	}
	
	public static String getString(String id, String defState) {
		return properties.getProperty(id,defState);
	}
 
	// boolean helpers
	
	public static Object setProperty(String id, boolean state) {
		return properties.setProperty(id, ""+state);
	}
 
	public static boolean getBoolean(String id, boolean defState) {
		return Boolean.parseBoolean(properties.getProperty(id,""+defState));
	}
	
	// int helpers
	
	public static Object setProperty(String id, int val) {
		return properties.setProperty(id, ""+val);
	}
	
	public static int getInt(String id, int defVal) {
		return Integer.parseInt(properties.getProperty(id,""+defVal));
	}
 
	// float helpers
	
	public static Object setProperty(String id, float val) {
		return properties.setProperty(id, ""+val);
	}
	
	public static float getFloat(String id, float defVal) {
		return new Float(properties.getProperty(id,""+defVal)); 
  	}  
}