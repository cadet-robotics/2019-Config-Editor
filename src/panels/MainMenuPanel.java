package panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.ConfigEditor;
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
	JLabel title = new JLabel("Robot Controls Editor"),
		   subtitle = new JLabel("2019 Edition");
	
	//Buttons
	JButton swapControls = new JButton("Edit Controls") {{setSize(100, 50); setMaximumSize(getSize());}},
			swapPorts = new JButton("Edit Ports") {{setSize(100, 50); setMaximumSize(getSize());}},
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
		exitButton.setActionCommand("button_exit");
		
		//Listeners
		swapControls.addActionListener(this);
		swapPorts.addActionListener(this);
		exitButton.addActionListener(this);
		
		//Alignment
		title.setAlignmentX(CENTER_ALIGNMENT);
		subtitle.setAlignmentX(CENTER_ALIGNMENT);
		
		swapControls.setAlignmentX(CENTER_ALIGNMENT);
		swapPorts.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add components
		add(title);
		add(subtitle);
		add(Spacer.genSpacer(25));
		
		add(swapControls);
		add(swapPorts);
		add(Spacer.genSpacer(100));
		
		//TODO: Place exit button at the bottom
		add(exitButton);
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
		}
	}
	
	@Override
	public String getPanelName() {
		return "Main Menu";
	}
}
