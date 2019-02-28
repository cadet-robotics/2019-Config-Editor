package panels;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import panels.components.HighlightingComboBox;
import panels.components.Spacer;

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

/**
 * Contains the things to select between PWM, analog in, and DIO, and the
 * selectors from each
 * Mostly copied from ControlSelectorPanel, which was done first
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class PortSelectorPanel extends JTabbedPane {
	public PortsPanel pwmPanel,
					  dioPanel,
					  ainPanel,
					  pcmPanel;
	
	PortEditorPanel pep;
	
	/**
	 * Default constructor
	 * 
	 * @param pep Instance of the editor panel
	 * @param width Width for use with the combo boxes
	 */
	public PortSelectorPanel(PortEditorPanel pep, int width) {
		this.pep = pep;
		
		//Setup IDs
		List<Integer> pwmIDs = intToList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
					  dioIDs = intToList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
					  ainIDs = intToList(new int[] {0, 1, 2, 3}),
					  pcmIDs = intToList(new int[] {0, 1, 2, 3, 4, 5, 6, 7});
		
		//Setup panels with selectors
		pwmPanel = new PortsPanel("Select a PWM item to edit", "pwm", pwmIDs, pep, width - 20);
		dioPanel = new PortsPanel("Select a DIO item to edit", "dio", dioIDs, pep, width - 20);
		ainPanel = new PortsPanel("Select an AIn item to edit", "ain", ainIDs, pep, width - 20);
		pcmPanel = new PortsPanel("Select a PCM item to edit", "pcm", pcmIDs, pep, width - 20);
		
		//Add tabs
		addTab("PWM", pwmPanel);
		addTab("DIO", dioPanel);
		addTab("AIn", ainPanel);
		addTab("PCM", pcmPanel);
		
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pep.actionPerformed(new ActionEvent(this, 0, "tab&" + getSelectedIndex()));
			}
		});
	}
	
	/**
	 * Resets each tab of items
	 * Intended to be used before adding items for a reload
	 */
	public void resetItems() {
		pwmPanel.resetItems();
		dioPanel.resetItems();
		ainPanel.resetItems();
		pcmPanel.resetItems();
	}
	
	/**
	 * Adds an item to the proper tab's list
	 * 
	 * @param name The name of the control
	 * @paran origin The name of the section of the config the item came from
	 */
	public void addItem(String name, String origin) {
		switch(origin) {
			case "pwm":
				pwmPanel.addItem(name);
				break;
			
			case "dio":
				dioPanel.addItem(name);
				break;
			
			case "analog in":
				ainPanel.addItem(name);
				break;
			
			case "pcm":
				pcmPanel.addItem(name);
				break;
		}
	}
	
	/**
	 * Sorts the items
	 */
	public void sortItems() {
		pwmPanel.sort();
		dioPanel.sort();
		ainPanel.sort();
	}
	
	/**
	 * Converts an array of ints into a List of Integers
	 * 
	 * @param ar The array to convert
	 * @return The List
	 */
	public List<Integer> intToList(int[] ar){
		return Arrays.stream(ar).boxed().collect(Collectors.toList());
	}
}

/**
 * Contains the selectors for selecting the ports
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
class PortsPanel extends JPanel {
	HighlightingComboBox<Integer> valueSelectorBox;
	HighlightingComboBox<String> itemSelectorBox;
	
	JLabel infoLabel,
		   valueLabel;
	
	public PortsPanel(String info, String category, List<Integer> range, PortEditorPanel pep, int width) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Setup selectors
		itemSelectorBox = new HighlightingComboBox<>(pep, category + "_");
		itemSelectorBox.setMaximumRowCount(25);
		itemSelectorBox.setMaximumSize(new Dimension(width, 30));
		itemSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		valueSelectorBox = new HighlightingComboBox<>(pep, category);
		valueSelectorBox.setMaximumRowCount(25);
		valueSelectorBox.setMaximumSize(new Dimension(width, 30));
		valueSelectorBox.setAlignmentX(CENTER_ALIGNMENT);
		
		range.forEach(v -> valueSelectorBox.addItem(v));
		
		//Setup labels
		infoLabel = new JLabel(info);
		infoLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		valueLabel = new JLabel("Select the value of the item");
		valueLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add components
		add(Spacer.genSpacer(10));
		add(infoLabel);
		add(itemSelectorBox);
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
		for(int i = 0; i < itemSelectorBox.getItemCount(); i++) {
			items.add(itemSelectorBox.getItemAt(i));
		}
		
		itemSelectorBox.removeAllItems();
		
		Collections.sort(items);
		
		//Add items back
		for(String s : items) {
			itemSelectorBox.addItem(s);
		}
	}
	
	/**
	 * Gets the current selection from the item selector
	 * 
	 * @return The selected item name
	 */
	public String getItemSelection() {
		return itemSelectorBox.getSelectedItem().toString();
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
	 * @param index The item to be selected
	 */
	public void setValueSelection(int item) {
		valueSelectorBox.setSelectedItem(item);
	}
	
	/**
	 * Adds an item to the item selector
	 * 
	 * @param itemName The name of the item
	 */
	public void addItem(String itemName) {
		itemSelectorBox.addItem(itemName);
	}
	
	/**
	 * Removes all item values
	 */
	public void resetItems() {
		itemSelectorBox.removeAllItems();
	}
}