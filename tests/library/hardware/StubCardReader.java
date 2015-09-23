package library.hardware;

import javax.swing.JFrame;

import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;



public class StubCardReader extends JFrame implements ICardReader {

	private boolean isEnabled;
	private ICardReaderListener listener;

	public StubCardReader() {
            isEnabled = false;
	}


	@Override
	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}


	@Override
	public void addListener(ICardReaderListener listener) {
		this.listener = listener;		
	}
        
        public ICardReaderListener getListener()
        {
            return listener;
        }
}
