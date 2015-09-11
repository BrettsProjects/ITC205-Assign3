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
        System.out.println("makeBook");
        String author = "Author";
        String title = "Title";
        String callNumber = "CallNumber";
        int id = 50;
        BookHelper instance = new BookHelper();
        IBook expResult = new Book(author, title, callNumber, id);
        IBook result = instance.makeBook(author, title, callNumber, id);
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
    }
    
}
