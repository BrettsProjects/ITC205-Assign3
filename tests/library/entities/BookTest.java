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
     * Test of borrow method, of class Book. Borrow confirms a book that is
     * AVAILABLE can be borrowed.
     */
    @Test
    public void testBorrow1() {
        System.out.println("Borrow: Book is available test.");
        Loan loan = new Loan();
        System.out.println(loan.toString() + " was used to borrow.");
        Book book = new Book("Available", "Available", "Available", 10);
        book.borrow(loan);
        System.out.println(book.getLoan().toString() + " was returned by book.");
        assertTrue(loan.equals(book.getLoan()));
    }

    /**
     * Test of getLoan method, of class Book.
     */
    @Test
    public void testGetLoan() {
        System.out.println("getLoan");
        Book instance = null;
        ILoan expResult = null;
        ILoan result = instance.getLoan();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
