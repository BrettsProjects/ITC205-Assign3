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
        System.out.println("makeBook");
        String author = "";
        String title = "";
        String callNumber = "";
        int id = 0;
        BookHelper instance = new BookHelper();
        IBook expResult = null;
        IBook result = instance.makeBook(author, title, callNumber, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
