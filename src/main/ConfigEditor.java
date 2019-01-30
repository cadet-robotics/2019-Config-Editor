package main;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import panels.MainMenuPanel;

/**
 * Program to edit the robot controls and ports from a GUI
 * 
 * @author Alex Pickering
 */
public class ConfigEditor {
	//Main window
	JFrame mainFrame = new JFrame("2019 Controls Editor");
	JPanel mainPanel = new JPanel(new CardLayout());
	
	//Panels to switch between
	public MainMenuPanel mainMenu = new MainMenuPanel(this);
	
	WindowCloser windowCloser = new WindowCloser(mainFrame);
	
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
		createWindow();
		
		switchPanel(mainMenu.getPanelName());
	}
	
	public void switchPanel(String panelName) {
		((CardLayout)(mainPanel.getLayout())).show(mainPanel, panelName);
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
		
		//Make the thing visible
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
