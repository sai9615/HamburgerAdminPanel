package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.User;

import java.util.Optional;


public interface UserService {
    User findByUserEmail(String emailId);
    Optional<User> findByUserName(String userName);
    Iterable<User> findAllUsers();
    void addUser(User user);
    void  deleteByUserName(String userName);
    void  deleteAllUsers();
}
