package panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import panels.components.HighlightingComboBox;
import panels.components.Spacer;

/**
 * Contains the things to select between buttons and axes,
 * and the selectors from each
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ControlSelectorPanel extends JTabbedPane {
	public ControlsPanel buttonsPanel,
						 axesPanel;
	
	ControlsEditorPanel cep;
	
	/**
	 * Default constructor
	 * 
	 * @param cep Instance of the editor panel
	 * @param width Width for use with the combo boxes
	 */
	public ControlSelectorPanel(ControlsEditorPanel cep, int width) {
		this.cep = cep;
		
		//Setup IDs
		List<Integer> buttonIDs = intToList(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}),
					  axisIDs = intToList(new int[] {0, 1, 2, 3});
		
		//Setup panels with the selectors
		buttonsPanel = new ControlsPanel("Select a control to edit", "buttons", buttonIDs, cep, width - 20);
		axesPanel = new ControlsPanel("Select an axis to edit", "axes", axisIDs, cep, width - 20);
		
		//Add tabs
		addTab("Buttons", buttonsPanel);
		addTab("Axes", axesPanel);
		
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cep.actionPerformed(new ActionEvent(this, 0, "tab&" + getSelectedIndex()));
			}
		});
	}
	
	/**
	 * Resets each tab of controls
	 * Intended to be used before adding controls for a reload
	 */
	public void resetControls() {
		buttonsPanel.resetControls();
		axesPanel.resetControls();
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
		} else if(!checkOther(lName)) {
			buttonsPanel.addControl(name);
		}
	}
	
	/**
	 * Sorts the control names alphabetically
	 */
	public void sortControls() {
		axesPanel.sort();
		buttonsPanel.sort();
	}
	
	/**
	 * Organization method for checking if a control is in the 'other' section
	 * 
	 * @param name The name of the control
	 * @return Whether or not this is in the 'other' section
	 */
	boolean checkOther(String name) {
		if(name.equals("main joystick")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Converts an array of integers to a List of Integers
	 * 
	 * @param ar The array to convert
	 * @return The list
	 */
	public List<Integer> intToList(int[] ar) {
		return Arrays.stream(ar).boxed().collect(Collectors.toList());
	}
}

/**
 * Contains the selectors for selecting the controls
 * 
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
		
		//Setup selectors
		controlSelectorBox = new HighlightingComboBox<>(cep, category + "_");
		controlSelectorBox.setMaximumRowCount(25);
		controlSelectorBox.setMaximumSize(new Dimension(width, 30));
		controlSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		valueSelectorBox = new HighlightingComboBox<>(cep, category);
		valueSelectorBox.setMaximumRowCount(25);
		valueSelectorBox.setMaximumSize(new Dimension(width, 30));
		valueSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		range.forEach(v -> valueSelectorBox.addItem(v));
		
		//Setup labels
		infoLabel = new JLabel(info);
		infoLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		valueLabel = new JLabel("Select the value of the control");
		valueLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add components
		add(Spacer.genSpacer(10));
		add(infoLabel);
		add(controlSelectorBox);
		add(Spacer.genSpacer(20));
		add(valueLabel);
		add(valueSelectorBox);
		
	}
	
	/**
	 * Sorts combobox entries
	 */
	public void sort() {
		List<String> items = new ArrayList<>();
		
		//Get items
		for(int i = 0; i < controlSelectorBox.getItemCount(); i++) {
			items.add(controlSelectorBox.getItemAt(i));
		}
		
		controlSelectorBox.removeAllItems();
		
		Collections.sort(items);
		
		//Add items back
		for(String s : items) {
			controlSelectorBox.addItem(s);
		}
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
	 * @param item The item to be selected
	 */
	public void setValueSelection(int item) {
		valueSelectorBox.setSelectedItem(item);
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