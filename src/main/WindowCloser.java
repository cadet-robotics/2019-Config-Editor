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
	
	public WindowCloser(JFrame fr) {
		this.fr = fr;
	}
	
	public void setSaved(boolean s) {
		saved = s;
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
