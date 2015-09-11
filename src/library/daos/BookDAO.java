/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import java.util.ArrayList;
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
    private List<IBook> bookList_;
    
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
            bookList_ = new ArrayList<>();
            currentBookCount_ = 0;
        }
    }

    @Override
    public IBook addBook(String author, String title, String callNo) {
        IBook newBook = iBookHelper_.makeBook(author, title, callNo, currentBookCount_);
        bookList_.add(newBook);
        currentBookCount_++;
        return newBook;
    }

    @Override
    public IBook getBookByID(int id) {
        try
        {
            return bookList_.get(id);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    @Override
    public List<IBook> listBooks() {
        return bookList_;
    }

    @Override
    public List<IBook> findBooksByAuthor(String author) {  
        ArrayList<IBook> returnList = new ArrayList<>();
        for (IBook book : bookList_)
        {
            if (book.getAuthor().equals(author))
            {
                returnList.add(book);
            }
        }
        
        return returnList;
    }

    @Override
    public List<IBook> findBooksByTitle(String title) {
        ArrayList<IBook> returnList = new ArrayList<>();
        for (IBook book : bookList_)
        {
            if (book.getTitle().equals(title))
            {
                returnList.add(book);
            }
        }
        
        return returnList;
    }

    @Override
    public List<IBook> findBooksByAuthorTitle(String author, String title) {
        ArrayList<IBook> returnList = new ArrayList<>();
        for (IBook book : bookList_)
        {
            if (book.getAuthor().equals(author) && book.getTitle().equals(title))
            {
                returnList.add(book);
            }
        }
        
        return returnList;
    }
    
}
