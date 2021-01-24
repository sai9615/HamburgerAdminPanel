package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Menu;
import com.example.HamburgerAdminPanel.Service.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@EnableSwagger2
public class MenuController {

    @Autowired
    MenuServiceImpl menuService;

    @GetMapping(value = "/menus")
    public ResponseEntity<?> findAllMenuItems(){
        try {
            List<Menu> menus = menuService.findAllMenuItems();
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/menus/{itemId}")
    public ResponseEntity<?> findByItemId(@PathVariable("itemId") String id){
        try {
            return new ResponseEntity<>(menuService.findByItemId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/menus/categories")
    public ResponseEntity<?> findByCategory(@RequestParam String type){
        try{
            return new ResponseEntity<>(menuService.findByCategory(type), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/menus/menu-items")
    public ResponseEntity<?> findByMenuItem(@RequestParam String name){
       try{
           return new ResponseEntity<>(menuService.findByMenuItem(name), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping(value = "/menus/filter-by-status")
    public ResponseEntity<?> filterByStatus(@RequestParam String status){
        try{
            return new ResponseEntity<>(menuService.filterByStatus(Boolean.parseBoolean(status)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/menus")
    public ResponseEntity<?> createMenuItem(@RequestBody List<Menu> menu){
        try {
            menuService.saveAllMenuItems(menu);
            return new ResponseEntity<>("Created new menu items", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/menus/menu-item/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("id") String
                                                id, @RequestBody Menu menu){
        try {
            menuService.updateMenuItem(id, menu);
            return new ResponseEntity<>("Updated menu item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/menus/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") String id){
        try {
            menuService.deleteMenuItem(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/menus")
    public ResponseEntity<?> deleteAllMenuItems(){
        try {
            menuService.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
