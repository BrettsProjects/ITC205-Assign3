/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.ILoan;
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
public class BookTest {
    
    private EBookState eBookState_;
    
    public BookTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookConstructor1()
    {
        Book book = new Book("", "error", "error", 47);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBookConstructor2()
    {
        Book book = new Book("Error", "", "error", 46);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBookConstructor3()
    {
        Book book = new Book("Error", "Error", "", 45);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBookConstructor4()
    {
        Book book = new Book("Error", "Error", "Error", 0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testBookConstructor5()
    {
        Book book = new Book("Error", "error", "error", -127);
    }
    
    /**
     * Ensures that a book that is AVAILABLE can be borrowed and that its state
     * then changes to ON_LOAN.
     */
    @Test
    public void testBorrowAvailable() {
        System.out.println("Borrow: Book is AVAILABLE test.");
        Book book = new Book("Available", "Available", "Available", 10);
        assertTrue(book.getState() == eBookState_.AVAILABLE);
        book.borrow(new Loan());
        assertTrue(book.getState() == eBookState_.ON_LOAN);
    }
    
    /**
     * Ensures that a book that is presently on loan cannot be borrowed.
     */
    @Test(expected=RuntimeException.class)
    public void testBorrowOnLoan()
    {
        System.out.println("Borrow: Book is ON LOAN test.");
        Loan loan = new Loan();
        Loan otherLoan = new Loan();
        Book book = new Book("Available", "Available", "Available", 10);
        book.borrow(loan); //Book is now on Loan.
        book.borrow(otherLoan);
    }
    
    /**
     * Ensures that a damaged book cannot be borrowed.
     */
    @Test(expected=RuntimeException.class)
    public void testBorrowDamaged()
    {
        System.out.println("Borrow: Book is DAMAGED test.");
        Loan loan = new Loan();
        Loan otherLoan = new Loan();
        Book book = new Book("Available", "Available", "Available", 10);
        book.borrow(loan); // Book is now on Loan.
        book.returnBook(true); // Book is now damaged.
        book.borrow(otherLoan);
    }
    
    /**
     * Ensures that a lost book cannot be borrowed.
     */
    @Test(expected=RuntimeException.class)
    public void testBorrowLost()
    {
        Loan loan = new Loan();
        System.out.println("Borrow: Book is LOST test.");
        Book book = new Book("Available", "Available", "Available", 10);
        book.lose();
        book.borrow(loan);
    }
    
    /**
     * Ensures that a disposed book cannot be borrowed.
     */
    @Test(expected=RuntimeException.class)
    public void testBorrowDisposed()
    {
        Loan loan = new Loan();
        System.out.println("Borrow: Book is DISPOSED test.");
        Book book = new Book("Available", "Available", "Available", 10);
        book.dispose();
        book.borrow(loan);
    }

    /**
     * Ensure that a new book object returns null as it is not associated with
     * a loan.
     */
    @Test
    public void testGetLoanShouldBeNull() {
        Book book = new Book("Available", "Available", "Available", 20);
        assertTrue(book.getLoan() == null);
    }
    
    /**
     * This test ensures that a borrowed book returns null when it has been
     * returned and is no longer associated with a loan.
     */
    @Test
    public void testGetLoanShouldBeNullAfterBorrowedAndReturned()
    {
        Book book = new Book("Available", "Available", "Available", 20);
        book.borrow(new Loan());
        book.returnBook(false);
        assertTrue(book.getLoan() == null);
    }
    
    /**
     * Ensures that the correct loan is returned after a book has been borrowed
     * and has not been lost or damaged.
     */
    @Test
    public void testBookOnLoanReturnsCorrectLoan()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        
        book.borrow(null);
    }
    
    /**
     * Ensures that a null loan object cannot be passed into the book as a valid
     * loan.
     */
    @Test(expected=RuntimeException.class)
    public void testBookLoanCannotBeNull()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(null);
    }
    
    /**
     * Ensures that the book returns the correct loan object
     */
    
    // NOTE: NEED TO ENSURE THAT THE LOAN RETURNS TEH LOAN WHEN THE BOOK
    // IS IN OTHER STATES!!!!
    
    @Test
    public void testGetLoanShouldReturnLoan()
    {
        
    }

    /**
     * Test of returnBook method, of class Book.
     */
    @Test
    public void testReturnBook() {
        System.out.println("returnBook");
        boolean damaged = false;
        Book instance = null;
        instance.returnBook(damaged);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lose method, of class Book.
     */
    @Test
    public void testLose() {
        System.out.println("lose");
        Book instance = null;
        instance.lose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of repair method, of class Book.
     */
    @Test
    public void testRepair() {
        System.out.println("repair");
        Book instance = null;
        instance.repair();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dispose method, of class Book.
     */
    @Test
    public void testDispose() {
        System.out.println("dispose");
        Book instance = null;
        instance.dispose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getState method, of class Book.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Book instance = null;
        EBookState expResult = null;
        EBookState result = instance.getState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuthor method, of class Book.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        Book instance = null;
        String expResult = "";
        String result = instance.getAuthor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitle method, of class Book.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        Book instance = null;
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCallNumber method, of class Book.
     */
    @Test
    public void testGetCallNumber() {
        System.out.println("getCallNumber");
        Book instance = null;
        String expResult = "";
        String result = instance.getCallNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class Book.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Book instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
