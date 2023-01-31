package com.distribuida.service;

import com.distribuida.db.Author;
import java.util.List;

public interface AuthorService {

    List<Author> findAll();
    Author findById(Long id);
    void deleteAuthor(Long id);
    void updateAuthor(Long id, Author author);
    void createAuthor( Author author);


}
