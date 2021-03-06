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
    
    @Override
    /**
     * Allows a book to be borrowed by a particular Loan object. The book first
     * checks its present status and if it is AVAILABLE then the book
     * is allowed to be borrowed by the loan.
     */
    public void borrow(ILoan loanAssociated) {
        if (loanAssociated == null)
        {
            throw new RuntimeException("The associated loan cannot be null.");
        }
        else if (eBookState_ == eBookState_.AVAILABLE && loanAssociated_ == null)
        {
            loanAssociated_ = loanAssociated; // Associates the loan
            eBookState_ = eBookState_.ON_LOAN; // Sets the book status to on loan.
        }
        else
        {
            throw new RuntimeException("This book has already been borrowed by " + loanAssociated_.getID());
        }
    }

    @Override
    /**
     * Returns the associated loan object with this book. If the book isnt
     * presently on loan then null is returned.
     */
    public ILoan getLoan() {
        if (eBookState_ == eBookState_.ON_LOAN)
        {
            return loanAssociated_;
        }
        else
        {
            return null;
        }
    }

    @Override
    /**
     * Return book allows a book to be returned from a loan. If the book is
     * damanged, then the book is put into the DAMAGED state, otherwise it 
     * is AVAILABLE.
     */
    public void returnBook(boolean damaged) {
        if (eBookState_ == eBookState_.ON_LOAN)
        {
            if (damaged)
            {
                eBookState_ = eBookState_.DAMAGED;
            }
            else
            {
                eBookState_ = eBookState_.AVAILABLE;
                loanAssociated_ = null;
            }
        }
        else
        {
            throw new RuntimeException("This book was not presently on loan.");
        }
    }

    @Override
    /**
     * If the book is lost whilst on loan, then it may be set to LOST.
     */
    public void lose() {
        if (eBookState_ == eBookState_.ON_LOAN)
        {
            eBookState_ = eBookState_.LOST;
            loanAssociated_ = null;
        }
        else
        {
            throw new RuntimeException("The book was not on loan.");
        }
    }

    @Override
    /**
     * If the book is damaged, then it may be set to AVAILABLE.
     */
    public void repair() {
        if (eBookState_ == eBookState_.DAMAGED)
        {
            eBookState_ = eBookState_.AVAILABLE;
        }
        else
        {
            throw new RuntimeException("The book was not damaged.");
        }
    }

    @Override
    /**
     * If the book is damaged, lost, or available and we choose to remove it
     * we can.
     */
    public void dispose() {
        if (eBookState_ == eBookState_.AVAILABLE || eBookState_ == 
                eBookState_.LOST || eBookState_ == eBookState_.DAMAGED)
            // If the book is AVAILABLE, LOST, DAMAGED then we may dispose of
            // it.
        {
            eBookState_ = eBookState_.DISPOSED;
        }
        else
        {
            throw new RuntimeException("The book was not in a state to be" +
                    " disposed.");
        }
    }

    @Override
    /**
     * Returns the current state of the book.
     */
    public EBookState getState() {
        return eBookState_;
    }

    @Override
    /**
     * Returns the author of the book.
     */
    public String getAuthor() {
        return author_;
    }

    @Override
    /**
     * Returns the title of the book.
     */
    public String getTitle() {
        return title_;
    }

    @Override
    /**
     * Returns the call number of the book.
     */
    public String getCallNumber() {
        return callNumber_;
    }

    @Override
    /**
     * Returns the ID of the book.
     */
    public int getID() {
        return bookId_;
    }
    
}
