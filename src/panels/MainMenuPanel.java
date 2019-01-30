package panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.ConfigEditor;

/**
 * Main menu to choose what to configure
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements NamedPanel {
	//Instance of the main class for switching
	ConfigEditor mainWindow;
	
	//Labels
	JLabel title = new JLabel("2019 Controls Editor");
	
	public MainMenuPanel(ConfigEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Layout and font
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	@Override
	public String getPanelName() {
		return "Main Menu";
	}
}
