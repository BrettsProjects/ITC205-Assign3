/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import java.util.List;
import library.interfaces.entities.IBook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brett Smith
 */
public class BookDAOTest {
    
    public BookDAOTest() {
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
     * Ensures that the illegal argument exception is thrown when the 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorInvalidParam()
    {
        BookDAO instance = new BookDAO(null);
    }
    
    /**
     * Ensures that valid arguments create a new instance of BookDAO
     */
    @Test
    public void testConstructorValidParam()
    {
        BookDAO instance = new BookDAO(new BookHelper());
    }
    
    /**
     * Test of addBook method, of class BookDAO. Adds only valid books. Another
     * method will be used to check for invalid book messages.
     */
    @Test
    public void testAddBook() {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        
        assertTrue(instance.findBooksByAuthor("Author").size() == 2);
        assertTrue(instance.findBooksByAuthor("OtherAuthor").size() == 1);
    }

    /**
     * Ensures that adding an invalid book throws a runtime exception.
     */
    @Test(expected=RuntimeException.class)
    public void testAddInvalidBook()
    {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "");
    }
    
    /**
     * Ensures that adding an invalid book throws a runtime exception.
     */
    @Test(expected=RuntimeException.class)
    public void testAddInvalidBook2()
    {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook(null, "Title", "");
    }
    
    /**
     * Test of getBookByID method, of class BookDAO.
     */
    @Test
    public void testGetBookByID() {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        assertTrue(instance.getBookByID(1).getAuthor().equals("Author") && instance.getBookByID(1).getTitle().equals("Title"));
        assertTrue(instance.getBookByID(2).getAuthor().equals("OtherAuthor") && instance.getBookByID(2).getTitle().equals("Title"));
        assertTrue(instance.getBookByID(3).getAuthor().equals("Author") && instance.getBookByID(3).getTitle().equals("OtherTitle"));
    }

    /**
     * Test of listBooks method, ensuring that the list contains the added
     * number of members.
     */
    @Test
    public void testListBooks() {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        
        assertTrue(instance.listBooks().size() == 3);
    }
    
    /**
     * Test of listBooks method, ensuring that the list returned contains no
     * members.
     */
    @Test
    public void testListBooksNothingInList() {
        BookDAO instance = new BookDAO(new BookHelper());
        
        assertTrue(instance.listBooks().size() == 0);
    }

    /**
     * Test of findBooksByAuthor method, of class BookDAO.
     */
    @Test
    public void testFindBooksByAuthor() {
        System.out.println("findBooksByAuthor");
        String author = "";
        BookDAO instance = null;
        List<IBook> expResult = null;
        List<IBook> result = instance.findBooksByAuthor(author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByTitle method, of class BookDAO.
     */
    @Test
    public void testFindBooksByTitle() {
        System.out.println("findBooksByTitle");
        String title = "";
        BookDAO instance = null;
        List<IBook> expResult = null;
        List<IBook> result = instance.findBooksByTitle(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByAuthorTitle method, of class BookDAO.
     */
    @Test
    public void testFindBooksByAuthorTitle() {
        System.out.println("findBooksByAuthorTitle");
        String author = "";
        String title = "";
        BookDAO instance = null;
        List<IBook> expResult = null;
        List<IBook> result = instance.findBooksByAuthorTitle(author, title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
