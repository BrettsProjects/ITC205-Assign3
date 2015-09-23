package library.hardware;

import javax.swing.JFrame;

import library.interfaces.hardware.IPrinter;

import javax.swing.JTextArea;

public class StubPrinter extends JFrame implements IPrinter {

	private JTextArea textArea;

	public StubPrinter() {
	}

	@Override
	public void print(String printData) {
		textArea.setText(printData);		
	}
        
        public String getPrintData()
        {
            return textArea.getText();
        }
}
