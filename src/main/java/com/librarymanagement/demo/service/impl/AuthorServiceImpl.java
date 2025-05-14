package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.authorException.AuthorNotFoundException;
import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.repository.AuthorRepository;
import com.librarymanagement.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author retrieve(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: " + id));
    }

    @Override
    public List<Author> retrieveAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(Author author) {
        if (!authorRepository.existsById(author.getAuthorId())) {
            throw new AuthorNotFoundException("Cannot update non-existing author with ID: " + author.getAuthorId());
        }
        return authorRepository.save(author);
    }


    @Override
    public void delete(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: " + id));
        authorRepository.delete(author);
    }

    @Override
    public List<Author> searchByName(String firstName, String lastName) {
//        List<Author> authors = authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
//        if (authors.isEmpty()) {
//            throw new AuthorNotFoundException("Author not found with name: " + firstName + " " + lastName);
//        }
//        return authors;
            return Optional.of(authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName))
                    .filter(authors -> !authors.isEmpty())
                    .orElseThrow(() -> new AuthorNotFoundException("Author not found with name: " + firstName + " " + lastName));


    }



}
