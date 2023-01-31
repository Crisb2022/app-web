package com.distribuida.service;

import com.distribuida.db.Book;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(int id);
    void deleteBook(int id);
    void updateBook(int id, Book book);
    void createBook( Book book);


}
