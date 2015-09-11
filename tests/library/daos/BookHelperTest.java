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
        String author = "Author";
        String title = "Title";
        String callNumber = "CallNumber";
        int id = 50;
        library.daos.BookHelper instance = new library.daos.BookHelper();
        System.out.println("echo 1");
        IBook expResult = new Book(author, title, callNumber, id);
        System.out.println("echo 2");
        IBook result = instance.makeBook(author, title, callNumber, id);
    }
    
}
