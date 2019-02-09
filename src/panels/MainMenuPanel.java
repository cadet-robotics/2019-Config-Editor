package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.ConfigEditor;
import main.ConsoleWindow;
import panels.components.Spacer;
/**
 * Main menu to choose what to configure
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements ActionListener, NamedPanel {
	//Instance of the main class for switching
	ConfigEditor mainWindow;
	
	//Labels
	JLabel title = new JLabel("Robot Config Editor"),
		   subtitle = new JLabel("2019 Edition");
	
	//Buttons
	JButton swapControls = new JButton("Edit Controls") {{setSize(100, 50); setMaximumSize(getSize());}},
			swapPorts = new JButton("Edit Ports") {{setSize(100, 50); setMaximumSize(getSize());}},
			deployConfigButton = new JButton("Deploy Config") {{setSize(100, 50); setMaximumSize(getSize());}},
			exitButton = new JButton("Exit") {{setSize(100, 50); setMaximumSize(getSize());}};
	
	public MainMenuPanel(ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Layout and font
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title.setFont(title.getFont().deriveFont(26f));
		subtitle.setFont(subtitle.getFont().deriveFont(16f));
		
		//Commands
		swapControls.setActionCommand("button_controls");
		swapPorts.setActionCommand("button_ports");
		deployConfigButton.setActionCommand("button_deploy");
		exitButton.setActionCommand("button_exit");
		
		//Listeners
		swapControls.addActionListener(this);
		swapPorts.addActionListener(this);
		deployConfigButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		//Alignment
		title.setAlignmentX(CENTER_ALIGNMENT);
		subtitle.setAlignmentX(CENTER_ALIGNMENT);
		
		swapControls.setAlignmentX(CENTER_ALIGNMENT);
		swapPorts.setAlignmentX(CENTER_ALIGNMENT);
		deployConfigButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add components
		add(title);
		add(subtitle);
		add(Spacer.genSpacer(25));
		
		add(swapControls);
		add(swapPorts);
		add(Spacer.genSpacer(20));
		
		add(deployConfigButton);
		add(Spacer.genSpacer(250));
		
		//TODO: Place exit button at the bottom
		add(exitButton);
	}
	
	/**
	 * Deploys the robot code with the new config
	 */
	void deployConfig() {
		HashMap<String, String> conf = mainWindow.pcfg.getConfig();
		ProcessBuilder pb;
		if(conf.get("stacktrace").equals("true")) {
			pb = new ProcessBuilder("cmd.exe", "/c", "gradlew", "deploy", "-PteamNumber=" + conf.get("team_number"), "--offline", "--stacktrace", "-Dorg.gradle.java.home=\"" + conf.get("java_home") + "\"");
		} else {
			pb = new ProcessBuilder("cmd.exe", "/c", "gradlew", "deploy", "-PteamNumber=" + conf.get("team_number"), "--offline", "-Dorg.grade.java.home=\"" + conf.get("java_home") + "\"");
		}
		
		pb.directory(new File(conf.get("project_dir")));
		pb.redirectErrorStream(true);
		
		ConsoleWindow outputWindow = new ConsoleWindow();
		
		try {
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			outputWindow.show(br);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "IOException while deploying", "IOException", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "button_exit":
				mainWindow.close();
				break;
			
			case "button_controls":
				mainWindow.switchPanel("controls");
				break;
			
			case "button_ports":
				mainWindow.switchPanel("ports");
				break;
			
			case "button_deploy":
				deployConfig();
		}
	}
	
	@Override
	public String getPanelName() {
		return "Main Menu";
	}
}
