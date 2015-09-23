/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import library.interfaces.entities.IBook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unfortunately no way to mock the constructor method call. Book has already
 * been unit tested however.
 * @author Brett Smith
 */
public class BookHelperTest {
    
    public BookHelperTest() {
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
     * Test of makeBook method, of class BookHelper.
     */
    @Test
    public void testMakeBook() {
        String author = "Author";
        String title = "Title";
        String callNumber = "CallNumber";
        int id = 50;
        library.daos.BookHelper instance = new library.daos.BookHelper();
        IBook expResult = new StubBook(author, title, callNumber, id);
        IBook result = instance.makeBook(author, title, callNumber, id);
        
        // Test each element that defines the object is present and correct.
        assertEquals(expResult.getAuthor(), result.getAuthor());
        assertEquals(expResult.getCallNumber(), result.getCallNumber());
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getLoan(), result.getLoan());
        assertEquals(expResult.getState(), result.getState());
        assertEquals(expResult.getTitle(), result.getTitle());
    }
    
}
