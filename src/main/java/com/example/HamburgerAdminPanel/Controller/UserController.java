package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.User;
import com.example.HamburgerAdminPanel.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/admin/my-users")
    public ResponseEntity<?> findAllUsers(){
            try {
                return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PostMapping(value = "/my-users")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            userService.addUser(user);
            return new ResponseEntity<>("Added user", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/my-users")
    public ResponseEntity<?> deleteUser(@RequestParam String userName){
        try {
            userService.deleteByUserName(userName);
            return new ResponseEntity<>("Deleted user", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/my-users/all-users")
    public ResponseEntity<?> deleteAllMyUsers(){
        try {
            userService.deleteAllUsers();
            return new ResponseEntity<>("Deleted all users", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
