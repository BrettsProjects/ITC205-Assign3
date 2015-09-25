/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.List;
import library.daos.BookDAO;
import library.daos.LoanMapDAO;
import library.daos.MemberMapDAO;
import library.entities.Member;
import library.hardware.StubCardReader;
import library.hardware.StubDisplay;
import library.hardware.StubPrinter;
import library.hardware.StubScanner;
import library.interfaces.EBorrowState;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Brett.Smith
 */
public class BorrowUC_CTLTest {
    /* Stubs */
    private StubDisplay display;
    private StubCardReader reader;
    private StubScanner scanner;
    private StubPrinter printer;
    
    /* Mockito Mocks */
    private IMember member10, member11, member12, member13, member14, member15;
    private IBookDAO bookDAO;
    private ILoanDAO loanDAO;
    private IMemberDAO memberDAO;
    private IBook book;
    private ILoan loan;
    
    public BorrowUC_CTLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /* Stubs */
        display = new StubDisplay();
        reader = new StubCardReader();
        printer = new StubPrinter();
        scanner = new StubScanner();
        
        /* Mockito Mocks */
        bookDAO = mock(BookDAO.class);
        loanDAO = mock(LoanMapDAO.class);
        memberDAO = mock(MemberMapDAO.class);
        book = mock(IBook.class);
        loan = mock(ILoan.class);
        
        /* Setup for Book, BookDAO */
        when(bookDAO.getBookByID(10)).thenReturn(book);
        when(book.getState()).thenReturn(EBookState.AVAILABLE);
        when(book.getAuthor()).thenReturn("Author");
        when(book.getCallNumber()).thenReturn("CallNum");
        when(book.getTitle()).thenReturn("Title");
        when(book.getID()).thenReturn(10);
        
        /* Setup for Loan, LoanDAO */
        when(loanDAO.createLoan(any(), any())).thenReturn(loan);
        when(loan.toString()).thenReturn("FakeLoan");
        /* Setup for Members */
        member10 = mock(Member.class);
        member11 = mock(Member.class);
        member12 = mock(Member.class);
        member13 = mock(Member.class);
        member14 = mock(Member.class);
        member15 = mock(Member.class);
        
        when(memberDAO.getMemberByID(0)).thenReturn(null);
        when(memberDAO.getMemberByID(10)).thenReturn(member10);
        when(memberDAO.getMemberByID(15)).thenReturn(member15);
        when(memberDAO.getMemberByID(14)).thenReturn(member14);
        when(memberDAO.getMemberByID(13)).thenReturn(member13);
        when(memberDAO.getMemberByID(12)).thenReturn(member12);
        when(memberDAO.getMemberByID(11)).thenReturn(member11);
        when(memberDAO.getMemberByID(10)).thenReturn(member10);
        
        when(member15.hasReachedLoanLimit()).thenReturn(true);
        when(member14.hasReachedFineLimit()).thenReturn(true);
        when(member13.hasOverDueLoans()).thenReturn(true);
        when(member11.hasFinesPayable()).thenReturn(true);
        when(member10.hasOverDueLoans()).thenReturn(false);
        when(member10.hasReachedFineLimit()).thenReturn(false);
        when(member10.hasReachedLoanLimit()).thenReturn(false);
        
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
        assertTrue(display.getId().equals("Borrow UI"));
        assertTrue(reader.getEnabled());
        assertTrue(!scanner.getEnabled());
    }

    /**
     * Test of close method, of class BorrowUC_CTL. Ensures that the object
     * returns to the main menu on call. (This is an assumption)
     */
    @Test
    public void testClose() {
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        
        assertTrue(instance.getState().equals(EBorrowState.CREATED));
        
        instance.close();
        
        assertTrue(display.getId().equals("Main Menu"));
    }

    /**
     * Test ensures that unrestricted members get unrestricted service.
     */
    @Test
    public void testCardSwipedUnrestrictedMember1() {
        int memberID = 10;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(scanner.getEnabled());
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
    /**
     * Test ensures that unrestricted members get unrestricted service. Ensures
     * that a member who has fines payable doesnt end up restricted.
     */
    @Test
    public void testCardSwipedUnrestrictedMember2() {
        int memberID = 11;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(scanner.getEnabled());
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
    }
    
        /**
     * Test ensures that unrestricted members get unrestricted service. Ensures
     * that a member who already has loans can still borrow.
     */
    @Test
    public void testCardSwipedUnrestrictedMember3() {
        int memberID = 12;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        assertTrue(scanner.getEnabled());
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
        int memberID = 15;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        verify(member15, times(1)).hasReachedLoanLimit();
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
        int memberID = 13;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        verify(member13, times(1)).hasOverDueLoans();
        assertTrue(instance.getState().equals(EBorrowState.BORROWING_RESTRICTED));
    }
    
        /**
     * Tests that a member who is restricted, becomes restricted in the system.
     * 
     * This one tests that a borrower who has over due loans is
     * restricted.
     */
    @Test
    public void testCardSwipedRestrictedMember3() {
        int memberID = 14;
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        instance.initialise();
        instance.cardSwiped(memberID);
        verify(member14, times(1)).hasReachedFineLimit();
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
        verify(memberDAO, times(1)).getMemberByID(memberID);
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
        instance.cardSwiped(10);
        instance.bookScanned(10);
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        assertFalse(reader.getEnabled());
        assertFalse(scanner.getEnabled());
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
        instance.cardSwiped(10);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertFalse(reader.getEnabled());
        assertTrue(scanner.getEnabled());
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
        instance.cardSwiped(10);
        instance.bookScanned(10);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        instance.loansConfirmed();
        assertTrue(instance.getState().equals(EBorrowState.COMPLETED));
        assertFalse(reader.getEnabled());
        assertFalse(scanner.getEnabled());
        assertTrue(printer.getPrintData() != null);
        verify(loanDAO).commitLoan(any());
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
        instance.cardSwiped(10);
        instance.bookScanned(10);
        instance.scansCompleted();
        assertTrue(instance.getState().equals(EBorrowState.CONFIRMING_LOANS));
        instance.loansRejected();
        assertTrue(instance.getState().equals(EBorrowState.SCANNING_BOOKS));
        assertFalse(reader.getEnabled());
        assertTrue(scanner.getEnabled());
        assertTrue(instance.getScanCount() == 0);
        assertTrue(instance.getPendingLoans().size() == 0);
    }
    
    /**
     * Test of bookScanned method, of class BorrowUC_CTL.
     */
    @Test
    public void testBookScanned() {
        System.out.println("bookScanned");
        int barcode = 0;
        BorrowUC_CTL instance = null;
        instance.bookScanned(barcode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
