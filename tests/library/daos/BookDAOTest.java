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
     * Test of getBookByID method, of class BookDAO.
     */
    @Test
    public void testGetBookByID() {
        System.out.println("getBookByID");
        int id = 0;
        BookDAO instance = null;
        IBook expResult = null;
        IBook result = instance.getBookByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listBooks method, of class BookDAO.
     */
    @Test
    public void testListBooks() {
        System.out.println("listBooks");
        BookDAO instance = null;
        List<IBook> expResult = null;
        List<IBook> result = instance.listBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
