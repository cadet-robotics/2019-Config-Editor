package panels.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

/**
 * Class with methods to create spacers to separate components in the box layouts
 * 
 * @author Alex Pickering
 */
public class Spacer {
	
	/**
	 * Creates a component to separate sets of components
	 * (A rigidArea from Box)
	 * 
	 * @param height The height of the separator
	 * @return A rigidArea to separate things
	 */
	public static Component genSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
}
