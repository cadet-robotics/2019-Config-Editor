package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public WindowCloser windowCloser = new WindowCloser(mainFrame);
	
	//Images of the RIO and joystick
	public HashMap<String, BufferedImage> rioImages = new HashMap<>(),
										  joyImages = new HashMap<>();
	
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
		
		createWindow();
		
		switchPanel(mainMenu.getPanelName());
		
		pcfg = new ProgramConfig(this);
		rcfg = new RobotConfig(this);
		
		controlsPanel.updateCfg(rcfg.getRobotConfig());
	}
	
	/**
	 * Switches the view to the specified panel
	 * 
	 * @param panelName The name of the panel to switch to
	 */
	public void switchPanel(String panelName) {
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
		mainFrame.addWindowListener(windowCloser);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.add(mainPanel);
		
		//Add panels to switch between
		mainPanel.add(mainMenu, mainMenu.getPanelName());
		mainPanel.add(controlsPanel, controlsPanel.getPanelName());
		
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
		} catch(IOException e) {
			System.out.println("IOException while reading images");
			e.printStackTrace();
			
			windowCloser.closeWithDialogue("Failed to load images", "Loading Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
