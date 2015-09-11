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
     * Test of addBook method, of class BookDAO.
     */
    @Test
    public void testAddBook() {
        System.out.println("addBook");
        String author = "";
        String title = "";
        String callNo = "";
        BookDAO instance = null;
        IBook expResult = null;
        IBook result = instance.addBook(author, title, callNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
