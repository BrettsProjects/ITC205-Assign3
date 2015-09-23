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
 *
 * @author Brett.Smith
 */
public class BookHelperIT {
    
    public BookHelperIT() {
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
        int id = 10;
        BookHelper instance = new BookHelper();
        
        IBook result = instance.makeBook(author, title, callNumber, id);
        assertTrue(result.getAuthor().equals(author));
        assertTrue(result.getTitle().equals(title));
        assertTrue(result.getCallNumber().equals(callNumber));
        assertTrue(result.getID() == id);
    }
    
}
