package panels.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;

/**
 * A JComboBox that has a listener for the highlighted item
 * 
 * Mostly taken from a Stack Overflow answer
 * @author kleopatra
 */
@SuppressWarnings("serial")
public class HighlightingComboBox<T> extends JComboBox<T> {
	ActionListener al;
	String id;
	
	private ListSelectionListener listener;
	
	/**
	 * Default constructor
	 * 
	 * @param al The listener to fire events to
	 * @param id The ID (section) of the box
	 */
    public HighlightingComboBox(ActionListener al, String id) {
    	this.al = al;
    	this.id = id;
        uninstall();
        install();
    }

    @Override
    public void updateUI() {
        uninstall();
        super.updateUI();
        install();
    }

    private void uninstall() {
        if (listener == null) return;
        getPopupList().removeListSelectionListener(listener);
        listener = null;
    }

    protected void install() {
        listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;

                @SuppressWarnings("rawtypes")
				JList list = getPopupList();
                al.actionPerformed(new ActionEvent(this, 0, id + "&" + list.getSelectedValue()));
            }
        };
        getPopupList().addListSelectionListener(listener);
    }

    @SuppressWarnings("rawtypes")
	private JList getPopupList() {
        ComboPopup popup = (ComboPopup) getUI().getAccessibleChild(this, 0);
        return popup.getList();

    }
}
