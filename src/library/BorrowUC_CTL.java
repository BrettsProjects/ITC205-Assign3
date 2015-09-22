package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	
	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.display = display;
		this.ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
	}
	
	public void initialise() {
            this.previous = this.display.getDisplay();
            this.display.setDisplay((JPanel)this.ui, "Borrow UI");
            setState(EBorrowState.INITIALIZED);
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
            if (state.equals(state.INITIALIZED))
            {
                // Get the borrower
                borrower = memberDAO.getMemberByID(memberID);
                if (borrower == null)
                {
                    ui.displayErrorMessage("The scanned member "
                            + "does not exist!");
                    return;
                }
                
                // Set the interface based on borrower object
                if(borrower.hasOverDueLoans() || borrower.hasReachedFineLimit()
                        || borrower.hasReachedLoanLimit())
                {
                    setState(state.BORROWING_RESTRICTED);
                    ui.setState(state.BORROWING_RESTRICTED);
                    
                    // Display member details
                    displayMemberDetails();
                    
                    // Display existing loans
                    displayLoans();
                    
                    // Existing fine message displayed if relevant
                    displayFines();
                    
                    // Overdue message displayed if relevant
                    displayOverDueMessage();
                    
                    // atLoanLimit message displayed if relevant
                    displayAtLoanLimitMessage();
                    
                    // borrowing restricted error message displayed
                    displayBorrowingRestrictedError();
                }
                else
                {
                    setState(state.SCANNING_BOOKS);
                    ui.setState(state.SCANNING_BOOKS);
                    
                    // Display member details
                    displayMemberDetails();

                    //display existing loans
                    displayLoans();
                    
                    //scan count initialised to number of existing loans
                    scanCount = countLoans();
                    
                    //existing fine message displayed if relevant
                    displayFines();
                }
                
            }
            else
            {
                throw new RuntimeException("Card cannot be swiped when "
                        + "borrowing system is not yet initialised.");
            }
	}
        
        private int countLoans()
        {
            return borrower.getLoans().size();
        }
	
	private void displayLoans()
        {
            String allLoans = "";
            List loans = borrower.getLoans();
            for (int i = 0; i < loans.size(); i++)
            {
                allLoans = loans.get(i).toString() + "\r\n";
            }
            ui.displayExistingLoan(allLoans);
        }
        
        private void displayMemberDetails()
        {
            ui.displayMemberDetails(borrower.getID(), borrower.getFirstName() + " " + borrower.getLastName() , borrower.getContactPhone());
        }
	
        private void displayFines()
        {
            if (borrower.hasFinesPayable())
            {
                Float amount = borrower.getFineAmount();
                ui.displayOutstandingFineMessage(amount);
            }
        }
        
        private void displayOverDueMessage()
        {
            if (borrower.hasOverDueLoans())
            {
                ui.displayOverDueMessage();
            }
        }
        
        private void displayAtLoanLimitMessage()
        {
            if (borrower.hasReachedLoanLimit())
            {
                ui.displayAtLoanLimitMessage();
            }
        }
        
        private void displayBorrowingRestrictedError()
        {
            ui.displayErrorMessage("Borrowing is restricted at this time "
                    + "for you.");
        }
        
	@Override
	public void bookScanned(int barcode) {
		throw new RuntimeException("Not implemented yet");
	}

	
	private void setState(EBorrowState state) {
            
            // From Dr Tulips email, this method, being a private method
            // wont be specified in any of the documentation as its a "how to"
            // method. Dr Tulip uses it for changing the state of the
            // application and so we will use it for displaying different UI
            // components as that is how the state is represented.
            
            this.state = state;
            this.ui.setState(state);
            switch (state) {
		case INITIALIZED:
                        reader.setEnabled(true);
                        scanner.setEnabled(false);
			break;
			
		case SCANNING_BOOKS:
			reader.setEnabled(false);
                        scanner.setEnabled(true);
			break;
			
		case BORROWING_RESTRICTED:
                    reader.setEnabled(false);
                    scanner.setEnabled(false);
			break;
			
		case CONFIRMING_LOANS:
			break;

 		case COMPLETED:
			break;
			
		case CANCELLED:
			break;
			
		default:
			throw new RuntimeException("Unknown state");
		}
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansConfirmed() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansRejected() {
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
