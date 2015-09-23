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
    IScannerListener, IBorrowUIListener 
{
    private int scanCount = 0;

    private ICardReader reader;
    private IScanner scanner; 
    private IPrinter printer; 
    private IDisplay display;
    private IBorrowUI ui;
    private EBorrowState state; 
    private IBookDAO bookDAO;
    private IMemberDAO memberDAO;
    private ILoanDAO loanDAO;
    private IMember borrower;

    private List<IBook> bookList;
    private List<ILoan> loanList;

    private JPanel previous;


    public BorrowUC_CTL(ICardReader reader, IScanner scanner, IPrinter 
        printer, IDisplay display, IBookDAO bookDAO, ILoanDAO loanDAO, 
        IMemberDAO memberDAO) 
    {
            this.display = display;
            this.ui = new BorrowUC_UI(this);
            state = EBorrowState.CREATED;
            loanList = new ArrayList<>();
            bookList = new ArrayList<>();
    }

    public void initialise() 
    {
        this.previous = this.display.getDisplay();
        this.display.setDisplay((JPanel)this.ui, "Borrow UI");
        setState(EBorrowState.INITIALIZED);
        // Allows this controller class to be used.
    }

    public void close() {
        display.setDisplay(previous, "Main Menu");
        // Returns to the main menu
    }

    @Override
    public void cardSwiped(int memberID) 
    {
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
        if (borrower != null)
        {
            return borrower.getLoans().size();
        }
        else
        {
            throw new RuntimeException("The borrower is null.");
        }
        // returns the existing number of loans this borrower has.
    }

    private void displayLoans()
    {
        String allLoans = "";
        List loans = borrower.getLoans();
        for (Object loan : loans) {
            allLoans = allLoans + loan.toString() + "\r\n";
        }
        ui.displayExistingLoan(allLoans);
        // Creates a string to display all loans and calls the ui method.
    }

    private void displayMemberDetails()
    {
        ui.displayMemberDetails(borrower.getID(), borrower.getFirstName() + 
                " " + borrower.getLastName() , borrower.getContactPhone());
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
    public void bookScanned(int barcode) 
    {
        if (state == state.SCANNING_BOOKS)
        {
            IBook book = bookDAO.getBookByID(barcode);
            if (book == null)
            {
                ui.displayErrorMessage("Book cannot be found.");
                return;
            }
            else
            {
                // Book not available
                if (book.getState() != EBookState.AVAILABLE)
                {
                    ui.displayErrorMessage("Book not available.");
                    return;
                }
                // Book already scanned
                else if (bookList.contains(book))
                {
                    ui.displayErrorMessage("Book already scanned.");
                    return;
                }

                // if the scan count is less than loan limit (5)
                // the user can borrow the book by adding to the loan list.
                scanCount++;
                bookList.add(book);
                ILoan loan = loanDAO.createLoan(borrower, book);
                loanList.add(loan);

                if (scanCount == 5)
                {
                    setState(EBorrowState.CONFIRMING_LOANS);
                }
                else if (scanCount > 5)
                {
                    throw new RuntimeException("Illegal operation: User has"
                            + " more than 5 books borrowed!");
                }

                ui.displayScannedBookDetails(getBookDetails(book));
                ui.displayPendingLoan(getLoansDetail());
            }
        }
        else
        {
            throw new RuntimeException("You arent allowed to scan books "
                    + "right now.");
        }
    }

    private String getBookDetails(IBook book)
    {
        String bookDetail = book.getAuthor();
        bookDetail.concat(" " + book.getCallNumber());
        bookDetail.concat(" " + book.getTitle());
        bookDetail.concat(" " + book.getID());
        return bookDetail;
    }

    private String getLoansDetail()
    {
        String loanDetail = "";
        for (ILoan loanList1: loanList)
        {
            loanDetail = loanDetail + loanList1.toString();
        }
        return loanDetail;
    }

    @Override
    public void cancelled() 
    {
        // Seems to be called when the cancel button is hit, so return to
        // the initial config.
        reader.setEnabled(true);
        scanner.setEnabled(false);
        setState(state.CANCELLED);
        close();
    }

    @Override
    public void scansCompleted() 
    {
        if (loanList.size() > 0)
        {
            setState(EBorrowState.CONFIRMING_LOANS);
            ui.displayConfirmingLoan(getLoansDetail());
        }
        else
        {
            ui.displayErrorMessage("You need to scan atleast one book.");
        }
    }



    @Override
    public void loansConfirmed() 
    {
        if (state == state.CONFIRMING_LOANS)
        {
            if (loanList.size() > 0)
            {
                for (ILoan loanList1 : loanList) 
                {    
                    loanDAO.commitLoan(loanList1);
                }
                setState(state.COMPLETED);
                printer.print(getLoansDetail());
                state = state.COMPLETED;
                close();
            }
            else
            {
                throw new RuntimeException("There are no loans to commit.");
            }
        }
        else
        {
            throw new RuntimeException("You are not in a state to "
                    + "confirm loans.");
        }
    }

    @Override
    public void loansRejected() 
    {
        setState(state.SCANNING_BOOKS);
        loanList = new ArrayList<>();
        bookList = new ArrayList<>();
        scanCount = countLoans();
        displayMemberDetails();
        displayLoans();
    }

    private void setState(EBorrowState state) 
    {    
        // From Dr Tulips email, this method, being a private method
        // wont be specified in any of the documentation as its a "how to"
        // method. Dr Tulip uses it for changing the state of the
        // application and so we will use it for displaying different UI
        // components as that is how the state is represented.

        this.state = state;
        this.ui.setState(state);

        switch (state) 
        {
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
                reader.setEnabled(false);
                scanner.setEnabled(false);
                break;

            case COMPLETED:
                reader.setEnabled(false);
                scanner.setEnabled(false);
                break;

            case CANCELLED:
                // never used.
                break;

            default:
                throw new RuntimeException("Unknown state");
        }
    }
}
