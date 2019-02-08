package panels;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import panels.components.HighlightingComboBox;
import panels.components.Spacer;

/**
 * Contains the things to select between buttons and axes,
 * and the selectors from each
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ControlSelectorPanel extends JTabbedPane {
	public ControlsPanel buttonsPanel,
						 axesPanel,
						 otherPanel;
	
	ControlsEditorPanel cep;
	
	/**
	 * Default constructor
	 * 
	 * @param cep Instance of the editor panel
	 * @param width Width for use with the combo boxes
	 */
	public ControlSelectorPanel(ControlsEditorPanel cep, int width) {
		this.cep = cep;
		
		List<Integer> buttonIDs = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
					  axisIDs = List.of(0, 1, 2, 3),
					  otherIDs = List.of(1);
		
		buttonsPanel = new ControlsPanel("Select a control to edit", "buttons", buttonIDs, cep, width - 20);
		axesPanel = new ControlsPanel("Select an axis to edit", "axes", axisIDs, cep, width - 20);
		otherPanel = new ControlsPanel("Select an item to edit", "other", otherIDs, cep, width - 20);
		
		//Add tabs
		addTab("Buttons", buttonsPanel);
		addTab("Axes", axesPanel);
		addTab("Other", otherPanel);
	}
	
	/**
	 * Resets each tab of controls
	 * Intended to be used before adding controls for a reload
	 */
	public void resetControls() {
		buttonsPanel.resetControls();
		axesPanel.resetControls();
		otherPanel.resetControls();
	}
	
	/**
	 * Adds a control to the proper tab's list
	 * 
	 * @param name The name of the control
	 */
	public void addControl(String name) {
		String lName = name.toLowerCase();
		if(lName.contains("axis")) {
			axesPanel.addControl(name);
		} else if(checkOther(lName)) {
			otherPanel.addControl(name);
		} else {
			buttonsPanel.addControl(name);
		}
	}
	
	/**
	 * Organization method for checking if a control is in the 'other' section
	 * 
	 * @param name The name of the control
	 * @return Whether or not this is in the 'other' section
	 */
	boolean checkOther(String name) {
		if(name.contains("joystick")) {
			return true;
		}
		
		return false;
	}
}

/**
 * Contains the selectors for selecting the controls
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
class ControlsPanel extends JPanel {
	HighlightingComboBox<Integer> valueSelectorBox;
	HighlightingComboBox<String> controlSelectorBox;
	JLabel infoLabel,
		   valueLabel;
	
	public ControlsPanel(String info, String category, List<Integer> range, ControlsEditorPanel cep, int width) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		controlSelectorBox = new HighlightingComboBox<>(cep, category + "_");
		controlSelectorBox.setMaximumSize(new Dimension(width, 30));
		controlSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		valueSelectorBox = new HighlightingComboBox<>(cep, category);
		valueSelectorBox.setMaximumSize(new Dimension(width, 30));
		valueSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		range.forEach(v -> valueSelectorBox.addItem(v));
		
		this.infoLabel = new JLabel(info);
		infoLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		valueLabel = new JLabel("Select the value of the control");
		valueLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		add(Spacer.genSpacer(10));
		add(infoLabel);
		add(controlSelectorBox);
		add(Spacer.genSpacer(20));
		add(valueLabel);
		add(valueSelectorBox);
		
	}
	
	/**
	 * Gets the current selection from the control selector
	 * 
	 * @return The selected control name
	 */
	public String getControlSelection() {
		return controlSelectorBox.getSelectedItem().toString();
	}
	
	/**
	 * Gets the current selection from the value selector
	 * 
	 * @return The selected value
	 */
	public String getValueSelection() {
		return valueSelectorBox.getSelectedItem().toString();
	}
	
	/**
	 * Sets which value is selected
	 * 
	 * @param item The name
	 */
	public void setValueSelection(int index) {
		valueSelectorBox.setSelectedItem(index);
	}
	
	
	/**
	 * Adds a control to the control selector
	 * 
	 * @param controlName The name of the control
	 */
	public void addControl(String controlName) {
		controlSelectorBox.addItem(controlName);
	}
	
	/**
	 * Removes all control values
	 */
	public void resetControls() {
		controlSelectorBox.removeAllItems();
	}
}