package panels;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Contains the things to select between buttons and axes,
 * and the selectors from each
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ControlSelectorPanel extends JTabbedPane {
	ControlsPanel buttonsPanel = new ControlsPanel(),
				  axesPanel = new ControlsPanel();
	
	public ControlSelectorPanel() {
		//Add tabs
		addTab("Buttons", buttonsPanel);
		addTab("Axes", axesPanel);
	}
}

/**
 * Contains the selectors for selecting the controls
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
class ControlsPanel extends JPanel {
	public ControlsPanel() {
		
	}
}