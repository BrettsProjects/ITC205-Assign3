package library.daos;

import java.util.Objects;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * STUB CLASS VERSION OF BOOK. For BookHelperTest.java
 * @author Brett Smith
 */
public class Book implements IBook {
    private final String author_;
    private final String title_;
    private final String callNumber_;
    private final int bookId_;
    private ILoan loanAssociated_;
    private EBookState eBookState_;
    
    /**
     * Default constructor for book. Throws IllegalArgumentException if any of
     * parameters are invalid. i.e. objects are null OR bookId is less than or
     * equal to 0.
     * 
     * @param author
     * @param title
     * @param callNumber
     * @param bookId
     * 
     * @throws IllegalArgumentException 
     */
    public Book(String author, String title, String callNumber, int bookId)
            throws IllegalArgumentException
    {
        if (author == null || author.isEmpty())
        {
            throw new IllegalArgumentException("Author cannot be null or empty string.");
        }
        else
        {
            author_ = author;
        }
        
        if (title == null || title.isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be null or empty string.");
        }
        else
        {
            title_ = title;
        }
        
        if (callNumber == null || callNumber.isEmpty())
        {
            throw new IllegalArgumentException("Call Number cannot be null or empty string.");
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
        
        eBookState_ = eBookState_.AVAILABLE;
    }
    
    /** Method to override the object.equals method for the BookHelperTest.java
     * @param other
     * @return 
     */
    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Book)
        {
            Book book = (Book) other;
            return (book.hashCode() == this.hashCode());
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.author_);
        hash = 59 * hash + Objects.hashCode(this.title_);
        hash = 59 * hash + Objects.hashCode(this.callNumber_);
        hash = 59 * hash + this.bookId_;
        hash = 59 * hash + Objects.hashCode(this.loanAssociated_);
        return hash;
    }

    @Override
    public void borrow(ILoan loan) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ILoan getLoan() {
        return loanAssociated_;
    }

    @Override
    public void returnBook(boolean damaged) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return eBookState_;
    }

    @Override
    public String getAuthor() {
        return author_;
    }

    @Override
    public String getTitle() {
        return title_;
    }

    @Override
    public String getCallNumber() {
        return callNumber_;
    }

    @Override
    public int getID() {
        return bookId_;
    }
}
