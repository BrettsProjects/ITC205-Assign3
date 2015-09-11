/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import java.util.List;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

/**
 *
 * @author Brett Smith
 */
public class BookDAO implements IBookDAO {
    
    private IBookHelper iBookHelper_;
    private int currentBookCount_;
    
    public BookDAO(IBookHelper helper)
    {
        if (helper == null)
        {
            throw new IllegalArgumentException("The helper object cannot "
                    + "be null.");
        }
        else
        {
            iBookHelper_ = helper;
        }
    }

    @Override
    public IBook addBook(String author, String title, String callNo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IBook getBookByID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IBook> listBooks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IBook> findBooksByAuthor(String author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IBook> findBooksByTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IBook> findBooksByAuthorTitle(String author, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
