package panels;

/**
 * Inteface so that panels have names
 * 
 * @author Alex Pickering
 */
public interface NamedPanel {
	/**
	 * Gets the name of the panel for use in switching panels
	 * 
	 * @return The name of the panel
	 */
	abstract String getPanelName();
}
