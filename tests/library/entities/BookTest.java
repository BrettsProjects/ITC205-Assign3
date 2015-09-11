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
     * returned and is no longer associated with a loan. This test and the 
     * testGetLoanShouldBeNull() method ensure that an AVAILABLE book returns
     * null.
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
     * and has not been lost or damaged. This ensures that when the book is
     * ON_LOAN, the correct loan is returned.
     */
    @Test
    public void testBookOnLoanReturnsCorrectLoan()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        assertTrue(book.getLoan().equals(loan));
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
     * Asserts that the loan object is still returned when the book is lost
     * whilst on loan.
     */
    @Test
    public void testGetLoanShouldReturnLoanWhenLost()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        book.lose();
        assertTrue(book.getLoan() != loan);
    }
    
    /**
     * Asserts that the loan object is null when the book is damaged.
     */
    @Test
    public void testGetLoanShouldBeNullWhenDamaged()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        book.returnBook(true);
        assertTrue(book.getLoan() == null);
    }
    
    /**
     * Asserts that the loan object is null when the book was in the available
     * or damaged before it was disposed. This comment applies to the next
     * two successive tests.
     */
    @Test
    public void testGetLoanShouldBeNullWhenAvailableThenDisposed()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        assertTrue(book.getLoan() == null);
    }
    
    @Test
    public void testGetLoanShouldBeNullWhenDamagedthenDisposed()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        book.returnBook(true);
        book.dispose();
        assertTrue(book.getLoan() == null);
    }
    
    @Test
    public void testGetLoanShouldntBeNullWhenLost()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        book.lose();
        assertTrue(book.getLoan().equals(loan));
    }

    /**
     * Test of returnBook method, of class Book.
     */
    @Test
    public void testReturnBook() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(false);
        assertTrue(book.getState() == eBookState_.AVAILABLE);
    }
    
    @Test
    public void testReturnDamagedBook() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        assertTrue(book.getState() == eBookState_.DAMAGED);
    }
    
    /**
     * Test ensures that the book that is not on loan cannot be returned.
     */
    @Test(expected=RuntimeException.class)
    public void testBookThatIsAvailable()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.returnBook(false);
    }
    
    /**
     * Test of lose method, of class Book. The book must be on loan before it
     * can be lost
     */
    @Test(expected=RuntimeException.class)
    public void testLose() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.lose();
    }
    
    @Test
    public void testLoseOnLoan() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
    }

    /**
     * Test of repair method, of class Book. Ensure that repair throws a
     * Runtime exception where the book is not currently damaged and check that
     * a damaged book can be repaired.
     */
    @Test(expected=RuntimeException.class)
    public void testRepair() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.repair();
    }
    
    @Test
    public void testRepairDamagedBook() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        book.repair();
        assertTrue(book.getState() == eBookState_.AVAILABLE);
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
