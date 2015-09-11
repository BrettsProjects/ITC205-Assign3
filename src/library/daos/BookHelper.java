/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.daos;

import library.entities.Book;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

/**
 *
 * @author Brett Smith
 */
public class BookHelper implements IBookHelper {

    @Override
    public IBook makeBook(String author, String title, String callNumber, int id) {
        return new Book(author, title, callNumber, id);
    }
    
}
