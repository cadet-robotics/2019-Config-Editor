package panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.ControlsEditor;

/**
 * Main menu to choose what to configure
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements NamedPanel {
	//Instance of the main class for switching
	ControlsEditor mainWindow;
	
	//Labels
	JLabel title = new JLabel("2019 Controls Editor");
	
	public MainMenuPanel(ControlsEditor mainWindow) {
		this.mainWindow = mainWindow;
		
		//Layout and font
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	@Override
	public String getPanelName() {
		return "Main Menu";
	}
}
