package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComboBox;
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
	
	//Background image
	BufferedImage background;
	
	//Others
	Dimension jcbSize = new Dimension(125, 40);
	
	boolean axes = false; //Whether or not axes are being edited
	
	/**
	 * Default constructor
	 * 
	 * @param mainWindow Instance of main class
	 */
	public ControlsEditorPanel(BufferedImage background, ConfigEditor mainWindow) {
		this.background = background;
		this.mainWindow = mainWindow;
		
		//Layout
		setLayout(null);
	}
	
	/**
	 * Updates configuration variables
	 * 
	 * @param jo The JsonObject containing the config
	 */
	public void updateCfg(JsonObject jo) {
		config = jo;
		controlsConfig = config.getAsJsonObject("controls");
		
		//axisControls.clear();
		//buttonControls.clear();
		
		for(String k : controlsConfig.keySet()) {
			if(k.equals("desc")) continue;
			if(k.contains("axis")) {
				//axisControls.add(k);
			} else {
				//buttonControls.add(k);
			}
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
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		
		Graphics2D g = (Graphics2D) g1;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int centX = (getWidth() - background.getWidth()) / 2,
			centY = (getHeight() - background.getHeight()) / 2;
		
		g.drawImage(background, centX, centY, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
