/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entities;

import library.interfaces.entities.EBookState;
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

    /**
     * The tests below ensure that the constructor cannot be called with
     * invalid input in any of the constructor fields for Book.java
     */
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
    
    /**
     * Ensures that a getLoan() call should be null when a damaged book is returned
     * and then disposed.
     */
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
    
    /**
     * Test ensures that no loan object is associated when the book calls
     * getLoan after it is lost. This is inline with the specs whcih say
     * getLoan should return null if the book isnt currently ON_LOAN.
     */
    @Test
    public void testGetLoanShouldBeNullWhenLost()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        Loan loan = new Loan();
        book.borrow(loan);
        book.lose();
        assertTrue(book.getLoan() == null);
    }

    /**
     * Test of returnBook method, of class Book. Ensures that the return book
     * places the book into the correct object state. Applies to next two
     * methods.
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
    
    /**
     * Ensures that a book can be lost whilst it is on loan.
     */
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
    
    /** 
     * Test of repair method, ensures that a repair() method call makes the
     * book available to be borrowed again.
     */
    @Test
    public void testRepairDamagedBook() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        book.repair();
        assertTrue(book.getState() == eBookState_.AVAILABLE);
    }
    

    /**
     * Test of disposal of a book which is AVAILABLE, DAMAGED, or LOST
     */
    @Test
    public void testDispose() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        book.dispose();
        assertTrue(book.getState() == eBookState_.DISPOSED);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        assertTrue(book.getState() == eBookState_.DISPOSED);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
        book.dispose();
        assertTrue(book.getState() == eBookState_.DISPOSED);
    }
    
    /**
     * Ensures that an already disposed book cannot be disposed again.
     */
    @Test(expected=RuntimeException.class)
    public void TestDisposedAlreadyDisposed()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        book.dispose();
    }
    
    /**
     * Ensures that a book that is presently on loan cannot be disposed.
     */
    @Test(expected=RuntimeException.class)
    public void TestDisposedOnLoan()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.dispose();
    }

    /**
     * Test of getState method, of class Book. Ensures that the correct state
     * is returned by book for each of the VALID state transitions.
     */
    @Test
    public void testGetState() {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        assertTrue(book.getState().equals(eBookState_.DISPOSED));
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        assertTrue(book.getState().equals(eBookState_.ON_LOAN));
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        assertTrue(book.getState().equals(eBookState_.DAMAGED));
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        assertTrue(book.getState() == eBookState_.AVAILABLE);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(false);
        assertTrue(book.getState() == eBookState_.AVAILABLE);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
        assertTrue(book.getState() == eBookState_.LOST);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
        book.dispose();
        assertTrue(book.getState() == eBookState_.DISPOSED);
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        book.repair();
        assertTrue(book.getState().equals(eBookState_.AVAILABLE));
        
        book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.returnBook(true);
        book.dispose();
        assertTrue(book.getState().equals(eBookState_.DISPOSED));
    }
    
    /**
     * Ensures that a damaged book cannot be lost.
     */
    @Test(expected=RuntimeException.class)
    public void testDamagedBookCannotBeLost()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.dispose();
        book.lose();
    }
    
    /**
     * Ensures that an on loan book cannot be disposed.
     */
    @Test(expected=RuntimeException.class)
    public void testOnLoanBookCannotBeDisposed()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.dispose();
    }
    
    /**
     * Ensures than an LOST book cannot become available by returnBook() method
     * calls
     */
    @Test(expected=RuntimeException.class)
    public void testLostBooksCannotBecomeDamaged()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
        book.returnBook(true);
    }
    
    /**
     * Ensures than an LOST book cannot become available by returnBook() method
     * calls
     */
    @Test(expected=RuntimeException.class)
    public void testLostBooksCannotBecomeAvailable()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.borrow(new Loan());
        book.lose();
        book.returnBook(false);
    }
    
    /**
     * Ensures than an DISPOSED book cannot become available by returnBook() method
     * calls
     */
    @Test(expected=RuntimeException.class)
    public void testDisposedBooksCannotBecomeDamaged()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        book.returnBook(true);
    }
    
    /**
     * Ensures than an DISPOSED book cannot become available by returnBook() method
     * calls
     */
    @Test(expected=RuntimeException.class)
    public void testDisposedBooksCannotBecomeAvailable()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        book.returnBook(false);
    }
    
    /**
     * Ensures than an DISPOSED book cannot become lost by lose() method
     * calls
     */
    @Test(expected=RuntimeException.class)
    public void testDisposedBooksCannotBecomeLost()
    {
        Book book = new Book("MyBook", "MyBook", "MyBook", 100);
        book.dispose();
        book.lose();
    }

    /**
     * Test of getAuthor method, of class Book. Ensures the correct string
     * is returned.
     */
    @Test
    public void testGetAuthor() {
        Book book = new Book("Author", "BookTitle", "CallNumber", 256);
        assertTrue(book.getAuthor().equals("Author"));
    }

    /**
     * Test of getTitle method, of class Book. Ensures the correct string
     * is returned.
     */
    @Test
    public void testGetTitle() {
        Book book = new Book("Author", "BookTitle", "CallNumber", 256);
        assertTrue(book.getTitle().equals("BookTitle"));
    }

    /**
     * Test of getCallNumber method, of class Book. Ensures the correct string
     * is returned.
     */
    @Test
    public void testGetCallNumber() {
        Book book = new Book("Author", "BookTitle", "CallNumber", 256);
        assertTrue(book.getCallNumber().equals("CallNumber"));
    }

    /**
     * Test of getID method, of class Book. Ensures the correct string
     * is returned.
     */
    @Test
    public void testGetID() {
        Book book = new Book("Author", "BookTitle", "CallNumber", 256);
        assertTrue(book.getID() == 256);
    }
    
}
