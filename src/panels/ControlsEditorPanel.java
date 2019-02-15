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
	JsonObject config;
	
	//Main instance
	ConfigEditor mainWindow;
	
	//Selector panel
	ControlSelectorPanel selectorPanel;
	
	//Panel on the rigt
	RightSidePanel rsp;
	
	boolean ready = false;
	String previousCommand = "";
	
	/**
	 * Default constructor
	 * 
	 * @param background Default image to show on the right
	 * @param mainWindow Instance of main class
	 */
	public ControlsEditorPanel(BufferedImage background, ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Setup
		int selectorWidth = (int) mainWindow.getDefaultSize().getWidth() / 3;
		selectorPanel = new ControlSelectorPanel(this, selectorWidth);
		rsp = new RightSidePanel(this, background);
		
		//Size
		 selectorPanel.setPreferredSize(new Dimension(selectorWidth, (int) mainWindow.getDefaultSize().getHeight() - 50));
		
		//Add components
		add(selectorPanel);
		add(rsp);
	}
	
	/**
	 * Updates configuration variables
	 * 
	 * @param jo The JsonObject containing the robot's config
	 */
	public void updateCfg(JsonObject jo) {
		config = jo.getAsJsonObject("controls");
		
		selectorPanel.resetControls();
		
		for(String k : config.keySet()) {
			if(k.equals("desc")) continue;
			selectorPanel.addControl(mainWindow.format(k));
		}
		
		selectorPanel.sortControls();
		
		ready = true;
		
		//Update initial selections
		selectorPanel.axesPanel.setValueSelection(config.get(selectorPanel.axesPanel.getControlSelection().toLowerCase()).getAsInt());
		selectorPanel.buttonsPanel.setValueSelection(config.get(selectorPanel.buttonsPanel.getControlSelection().toLowerCase()).getAsInt());
		selectTabImage(0);
	}
	
	/**
	 * Saves the robot config
	 */
	void save() {
		JsonObject newRobotConfig = mainWindow.rcfg.getRobotConfig();
		
		newRobotConfig.remove("controls");
		newRobotConfig.add("controls", config);
		
		mainWindow.rcfg.setRobotConfig(newRobotConfig);
		
		try {
			mainWindow.rcfg.save();
		} catch(IOException e) {
			e.printStackTrace();
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
		System.out.println(previousCommand);
		System.out.println(command);
		
		//Command from the selectors
		if(command.contains("&")) {
			String v = command.split("&")[1];
			
			//Command is from a click
			if(previousCommand.contains("&") && previousCommand.split("&")[1].equals("null")) {
				if(command.contains("_")) { //underscores mean it was the control, updated selected value to match config
					if(command.startsWith("buttons")) {
						selectorPanel.buttonsPanel.setValueSelection(config.get(v).getAsInt());
					} else if(command.startsWith("axes")) {
						selectorPanel.axesPanel.setValueSelection(config.get(v).getAsInt());
					}
				} else { //Changed the selected value, update config to match
					if(command.startsWith("buttons")) {
						config.remove(selectorPanel.buttonsPanel.getControlSelection().toLowerCase());
						config.addProperty(selectorPanel.buttonsPanel.getControlSelection().toLowerCase(), Integer.parseInt(selectorPanel.buttonsPanel.getValueSelection()));
					} else if(command.startsWith("axes")) {
						config.remove(selectorPanel.axesPanel.getControlSelection().toLowerCase());
						config.addProperty(selectorPanel.axesPanel.getControlSelection().toLowerCase(), Integer.parseInt(selectorPanel.axesPanel.getValueSelection()));
					}
						
					//Config changed, set unsaved
					mainWindow.windowCloser.setSaved(false);
				}
			}
			
			if(!command.contains("_") && !command.contains("null")) {
				if(command.startsWith("buttons")) {
					rsp.setImage(mainWindow.joyImages.get("joystick_buttons_" + v + ".jpg"));
				} else if(command.startsWith("axes")) {
					rsp.setImage(mainWindow.joyImages.get("joystick_axes_" + v + ".jpg"));
				} else if(command.startsWith("tab")) { //Switched tab, update image
					selectTabImage(Integer.parseInt(v));
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
					ready = false;
					mainWindow.switchPanel("Main Menu");
					break;
			}
		}
		
		previousCommand = command;
	}
	
	/**
	 * Selects the current image based on a tab change
	 * 
	 * @param i The new index
	 */
	void selectTabImage(int i) {
		switch(i) {
			case 0:	//buttons
				rsp.setImage(mainWindow.joyImages.get("joystick_buttons_" + selectorPanel.buttonsPanel.getValueSelection() + ".jpg"));
				break;
			
			case 1:	//axes
				rsp.setImage(mainWindow.joyImages.get("joystick_axes_" + selectorPanel.axesPanel.getValueSelection() + ".jpg"));
				break;
		}
	}
}
