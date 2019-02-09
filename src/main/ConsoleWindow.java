package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
		setTitle("Deploy Output - Please wait for execution to finish");
		
		add(cp);
		
		setSize(500, 500);
		setVisible(true);
	}
	
	/**
	 * Adds the bufferedreader for console output
	 * 
	 * @param in The bufferedreader
	 */
	public void show(BufferedReader in) throws IOException {
		cp.show(in);
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
	JTextArea outputArea = new JTextArea("Deploying code...");
	
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
		scrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		
		add(scrollPane);
		add(exitButton);
	}
	
	/**
	 * Displays the console output
	 * 
	 * @param in The input reader
	 * @throws IOException 
	 */
	public void show(BufferedReader in) throws IOException {
		outputArea.setText("");
		
		//Show output
		while(true) {
			String line = in.readLine();
			if(line == null) {
				break;
			}
			
			outputArea.append(line + "\n");
		}
		
		parent.setTitle("Deploy Output");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "exit":
				parent.dispose();
		}
	}
}
