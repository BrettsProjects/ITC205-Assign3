package library.hardware;

import javax.swing.JFrame;
import javax.swing.JPanel;

import library.interfaces.hardware.IDisplay;


public class StubDisplay extends JFrame implements IDisplay {
    
    private JPanel panel;
    private String id;

	public StubDisplay() {
	}


	@Override
	public JPanel getDisplay() {
		return panel;		
	}
        
        public String getId()
        {
            return id;
        }
	
	
	@Override
	public void setDisplay(JPanel panel, String id) {
            this.panel = panel;
            this.id = id;
	}

	
}
