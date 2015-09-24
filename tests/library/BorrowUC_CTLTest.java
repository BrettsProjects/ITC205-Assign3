/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import library.hardware.StubCardReader;
import library.hardware.StubDisplay;
import library.hardware.StubPrinter;
import library.hardware.StubScanner;
import library.interfaces.EBorrowState;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;

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

import org.mockito.Mock;

/**
 *
 * @author Brett.Smith
 */
public class BorrowUC_CTLTest {
    private StubDisplay display;
    private StubCardReader reader;
    private StubScanner scanner;
    private StubPrinter printer;
    
    @Mock
    private IBookDAO bookDAO;
    
    @Mock
    private ILoanDAO loanDAO;
    
    @Mock
    private IMemberDAO memberDAO;
    
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
        display = new StubDisplay();
        reader = new StubCardReader();
        printer = new StubPrinter();
        scanner = new StubScanner();
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
        System.out.println("initialise");
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
     * Test of close method, of class BorrowUC_CTL.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        BorrowUC_CTL instance = new BorrowUC_CTL(reader, scanner, printer,
                display, bookDAO, loanDAO, memberDAO);
        
        assertTrue(instance.getState().equals(EBorrowState.CREATED));
        
        instance.close();
        
        assertTrue(display.getId().equals("Main Menu"));
    }

    /**
     * Test of cardSwiped method, of class BorrowUC_CTL.
     */
    @Test
    public void testCardSwiped() {
        System.out.println("cardSwiped");
        int memberID = 0;
        BorrowUC_CTL instance = null;
        instance.cardSwiped(memberID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

    /**
     * Test of cancelled method, of class BorrowUC_CTL.
     */
    @Test
    public void testCancelled() {
        System.out.println("cancelled");
        BorrowUC_CTL instance = null;
        instance.cancelled();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scansCompleted method, of class BorrowUC_CTL.
     */
    @Test
    public void testScansCompleted() {
        System.out.println("scansCompleted");
        BorrowUC_CTL instance = null;
        instance.scansCompleted();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loansConfirmed method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansConfirmed() {
        System.out.println("loansConfirmed");
        BorrowUC_CTL instance = null;
        instance.loansConfirmed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loansRejected method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansRejected() {
        System.out.println("loansRejected");
        BorrowUC_CTL instance = null;
        instance.loansRejected();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
