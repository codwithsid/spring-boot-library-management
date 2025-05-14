package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                address.setUser(user);
            }
        }
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> retrieve(@PathVariable int id) {
        User user = userService.retrieve(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> retrieveAll() {
        List<User> users = userService.retrieveAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.update(user, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<User> search(@RequestParam(required = false) String emailId,
                                       @RequestParam(required = false) String mobileNumber) {
        User user = null;
        if (emailId != null) {
            user = userService.searchByEmailId(emailId);
        } else if (mobileNumber != null) {
            user = userService.searchByMobileNumber(mobileNumber);
        }
        return ResponseEntity.ok(user);
    }
}
