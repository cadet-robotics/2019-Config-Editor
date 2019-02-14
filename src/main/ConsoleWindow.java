package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A window for console output while deploying
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
public class ConsoleWindow extends JFrame {
	ConsolePanel cp;
	
	/**
	 * Default constructor
	 * 
	 * @param in The reader from the process
	 * @throws IOException 
	 */
	public ConsoleWindow() {
		cp = new ConsolePanel(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setTitle("Deploy Output - Please wait");
		
		add(cp);
		
		setSize(500, 500);
		setVisible(true);
	}
	
	/**
	 * Gets the output area
	 * 
	 * @return The output area
	 */
	public JTextArea getOutputArea() {
		return cp.getOutputArea();
	}
	
	/**
	 * Gets the panel
	 * 
	 * @return le panel
	 */
	public JPanel getPanel() {
		return cp;
	}
}

/**
 * Contains the console output and close button
 * 
 * @author Alex Pickering
 */
@SuppressWarnings("serial")
class ConsolePanel extends JPanel implements ActionListener {
	JFrame parent;
	
	JScrollPane scrollPane;
	JTextArea outputArea = new JTextArea();
	
	JButton exitButton = new JButton("Close Window");
	
	/**
	 * Default constructor
	 * 
	 * @param in The reader from the process
	 * @throws IOException 
	 */
	public ConsolePanel(JFrame parent) {
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		outputArea.setEditable(false);
		
		scrollPane = new JScrollPane(outputArea);
		scrollPane.setAutoscrolls(true);
		scrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		add(scrollPane);
		add(exitButton);
	}
	
	/**
	 * Gets the output area
	 * 
	 * @return The output area
	 */
	public JTextArea getOutputArea() {
		return outputArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "exit":
				parent.dispose();
		}
	}
}
