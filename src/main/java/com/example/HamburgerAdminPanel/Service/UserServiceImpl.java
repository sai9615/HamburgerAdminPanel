package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.User;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUserEmail(String emailId) {
        Optional<User> user = userRepository.findByEmail(emailId);
        if (user.isEmpty()){
            log.debug( "User with emailId: "+emailId+" doesn't exist");
            throw new ResourceNotFoundException("User with emailId: "+emailId+" doesn't exist");
        }
        return user.get();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isEmpty()){
            log.debug(" User with emailId: "+userName+" doesn't exist");
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
                log.debug( " User already exist in the database");
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
            log.debug(" User name: "+userName+" not found");
            throw new ResourceNotFoundException("User name: "+userName+" not found");
        }
    }

    @Override
    public void deleteAllUsers() {
        try {
            userRepository.deleteAll();
        } catch (Exception e){
            log.debug( " Internal server error");
            throw new ResourceNotFoundException("Internal error");
        }
    }
}
