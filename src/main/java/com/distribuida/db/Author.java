package com.distribuida.db;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Author implements Serializable {

    private Long id;
    private List<Book> books;
    private String firstName;
    private String lastName;

}
