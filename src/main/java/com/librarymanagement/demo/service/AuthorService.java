package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);
    Author retrieve(int authorId);
    List<Author> retrieveAll();
    Author update(Author author);
    void delete(int authorId);
    List<Author> searchByName(String firstName,String lastName);
}
