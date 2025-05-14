package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Author;
import com.librarymanagement.demo.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) {
        Author savedAuthor = authorService.save(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> retrieve(@PathVariable int id) {
        Author author = authorService.retrieve(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity<List<Author>> retrieveAll() {
        List<Author> authors = authorService.retrieveAll();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        author.setAuthorId(id);
        Author updatedAuthor = authorService.update(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Author>> search(@RequestParam String firstName,@RequestParam String lastName) {
        List<Author> authors = authorService.searchByName(firstName,lastName);
        return ResponseEntity.ok(authors);
    }
}
