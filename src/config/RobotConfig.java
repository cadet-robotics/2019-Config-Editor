package config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.ConfigEditor;

/**
 * Manages the robot's config file
 * 
 * @author Alex Pickering
 */
public class RobotConfig {
	JsonObject config;
	
	ConfigEditor cfe;
	
	/**
	 * Default constructor
	 * Loads the config file
	 */
	public RobotConfig(ConfigEditor cfe) {
		this.cfe = cfe;
		
		try {
			load();
		} catch(IOException e) {
			System.out.println("Exception while loading robot config");
			e.printStackTrace();
			
			cfe.windowCloser.closeWithDialogue("Could not load robot config", "Loading Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Saves the current version of the config
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(getConfigFile()), true);
		try {
			String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(config);
			pw.print(jsonString);
		} finally {
			pw.close();
		}
		
		cfe.windowCloser.setSaved(true);
	}
	
	/**
	 * Loads the robot's config file
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {
		config = new JsonParser().parse(new FileReader(getConfigFile())).getAsJsonObject();
	}
	
	File getConfigFile() {
		return new File(cfe.pcfg.getConfig().get("project_dir") + cfe.pcfg.getConfig().get("robot_config_dir"));
	}
	
	/**
	 * Gets the robot's config
	 * 
	 * @return The JSONObject of the robot's config file
	 */
	public JsonObject getRobotConfig() {
		return config;
	}
	
	/**
	 * Sets the robot's config
	 * 
	 * @param cfg The new version of the config JSON
	 */
	public void setRobotConfig(JsonObject cfg) {
		config = cfg;
	}
}
