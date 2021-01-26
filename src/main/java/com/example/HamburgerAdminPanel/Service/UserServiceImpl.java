package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.User;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUserEmail(String emailId) {
        Optional<User> user = userRepository.findByEmail(emailId);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User with emailId: "+emailId+" doesn't exist");
        }
        return user.get();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User with emailId: "+userName+" doesn't exist");
        }
        return user;
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
            Optional<User> userExist =userRepository.findByUserName(user.getUserName());
            if(!userExist.isEmpty()) {
                throw new RuntimeException("User already exist in the database");
            }
        String pass= user.getPassword();
        String encode= bCryptPasswordEncoder.encode(pass);
            user.setPassword(encode);
            userRepository.save(user);
    }

    @Override
    public void deleteByUserName(String userName) {
        try {
            userRepository.deleteByUserName(userName);
        } catch (Exception e){
            throw new ResourceNotFoundException("User name: "+userName+" not found");
        }
    }

    @Override
    public void deleteAllUsers() {
        try {
            userRepository.deleteAll();
        } catch (Exception e){
            throw new ResourceNotFoundException("Internal error");
        }
    }
}
