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

    @GetMapping(value = "/user/menus")
    public ResponseEntity<?> findAllMenuItems(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size){
        try {
            List<Menu> menus = menuService.findAllMenuItems(page ,size);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/{itemId}")
    public ResponseEntity<?> findByItemId(@PathVariable("itemId") String id){
        try {
            return new ResponseEntity<>(menuService.findByItemId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user/menus/categories")
    public ResponseEntity<?> findByCategory(@RequestParam String type, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.findByCategory(type, page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user/menus/menu-items")
    public ResponseEntity<?> findByMenuItem(@RequestParam String name){
       try{
           return new ResponseEntity<>(menuService.findByMenuItem(name), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping(value = "/user/menus/filter-by-status")
    public ResponseEntity<?> filterByStatus(@RequestParam String status, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.filterByStatus(Boolean.parseBoolean(status), page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user/menus/filter-by-menutype-category")
    public ResponseEntity<?> filterByCategoryAndMenuType(@RequestParam String menuType, @RequestParam String category, @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.findByMenuTypeAndCategory(menuType, category, page,size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/admin/menus")
    public ResponseEntity<?> createMenuItem(@RequestBody List<Menu> menu){
        try {
            menuService.saveAllMenuItems(menu);
            return new ResponseEntity<>("Created new menu items", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/admin/menus/menu-item/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("id") String
                                                id, @RequestBody Menu menu){
        try {
            menuService.updateMenuItem(id, menu);
            return new ResponseEntity<>("Updated menu item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/admin/menus/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") String id){
        try {
            menuService.deleteMenuItem(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/admin/menus")
    public ResponseEntity<?> deleteAllMenuItems(){
        try {
            menuService.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
