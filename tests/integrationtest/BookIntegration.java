/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import library.daos.BookDAO;
import library.daos.BookHelper;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

/**
 * All integration tests for the Book components of the application. 
 * @author Brett Smith
 */
public class BookIntegration extends junit.framework.TestCase {
    
    public BookIntegration()
    {
        
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
    
}
