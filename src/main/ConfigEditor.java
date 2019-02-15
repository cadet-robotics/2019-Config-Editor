package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import config.ProgramConfig;
import config.RobotConfig;
import panels.ControlsEditorPanel;
import panels.MainMenuPanel;
import panels.PortEditorPanel;

/**
 * Program to edit the robot controls and ports from a GUI
 * 
 * @author Alex Pickering
 */
public class ConfigEditor {
	//Config
	public ProgramConfig pcfg;
	public RobotConfig rcfg;
	
	//Main window
	JFrame mainFrame = new JFrame("2019 Controls Editor");
	JPanel mainPanel = new JPanel(new CardLayout());
	
	//Panels to switch between
	public MainMenuPanel mainMenu = new MainMenuPanel(this);
	public ControlsEditorPanel controlsPanel;
	public PortEditorPanel portPanel;
	
	public WindowCloser windowCloser = new WindowCloser(mainFrame);
	
	//Images of the RIO and joystick
	public HashMap<String, BufferedImage> rioImages = new HashMap<>(),
										  joyImages = new HashMap<>(),
										  pcmImages = new HashMap<>();
	
	//Other
	static final Dimension defaultSize = new Dimension(750, 600);
	
	public static void main(String[] args) {
		//Get better look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//Run in a non-static method
		new ConfigEditor();
	}
	
	/**
	 * Essentially main, but better
	 */
	public ConfigEditor() {
		loadImages();
		
		controlsPanel = new ControlsEditorPanel(joyImages.get("joy_def"), this);
		portPanel = new PortEditorPanel(rioImages.get("rio_def"), this);
		
		createWindow();
		
		switchPanel(mainMenu.getPanelName());
		
		pcfg = new ProgramConfig(this);
		rcfg = new RobotConfig(this);
	}
	
	/**
	 * Switches the view to the specified panel
	 * Updates the robot config for each editor as well, for when
	 * it switches to them
	 * 
	 * @param panelName The name of the panel to switch to
	 */
	public void switchPanel(String panelName) {
		if(panelName.equals(controlsPanel.getPanelName())) {
			controlsPanel.updateCfg(rcfg.getRobotConfig());
			windowCloser.setSaved(true);
		} else if(panelName.equals(portPanel.getPanelName())) {
			portPanel.updateCfg(rcfg.getRobotConfig());
			windowCloser.setSaved(true);
		}
		
		((CardLayout)(mainPanel.getLayout())).show(mainPanel, panelName);
	}
	
	/**
	 * Gets the window frame
	 * 
	 * @return The main frame
	 */
	public JFrame getFrame() {
		return mainFrame;
	}
	
	public Dimension getDefaultSize() {
		return defaultSize;
	}
	
	/**
	 * Closes the window safely
	 */
	public void close() {
		windowCloser.windowClosing(new WindowEvent(mainFrame, 0));
	}
	
	/**
	 * Creates the window and sets up the panel(s)
	 */
	void createWindow() {
		//Setup window
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(windowCloser);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.add(mainPanel);
		
		//Add panels to switch between
		mainPanel.add(mainMenu, mainMenu.getPanelName());
		mainPanel.add(controlsPanel, controlsPanel.getPanelName());
		mainPanel.add(portPanel, portPanel.getPanelName());
		
		//Make the thing visible
		mainFrame.setSize(defaultSize);
		mainFrame.setVisible(true);
	}
	
	void loadImages() {
		try {
			//Get joystick images
			joyImages.put("joy_def", ImageIO.read(new File("dat/joystick/joystick.jpg")));
			for(File f : new File("dat/joystick/axes").listFiles())
				joyImages.put(f.getName(), ImageIO.read(f));
			for(File f : new File("dat/joystick/buttons").listFiles())
				joyImages.put(f.getName(), ImageIO.read(f));
			
			//Get rio images
			rioImages.put("rio_def", ImageIO.read(new File("dat/roborio/roborio.jpg")));
			for(File f : new File("dat/roborio/dio").listFiles())
				rioImages.put(f.getName(), ImageIO.read(f));
			for(File f : new File("dat/roborio/pwm").listFiles())
				rioImages.put(f.getName(), ImageIO.read(f));
			for(File f: new File("dat/roborio/ani").listFiles())
				rioImages.put(f.getName(), ImageIO.read(f));
			
			//Get PCM images
			pcmImages.put("pcm_def", ImageIO.read(new File("dat/pcm/pcm_def.jpg")));
			for(File f : new File("dat/pcm").listFiles())
				pcmImages.put(f.getName(), ImageIO.read(f));
		} catch(IOException e) {
			System.out.println("IOException while reading images");
			e.printStackTrace();
			
			windowCloser.closeWithDialogue("Failed to load images", "Loading Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Formats a name into a more 'readable' version
	 * 
	 * @param s The string to format
	 * @return The formatted string
	 */
	public String format(String str) {
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
}
