package panels;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel that displays an image
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	JLabel label;
	
	/**
	 * Default constructor
	 * 
	 * @param img The image to display
	 */
	public ImagePanel(Image img) {
		label = new JLabel();
		label.setIcon(new ImageIcon(img));
		add(label);
	}
	
	/**
	 * Changes the displayed image
	 * 
	 * @param img The new image
	 */
	public void setImage(Image img) {
		remove(label);
		label.setIcon(new ImageIcon(img));
		add(label);
	}
}
