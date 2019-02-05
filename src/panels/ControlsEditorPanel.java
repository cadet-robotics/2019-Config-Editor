package panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;

import com.google.gson.JsonObject;

import main.ConfigEditor;


/**
 * Panel for editing the controls on the joystick
 * 
 * @author Alex pickering
 */
@SuppressWarnings("serial")
public class ControlsEditorPanel extends JPanel implements ActionListener, NamedPanel {
	//Config file stuff
	JsonObject config,
			   controlsConfig;
	
	//Main instance
	ConfigEditor mainWindow;
	
	//Selector panel
	ControlSelectorPanel selectorPanel = new ControlSelectorPanel();
	
	//Control names
	HashMap<String, String> buttonsMap = new HashMap<>();
	
	/**
	 * Default constructor
	 * 
	 * @param mainWindow Instance of main class
	 */
	public ControlsEditorPanel(BufferedImage background, ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Layout
		
		//Alignment
		
		//Size
		 selectorPanel.setPreferredSize(new Dimension((int) mainWindow.getDefaultSize().getWidth() / 3, (int) mainWindow.getDefaultSize().getHeight() - 50));
		
		//Add components
		add(selectorPanel);
		add(new ImagePanel(background));
	}
	
	/**
	 * Updates configuration variables
	 * 
	 * @param jo The JsonObject containing the config
	 */
	public void updateCfg(JsonObject jo) {
		config = jo;
		controlsConfig = config.getAsJsonObject("controls");
		
		//controlSelector.removeAllItems();
		
		for(String k : controlsConfig.keySet()) {
			if(k.equals("desc")) continue;
			//controlSelector.addItem(format(k));
		}
	}
	
	/**
	 * Formats a control key into a more 'readable' version
	 * 
	 * @param s The string to format
	 * @return The formatted string
	 */
	String format(String str) {
		//Split by word
		String[] sa = str.split(" ");
		String ret = "";
		
		for(int i = 0; i < sa.length; i++) {
			//Split by hyphen (x-axis to X-Axis and not X-axis)\
			String[] sah = sa[i].split("-");
			sa[i] = "";
			
			for(int j = 0; j < sah.length; j++) {
				sah[j] = sah[j].substring(0, 1).toUpperCase() + sah[j].substring(1);
				sa[i] += "-" + sah[j];
			}
			
			sa[i] = sa[i].substring(1);
			
			ret += " " + sa[i];
		}
		
		return ret.substring(1);
	}
	
	@Override
	public String getPanelName() {
		return "controls";
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
