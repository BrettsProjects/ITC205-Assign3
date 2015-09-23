/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import java.util.List;
import static junit.framework.Assert.assertTrue;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The integration test file ensures that methods function when using the real
 * classes.
 * @author Brett.Smith
 */
public class BookDAOIT {
    
    
    public BookDAOIT() {
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

       @Test
    public void testBookDAOConstructor()
    {
        IBookDAO dao = new BookDAO(new BookHelper());
        assertTrue(dao instanceof IBookDAO);
    }
    
    @Test
    public void testAddBookReturnsIBook()
    {
        IBookDAO dao = new BookDAO(new BookHelper());
        assertTrue(dao.addBook("BookName", "BookAuthor", "CallNumber") instanceof IBook);
    }
    
    @Test
    public void testBookHelperMakesBooks()
    {
        IBookHelper bookHelp = new BookHelper();
        assertTrue(bookHelp.makeBook("Author", "Title", "CallNum", 86) instanceof IBook);
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
        assertTrue(instance.listBooks().size() == 3);
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
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        
        assertTrue(instance.findBooksByAuthor("Author").size() == 2);
        assertTrue(instance.findBooksByAuthor("invalid").size() == 0);
        assertTrue(instance.findBooksByAuthor(null).size() == 0);
        assertTrue(instance.findBooksByAuthor("OtherAuthor").size() == 1);
    }

    /**
     * Test of findBooksByTitle method, of class BookDAO.
     */
    @Test
    public void testFindBooksByTitle() {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        
        assertTrue(instance.findBooksByTitle("Title").size() == 2);
        assertTrue(instance.findBooksByTitle("invalid").size() == 0);
        assertTrue(instance.findBooksByTitle("OtherTitle").size() == 1);
    }

    /**
     * Test of findBooksByAuthorTitle method, of class BookDAO.
     */
    @Test
    public void testFindBooksByAuthorTitle() {
        BookDAO instance = new BookDAO(new BookHelper());
        instance.addBook("Author", "Title", "CallNumber");
        instance.addBook("OtherAuthor", "Title", "CallNumber");
        instance.addBook("Author", "OtherTitle", "CallNumber");
        
        assertTrue(instance.findBooksByAuthorTitle("OtherAuthor", "Title").size() == 1);
        assertTrue(instance.findBooksByAuthorTitle("Author", "Title").size() == 1);
        assertTrue(instance.findBooksByAuthorTitle("Author", "OtherTitle").size() == 1);
    }
    
}
