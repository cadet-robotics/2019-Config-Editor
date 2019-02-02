package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * WindowLister that checks if things are saved and gives
 * a confirmation dialogue if they aren't
 * 
 * @author Alex Pickering
 */
public class WindowCloser extends WindowAdapter {
	private JFrame fr;
	
	private boolean saved = true;
	
	/**
	 * Default constructor
	 * 
	 * @param fr The frame to work with
	 */
	public WindowCloser(JFrame fr) {
		this.fr = fr;
	}
	
	/**
	 * Sets whether or not things have been saved
	 * If this is false, a confirmation dialogue will show before exiting
	 * 
	 * @param s Whether or not things have been saved
	 */
	public void setSaved(boolean s) {
		saved = s;
	}
	
	/**
	 * Closes the window with a dialogue pop-up
	 * 
	 * @param msg The message in the dialogue
	 * @param title The title of the pop-up
	 * @param type The type of JOptionPane
	 */
	public void closeWithDialogue(String msg, String title, int type) {
		JOptionPane.showMessageDialog(null, msg, title, type);
		fr.dispose();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		int conf = JOptionPane.YES_OPTION;
		
		if(!saved) {
			conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit without saving?", "Exit Without Saving", JOptionPane.YES_NO_OPTION);
		}
		
		if(conf == JOptionPane.YES_OPTION) {
			fr.dispose();
		}
	}
}
