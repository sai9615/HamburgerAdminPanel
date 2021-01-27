package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.User;
import com.example.HamburgerAdminPanel.Service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
@RequestMapping(value = "/api")
@EnableSwagger2
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/admin/my-users")
    @ApiOperation(value = "Fetch all user's data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findAllUsers(){
            try {
                return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PostMapping(value = "/my-users")
    @ApiOperation(value = "Add a User to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> addUser(@ApiParam("Details about the user to be added") @RequestBody User user){
        try {
            userService.addUser(user);
            return new ResponseEntity<>("Added user", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/my-users")
    @ApiOperation(value = "Delete a user given their username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "User Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteUser(@RequestParam String userName){
        try {
            userService.deleteByUserName(userName);
            return new ResponseEntity<>("Deleted user", HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/my-users/all-users")
    @ApiOperation(value = "Delete all the users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllMyUsers(){
        try {
            userService.deleteAllUsers();
            return new ResponseEntity<>("Deleted all users", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
