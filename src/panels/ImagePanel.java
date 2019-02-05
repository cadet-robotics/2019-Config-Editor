package panels;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel that displays an image
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	/**
	 * Default constructor
	 * @param img The image to display
	 */
	public ImagePanel(BufferedImage img) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(img));
		add(label);
	}
}
