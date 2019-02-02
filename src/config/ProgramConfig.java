package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import main.ConfigEditor;

/**
 * Contains the config for this application
 * Contains the data and the code to load it
 * 
 * @author Alex Pickering
 */
public class ProgramConfig {
	HashMap<String, String> conf = new HashMap<String, String>();
	
	/**
	 * Default constructor
	 * Loads the config on creation
	 */
	public ProgramConfig(ConfigEditor cfe) {
		try {
			load();
		} catch(IOException | IllegalArgumentException e) {
			System.out.println("Exception while loading program config");
			e.printStackTrace();
			
			cfe.windowCloser.closeWithDialogue("Could not load program config", "Loading Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Loads the config
	 * 
	 * @throws FileNotFoundException 
	 */
	void load() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("config.cfg")));
		
		String line = "";
		
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("#") || line.length() == 0) continue; //Ignore comments
				
				String[] ar = line.split("=");
				
				if(ar.length != 2) {
					throw new IllegalArgumentException("Invalid config line: " + line);
				}
				
				conf.put(ar[0], ar[1]);
			}
		} finally {
			br.close();
		}
	}
	
	/**
	 * Gets the program config
	 * 
	 * @return The program config
	 */
	public HashMap<String, String> getConfig(){
		return conf;
	}
}
