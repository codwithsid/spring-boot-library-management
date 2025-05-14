package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User retrieve(int userId);
    List<User> retrieveAll();
    User update(User user, int id);
    void delete(int userId);
    User searchByEmailId(String emailId);
    User searchByMobileNumber(String mobileNumber);
}
