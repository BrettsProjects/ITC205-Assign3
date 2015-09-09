/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
/**
 *
 * @author Brett.Smith
 */
public class Book implements IBook
{
    private String author_;
    private String title_;
    private String callNumber_;
    private int bookId_;
    private ILoan loanAssociated_;
    
    public Book(String author, String title, String callNumber, int bookId)
            throws IllegalArgumentException
    {
        if (author == null)
        {
            throw new IllegalArgumentException("Author cannot be null.");
        }
        else
        {
            author_ = author;
        }
        
        if (title == null)
        {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        else
        {
            title_ = title;
        }
        
        if (callNumber == null)
        {
            throw new IllegalArgumentException("Call Number cannot be null.");
        }
        else
        {
            callNumber_ = callNumber;
        }
       
        if (bookId <= 0 )
        {
            throw new IllegalArgumentException("Book ID cannot be 0 or less than 0.");
        }
        else
        {
            bookId_ = bookId;
        }
    }
    
    @Override
    public void borrow(ILoan loanAssociated) {
        if (loanAssociated == null)
        {
            loanAssociated_ = loanAssociated;
        }
        else
        {
            throw new RuntimeException("This book has already been borrowed by " + loanAssociated_.getID());
        }
    }

    @Override
    public ILoan getLoan() {
        return loanAssociated_;
    }

    @Override
    public void returnBook(boolean damaged) {
        
    }

    @Override
    public void lose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void repair() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EBookState getState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAuthor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCallNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
