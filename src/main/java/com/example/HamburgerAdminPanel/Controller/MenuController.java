package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Menu;
import com.example.HamburgerAdminPanel.Service.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MenuController {

    @Autowired
    MenuServiceImpl menuService;

    @GetMapping(value = "/menu")
    public ResponseEntity<?> findAllMenuItems(){
        try {
            List<Menu> menus = menuService.findAllMenuItems();
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/menu/{menuId}")
    public ResponseEntity<?> findByMenuId(@PathVariable("menuId") String id){
        try {
            return new ResponseEntity<>(menuService.findByMenusId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/menu/menuType")
    public ResponseEntity<?> findByMenuType(@RequestParam String type){
        try{
            return new ResponseEntity<>(menuService.findByMenuType(type), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/menu/menuItem")
    public ResponseEntity<?> findByMenuItem(@RequestParam String name){
       try{
           return new ResponseEntity<>(menuService.findByMenuItem(name), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping(value = "/menu/menuItems")
    public ResponseEntity<?> createMenuItem(@RequestBody List<Menu> menu){
        try {
            menuService.saveAllMenuItems(menu);
            return new ResponseEntity<>("Created new menu items", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/menu/menuItem/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("id") String
                                                id, @RequestBody Menu menu){
        try {
            menuService.updateMenuItem(id, menu);
            return new ResponseEntity<>("Updated menu item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/menu/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") String id){
        try {
            menuService.deleteMenuItem(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/menu")
    public ResponseEntity<?> deleteAllMenuItems(){
        try {
            menuService.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
