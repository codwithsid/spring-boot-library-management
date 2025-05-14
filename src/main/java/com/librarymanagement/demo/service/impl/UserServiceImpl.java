package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.repository.UserRepository;
import com.librarymanagement.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> retrieveAll() {
        return userRepository.findAll();
    }

    @Override
    public User retrieve(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    @Override
    public User update(User newUser, int id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        newUser.setUserId(existingUser.getUserId());
        return userRepository.save(newUser);
    }

    @Override
    public void delete(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        userRepository.delete(user);
    }

    @Override
    public User searchByEmailId(String emailId) {
        User user = userRepository.findByemailId(emailId);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + emailId);
        }
        return user;
    }

    @Override
    public User searchByMobileNumber(String mobileNumber) {
        User user = userRepository.findBymobileNumber(mobileNumber);
        if (user == null) {
            throw new UserNotFoundException("User not found with mobile number: " + mobileNumber);
        }
        return user;
    }
}
