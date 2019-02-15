package panels;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Contains the right-side image and the save/back buttons
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class RightSidePanel extends JPanel {
	JPanel topPanel;
	ImagePanel img;
	
	boolean sizeSet = false;
	
	/**
	 * Default constructor
	 * 
	 * @param parent The parent object of this
	 * @param i The image to display
	 */
	public RightSidePanel(JPanel parent, Image i) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.img = new ImagePanel(i);
		
		JButton save = new JButton("Save"),
				back = new JButton("Back");
		
		save.setActionCommand("save");
		back.setActionCommand("back");
		
		//Things that could be parents of this
		if(parent instanceof ControlsEditorPanel) {
			save.addActionListener((ControlsEditorPanel) parent);
			back.addActionListener((ControlsEditorPanel) parent);
		} else if(parent instanceof PortEditorPanel) {
			save.addActionListener((PortEditorPanel) parent);
			back.addActionListener((PortEditorPanel) parent);
		}
		
		topPanel = new JPanel();
		topPanel.add(save);
		topPanel.add(back);
		
		add(topPanel);
		add(img);
	}
	
	/**
	 * Changes the displayed image
	 * 
	 * @param i The new image
	 */
	public void setImage(Image i) {
		img.setImage(i);
		
		if(!sizeSet) {
			setPreferredSize(getSize());
			sizeSet = true;
		}
	}
}
