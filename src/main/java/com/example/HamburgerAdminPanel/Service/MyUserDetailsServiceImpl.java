package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.User;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUserName(userName);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User with userName: "+userName+" doesn't exist");
        }
        return new MyUserDetails(user.get());
    }
}
