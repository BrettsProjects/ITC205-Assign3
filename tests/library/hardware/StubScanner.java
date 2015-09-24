package library.hardware;

import javax.swing.JFrame;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class StubScanner extends JFrame implements IScanner {
	private IScannerListener listener;
        private boolean isEnabled;

	public StubScanner() {
	
	}

	@Override
	public void setEnabled(boolean enabled) {
            isEnabled = enabled;
	}
        
        public boolean getEnabled()
        {
            return isEnabled;
        }

	@Override
	public void addListener(IScannerListener listener) {
		this.listener = listener;
		
	}
        
        public IScannerListener getListener()
        {
            return listener;
        }

}
