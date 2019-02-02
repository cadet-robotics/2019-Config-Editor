package main;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panels.ControlsEditorPanel;

/**
 * Contains the combo box and information on a button or axis on the joystick
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class JoystickPart extends JPanel {
	String buttonTitle,				//The title of this button (ex - Button 1, X Axis)
		   currentControl;			//The current config JSON key that maps to this
	
	int controlID;					//The number of the control, comes from the driver station
	
	ArrayList<String> controlKeys;	//The possible keys that could map to this
	
	JComboBox<String> dropdown;		//The combo box that has the options for what this controls
	
	JLabel title;
	
	/**
	 * Default constructor
	 * 
	 * @param title The title of the mini menu
	 * @param id The ID of the button this maps to
	 */
	public JoystickPart(String t, int id, ControlsEditorPanel cep) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		buttonTitle = t;
		controlID = id;
		
		currentControl = "none";
		
		title = new JLabel(t);
		title.setFont(title.getFont().deriveFont(12));
		
		dropdown = new JComboBox<>();
		dropdown.setActionCommand("combobox_" + buttonTitle);
		dropdown.addActionListener(cep);
		
		add(title);
		add(dropdown);
	}
	
	/**
	 * Updates the drop down menu to reflect the available keys
	 * 
	 * @param keys The list of keys from the JSON config
	 */
	public void updateDropdown(ArrayList<String> keys) {
		controlKeys = keys;
		
		dropdown.removeAllItems();
		for(String s : keys) dropdown.addItem(s);
		
		dropdown.addItem("None");
		dropdown.setSelectedItem("None");
	}
	
	/**
	 * Gets the button's (or axis') id
	 * @return The id
	 */
	public int getID() {
		return controlID;
	}
	
	/**
	 * Gets the JSON tag of the selected control
	 * @return The selected control
	 */
	public String getControl() {
		return currentControl;
	}
}
