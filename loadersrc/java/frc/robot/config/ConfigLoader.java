package java.frc.robot.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Loads the config file into a JSON object for parsing
 * 
 * @author Alex Pickering
 */
public class ConfigLoader {
	public JSONObject loadConfigFile() throws IOException, ParseException {
		FileReader fr = new FileReader(getConfigFile());
		
		return (JSONObject) new JSONParser().parse(fr);
	}
	
	/**
	 * Gets the config file to read from
	 * This will be the 'first' .cfg file found in this implementation
	 * 
	 * @return The first .cfg file in the deploy directory
	 * @throws FileNotFoundException
	 */
	File getConfigFile() throws FileNotFoundException {
		FilenameFilter fnf = new FilenameFilter() {
			@Override
			public boolean accept(File d, String n) {
				return n.endsWith(".cfg");
			}
		};
		
		File[] dir = Filesystem.getDeployDirectory().listFiles(fnf);
		
		if(dir.length == 0) throw new FileNotFoundException("No .cfg files found in deploy directory");
		
		return dir[0];
	}
	
	/**
	 * Gets the config file to read from
	 * The file is specified in this implementation
	 * 
	 * @param fileName The config file to read from
	 * @return The config file
	 * @throws FileNotFoundException
	 */
	File getConfigFile(String fileName) throws FileNotFoundException {
		File[] dir = Filesystem.getDeployDirectory().listFiles();
		
		if(dir.length == 0) throw new FileNotFoundException("No files in deploy directory");
		
		for(File f : dir) {
			System.out.println(f.getName());//debug TODO
			if(f.getName().equals(fileName)) return f;
		}
		
		throw new FileNotFoundException("Secified config file not found");
	}
}
