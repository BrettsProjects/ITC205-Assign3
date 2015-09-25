/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.Calendar;
import java.util.Date;
import library.daos.BookDAO;
import library.daos.BookHelper;
import library.daos.LoanHelper;
import library.daos.LoanMapDAO;
import library.daos.MemberHelper;
import library.daos.MemberMapDAO;
import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;
import library.interfaces.EBorrowState;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brett.Smith
 */
public class BorrowUC_CTLIT {
    
    public BorrowUC_CTLIT() {
    }
    private Display display;
    private CardReader reader;
    private Scanner scanner;
    private Printer printer;
    private IBookDAO bookDAO;
    private ILoanDAO loanDAO;
    private IMemberDAO memberDAO;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        reader = new CardReader();
        scanner = new Scanner();
        printer = new Printer();
        display = new Display();
        
        bookDAO = new BookDAO(new BookHelper());
        loanDAO = new LoanMapDAO(new LoanHelper());
        memberDAO = new MemberMapDAO(new MemberHelper());
        setupTestData();
        setupAdditionalTestData();
    }
    
    private void setupTestData() {
            IBook[] book = new IBook[15];
            IMember[] member = new IMember[6];

            book[0]  = bookDAO.addBook("author1", "title1", "callNo1");
            book[1]  = bookDAO.addBook("author1", "title2", "callNo2");
            book[2]  = bookDAO.addBook("author1", "title3", "callNo3");
            book[3]  = bookDAO.addBook("author1", "title4", "callNo4");
            book[4]  = bookDAO.addBook("author2", "title5", "callNo5");
            book[5]  = bookDAO.addBook("author2", "title6", "callNo6");
            book[6]  = bookDAO.addBook("author2", "title7", "callNo7");
            book[7]  = bookDAO.addBook("author2", "title8", "callNo8");
            book[8]  = bookDAO.addBook("author3", "title9", "callNo9");
            book[9]  = bookDAO.addBook("author3", "title10", "callNo10");
            book[10] = bookDAO.addBook("author4", "title11", "callNo11");
            book[11] = bookDAO.addBook("author4", "title12", "callNo12");
            book[12] = bookDAO.addBook("author5", "title13", "callNo13");
            book[13] = bookDAO.addBook("author5", "title14", "callNo14");
            book[14] = bookDAO.addBook("author5", "title15", "callNo15");

            member[0] = memberDAO.addMember("fName0", "lName0", "0001", "email0");
            member[1] = memberDAO.addMember("fName1", "lName1", "0002", "email1");
            member[2] = memberDAO.addMember("fName2", "lName2", "0003", "email2");
            member[3] = memberDAO.addMember("fName3", "lName3", "0004", "email3");
            member[4] = memberDAO.addMember("fName4", "lName4", "0005", "email4");
            member[5] = memberDAO.addMember("fName5", "lName5", "0006", "email5");

            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();

            //create a member with overdue loans		
            for (int i=0; i<2; i++) {
                    ILoan loan = loanDAO.createLoan(member[1], book[i]);
                    loanDAO.commitLoan(loan);
            }
            cal.setTime(now);
            cal.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
            Date checkDate = cal.getTime();		
            loanDAO.updateOverDueStatus(checkDate);

            //create a member with maxed out unpaid fines
            member[2].addFine(10.0f);

            //create a member with maxed out loans
            for (int i=2; i<7; i++) {
                    ILoan loan = loanDAO.createLoan(member[3], book[i]);
                    loanDAO.commitLoan(loan);
            }

            //a member with a fine, but not over the limit
            member[4].addFine(5.0f);

            //a member with a couple of loans but not over the limit
            for (int i=7; i<9; i++) {
                    ILoan loan = loanDAO.createLoan(member[5], book[i]);
                    loanDAO.commitLoan(loan);
            }
    }
    
    private void setupAdditionalTestData()
    {
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 7
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 8
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 9
        bookDAO.addBook("author5", "title15", "callNo16");
        bookDAO.addBook("author5", "title15", "callNo17");
        bookDAO.addBook("author5", "title15", "callNo18");
        bookDAO.addBook("author5", "title15", "callNo19");
        bookDAO.addBook("author5", "title15", "callNo20");
        bookDAO.addBook("author5", "title15", "callNo21");
        bookDAO.addBook("author5", "title15", "callNo22");
    }
        
    @After
    public void tearDown() {
    }

    /**
     * Test of initialise method, of class BorrowUC_CTL. Ensures that a correct
     * call to initialise, and BorrowBookCTL with correct variables will
     * result in the appropriate object state change.
     */
    @Test
    public void testInitialise() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        
        assertTrue(instance.getState().equals(EBorrowState.CREATED));
        instance.initialise();
        assertTrue(instance.getState().equals(EBorrowState.INITIALIZED));
    }

    /**
     * Test ensures that unrestricted members get unrestricted service. Member
     * has not borrowed and doesnt have fines.
     */
    @Test
    public void testCardSwipedUnrestrictedMember1() {
        int memberID = 1;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
            display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
    /**
     * Test ensures that unrestricted members get unrestricted service. Ensures
     * that a member who has fines payable doesnt end up restricted.
     */
    @Test
    public void testCardSwipedUnrestrictedMember2() {
        int memberID = 5;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
        /**
     * Test ensures that unrestricted members get unrestricted service. Ensures
     * that a member who already has loans can still borrow.
     */
    @Test
    public void testCardSwipedUnrestrictedMember3() {
        int memberID = 6;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
    /**
     * Tests that a member who is restricted, becomes restricted in the system.
     * 
     * This one tests that a borrower who has reached the loan limit is
     * restricted.
     */
    @Test
    public void testCardSwipedRestrictedMember1() {
        int memberID = 4;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.BORROWING_RESTRICTED));
    }
    
    /**
     * Tests that a member who is restricted, becomes restricted in the system.
     * 
     * This one tests that a borrower who has over due loans is
     * restricted.
     */
    @Test
    public void testCardSwipedRestrictedMember2() {
        int memberID = 2;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.BORROWING_RESTRICTED));
    }
    
        /**
     * Tests that a member who is restricted, becomes restricted in the system.
     * 
     * This one tests that a borrower who has over fine limit is restricted.
     */
    @Test
    public void testCardSwipedRestrictedMember3() {
        int memberID = 3;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getState().equals(EBorrowState.BORROWING_RESTRICTED));
    }
    
    /**
     * Ensures that when no such member exists, that the borrower object
     * returned is null.
     */
    @Test
    public void testCardSwipedNoSuchMember() {
        int memberID = 0;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(instance.getBorrower() == null);
    }
    /**
     * Ensures that you cannot call cardSwiped unless you have initialised.
     */
    @Test(expected=RuntimeException.class)
    public void testCardSwipedBeforeInitialise() {
        int memberID = 0;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.cardSwiped(memberID);
    }

    /**
     * Test of cancelled method, of class BorrowUC_CTL. Implementation was
     * assumed.
     */
    @Test
    public void testCancelled() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cancelled();
        assertTrue(instance.getState().equals(EBorrowState.CANCELLED));
    }

    /**
     * Test of scansCompleted method, of class BorrowUC_CTL.
     */
    @Test
    public void testScansCompleted() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(7);
        instance.bookScanned(10);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        instance.scansCompleted();
        System.out.println(instance.getScanCount());
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
    }
    
    /**
     * Test of scansCompleted method, of class BorrowUC_CTL. This test tests
     * when there are no loans present in the loan list. Its an invalid state
     * test.
     */
    @Test
    public void testScansCompletedNoLoans() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(7);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
    /**
     * Test of scansCompleted method, of class BorrowUC_CTL. This test tests
     * when we're not in SCANNING_BOOKS. Its an invalid state
     * test.
     */
    @Test(expected=RuntimeException.class)
    public void testScansCompletedNotScanningBooks() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.scansCompleted();
    }

    /**
     * Test of loansConfirmed method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansConfirmed() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(8);
        instance.bookScanned(10);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        instance.loansConfirmed();
        assertTrue(instance.getState().equals(EBorrowState.COMPLETED));
    }
    
    /**
     * Test of loansConfirmed method, of class BorrowUC_CTL.
     */
    @Test(expected=RuntimeException.class)
    public void testLoansConfirmedNotInStateForTransition() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(10);
        instance.bookScanned(10);
        instance.loansConfirmed();
    }
    
    /**
     * Test of loansRejected method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansRejected() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(8);
        instance.bookScanned(11);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        instance.loansRejected();
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
        assertTrue(instance.getPendingLoans().size() == 0);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. Ensures that normal
     * conditions of adding a book are met.
     */
    @Test
    public void testBookScanned() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(9);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
        instance.bookScanned(12);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 1);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. Ensures that you
     * cannot scan books without first swiping a card.
     */
    @Test(expected=RuntimeException.class)
    public void testBookScannedNotReadyToScan() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        assertTrue(instance.getScanCount() == 0);
        instance.bookScanned(10);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. Ensures that a book
     * that is not available cannot be scanned.
     */
    @Test
    public void testBookScannedNotAvailable() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(9);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
        instance.bookScanned(3);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. Ensures that a book
     * that doesnt exist cannot be added.
     */
    @Test
    public void testBookScannedNotExist() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(9);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
        instance.bookScanned(25);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 0);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. confirms that the same
     * book cannot be added twice.
     */
    @Test
    public void testBookScannedAddSameBook() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(9);
        instance.bookScanned(10);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 1);
        instance.bookScanned(10);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 1);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL. Confirms that on the
     * addition of 5 books causes the controller to transition state to 
     * CONFIRMING_LOANS.
     */
    @Test
    public void testBookScannedAdd5Books() 
    {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(9);
        instance.bookScanned(16);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 1);
        instance.bookScanned(17);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 2);
        instance.bookScanned(18);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 3);
        instance.bookScanned(19);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertTrue(instance.getScanCount() == 4);
        instance.bookScanned(20);
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        assertTrue(instance.getScanCount() == 5);
    }
}
