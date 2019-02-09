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
 * Panel for editing the ports on the RIO
 * Most of this is copied from the ControlsEditorPanel, which was made first
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class PortEditorPanel extends JPanel implements ActionListener, NamedPanel {
	//Config instance
	JsonObject config;
	
	//Main instance
	ConfigEditor mainWindow;
	
	//Selector panel
	PortSelectorPanel selectorPanel;
	
	//Right image/buttons panel
	RightSidePanel rsp;
	
	boolean ready = false;
	String previousCommand = "";
	
	/**
	 * Default constructor
	 * 
	 * @param background Default image to show on the right
	 * @param mainWindow Instance of the main class
	 */
	public PortEditorPanel(BufferedImage background, ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Setup
		int selectorWidth = (int) mainWindow.getDefaultSize().getWidth() / 4;
		selectorPanel = new PortSelectorPanel(this, selectorWidth);
		rsp = new RightSidePanel(this, background);
		
		//Size
		selectorPanel.setPreferredSize(new Dimension(selectorWidth, (int) mainWindow.getDefaultSize().getHeight() - 50));
		
		//Add components
		add(selectorPanel);
		add(rsp);
	}
	
	/**
	 * Updates the configuration
	 * 
	 * @param jo The JsonObject containing the robot's config
	 */
	public void updateCfg(JsonObject jo) {
		config = jo;
		
		selectorPanel.resetItems();
		
		//Loop over each section and item
		for(String cat : config.keySet()) {
			if(cat.equals("pwm") || cat.equals("dio") || cat.equals("analog in")) {
				for(String k : config.get(cat).getAsJsonObject().keySet()) {
					if(k.equals("desc")) continue;
					selectorPanel.addItem(mainWindow.format(k), cat);
				}
			}
		}
		
		ready = true;
		
		//Update initial selections
		selectorPanel.pwmPanel.setValueSelection(config.get("pwm").getAsJsonObject().get(selectorPanel.pwmPanel.getItemSelection().toLowerCase()).getAsInt());
		selectorPanel.dioPanel.setValueSelection(config.get("dio").getAsJsonObject().get(selectorPanel.dioPanel.getItemSelection().toLowerCase()).getAsInt());
		selectorPanel.ainPanel.setValueSelection(config.get("analog in").getAsJsonObject().get(selectorPanel.ainPanel.getItemSelection().toLowerCase()).getAsInt());
		selectTabImage(0);
	}
	
	/**
	 * Saves the robot config
	 */
	void save() {
		mainWindow.rcfg.setRobotConfig(config);
		
		try {
			mainWindow.rcfg.save();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getPanelName() {
		return "ports";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!ready) return;
		
		String command = e.getActionCommand().toLowerCase();
		System.out.println(command);
		
		//Command is from the selectors
		if(command.contains("&")) {
			String v = command.split("&")[1];
			
			//Command is from a click
			if(previousCommand.contains("&") && previousCommand.split("&")[1].equals("null")) {
				if(command.contains("_")) { //Selected item updated
					if(command.startsWith("pwm")) {
						selectorPanel.pwmPanel.setValueSelection(config.get("pwm").getAsJsonObject().get(v).getAsInt());
					} else if(command.startsWith("dio")) {
						selectorPanel.dioPanel.setValueSelection(config.get("dio").getAsJsonObject().get(v).getAsInt());
					} else if(command.startsWith("ain")) {
						selectorPanel.ainPanel.setValueSelection(config.get("analog in").getAsJsonObject().get(v).getAsInt());
					}
				} else { //value updated
					if(command.startsWith("pwm")) {
						config.get("pwm").getAsJsonObject().remove(selectorPanel.pwmPanel.getItemSelection().toLowerCase());
						config.get("pwm").getAsJsonObject().addProperty(selectorPanel.pwmPanel.getItemSelection().toLowerCase(), Integer.parseInt(selectorPanel.pwmPanel.getValueSelection()));
					} else if(command.startsWith("dio")) {
						config.get("dio").getAsJsonObject().remove(selectorPanel.dioPanel.getItemSelection().toLowerCase());
						config.get("dio").getAsJsonObject().addProperty(selectorPanel.dioPanel.getItemSelection().toLowerCase(), Integer.parseInt(selectorPanel.dioPanel.getValueSelection()));
					} else if(command.startsWith("ain")) {
						config.get("analog in").getAsJsonObject().remove(selectorPanel.ainPanel.getItemSelection().toLowerCase());
						config.get("analog in").getAsJsonObject().addProperty(selectorPanel.ainPanel.getItemSelection().toLowerCase(), Integer.parseInt(selectorPanel.ainPanel.getValueSelection()));
					}
					
					mainWindow.windowCloser.setSaved(false);
				}
			}
			
			//Value highlighted of tab changed
			if(!command.contains("_") && !command.contains("null")) {
				if(command.startsWith("pwm")) {
					rsp.setImage(mainWindow.rioImages.get("roborio_pwm_" + v + ".jpg"));
				} else if(command.startsWith("dio")) {
					rsp.setImage(mainWindow.rioImages.get("roborio_dio_" + v + ".jpg"));
				} else if(command.startsWith("ain")) {
					rsp.setImage(mainWindow.rioImages.get("roborio_ani_" + v + ".jpg"));
				} else if(command.startsWith("tab")) {
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
			case 0: //PWM
				rsp.setImage(mainWindow.rioImages.get("roborio_pwm_" + selectorPanel.pwmPanel.getValueSelection() + ".jpg"));
				break;
			
			case 1: //DIO
				rsp.setImage(mainWindow.rioImages.get("roborio_dio_" + selectorPanel.dioPanel.getValueSelection() + ".jpg"));
				break;
			
			case 2: //AIn
				rsp.setImage(mainWindow.rioImages.get("roborio_ani_" + selectorPanel.ainPanel.getValueSelection() + ".jpg"));
				break;
		}
	}
}
