package panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
	ControlSelectorPanel selectorPanel;
	
	//Panel on the rigt
	RightSidePanel rsp;
	
	boolean ready = false;
	String previousCommand = "",
		   selectedControl = "";
	
	/**
	 * Default constructor
	 * 
	 * @param mainWindow Instance of main class
	 */
	public ControlsEditorPanel(BufferedImage background, ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Setup
		int selectorWidth = (int) mainWindow.getDefaultSize().getWidth() / 3;
		selectorPanel = new ControlSelectorPanel(this, selectorWidth);
		rsp = new RightSidePanel(this, background);
		
		//Layout
		
		//Alignment
		
		//Size
		 selectorPanel.setPreferredSize(new Dimension(selectorWidth, (int) mainWindow.getDefaultSize().getHeight() - 50));
		
		//Add components
		add(selectorPanel);
		add(rsp);
	}
	
	/**
	 * Updates configuration variables
	 * 
	 * @param jo The JsonObject containing the config
	 */
	public void updateCfg(JsonObject jo) {
		config = jo;
		controlsConfig = config.getAsJsonObject("controls");
		
		selectorPanel.resetControls();
		
		for(String k : controlsConfig.keySet()) {
			if(k.equals("desc")) continue;
			selectorPanel.addControl(format(k));
		}
		
		ready = true;
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
			//Split by hyphen (x-axis to X-Axis and not X-axis)
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
	
	void save() {
		mainWindow.rcfg.setRobotConfig(config);
		
		try {
			mainWindow.rcfg.save();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public String getPanelName() {
		return "controls";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!ready) return;
		
		String command = e.getActionCommand().toLowerCase();
		System.out.println(command);
		
		//Command from the selectors
		if(command.contains("&")) {
			String v = command.split("&")[1];
			
			//Command is from a click
			if(previousCommand.contains("&") && previousCommand.split("&")[1].equals("null")) {
				if(command.contains("_")) { //underscores mean it was the control
					if(command.startsWith("buttons")) {
						selectorPanel.buttonsPanel.setValueSelection(0);
					} else if(command.startsWith("axes")) {
						// broke selectorPanel.axesPanel.setValueSelection(config.get(v).getAsInt());
					}
				} else {
					//Config changed, set unsaved
					mainWindow.windowCloser.setSaved(false);
				}
			} else if(!command.contains("null") && !command.contains("_")){
				if(command.startsWith("buttons")) {
					rsp.setImage(mainWindow.joyImages.get("joystick_buttons_" + v + ".jpg"));
				} else if(command.startsWith("axes")) {
					rsp.setImage(mainWindow.joyImages.get("joystick_axes_" + v + ".jpg"));
				}
				
				rsp.repaint();
			}
		} else {
			switch(command) {
				case "save":
					save();
					break;
				
				case "back":
					save();
					mainWindow.switchPanel("Main Menu");
					break;
			}
		}
		
		previousCommand = command;
	}
}
