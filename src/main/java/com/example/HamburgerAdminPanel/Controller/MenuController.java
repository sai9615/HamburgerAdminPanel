package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Menu;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Service.MenuServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Display all the menu items using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findAllMenuItems(@ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                              @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try {
            List<Menu> menus = menuService.findAllMenuItems(page ,size);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/{itemId}")
    @ApiOperation(value = "Fetch a menu item using it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Menu Item Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByItemId(@ApiParam("ID of the menu") @PathVariable("itemId") String id){
        try {
            return new ResponseEntity<>(menuService.findByItemId(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/categories")
    @ApiOperation(value = "Fetch all menu items of a given category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Menu Items Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByCategory(@ApiParam("Category of the items to be fetched") @RequestParam String category,
                                            @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                            @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.findByCategory(category, page, size), HttpStatus.OK);
        } catch (InvalidInputException | ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/menu-items")
    @ApiOperation(value = "Fetch a menu item by it's name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Menu Item Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByMenuItem(@ApiParam("Menu item name")@RequestParam String name){
       try{
           return new ResponseEntity<>(menuService.findByMenuItem(name), HttpStatus.OK);
       } catch (ResourceNotFoundException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping(value = "/user/menus/filter-by-status")
    @ApiOperation(value = "Filter and display all the menu items by their status using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Menu Items Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> filterByStatus(@ApiParam("Status of the menu items to be filtered by") @RequestParam String status,
                                            @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                            @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.filterByStatus(Boolean.parseBoolean(status), page, size), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/filter-by-menu-types-category")
    @ApiOperation(value = "Filter and display all the menu items by their menu type using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Menu Items Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> filterByCategoryAndMenuType(@ApiParam("Menu type includes breakfast, lunch and dinner") @RequestParam String menuType,
                                                         @ApiParam("Category of the menu item") @RequestParam String category,
                                                         @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                                         @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.findByMenuTypeAndCategory(menuType, category, page,size), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/menus/filter-by-menu-types")
    @ApiOperation(value = "Filter and display all the menu items by their menu type using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Menu Items Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> filterByMenuType(@ApiParam("Menu type includes breakfast, lunch and dinner") @RequestParam String menuType,
                                                         @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                                         @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(menuService.findByMenuType(menuType, page,size), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/admin/menus")
    @ApiOperation(value = "Add a list of menu items to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createMenuItem(@ApiParam("List of menu items") @RequestBody List<Menu> menu){
        try {
            menuService.saveAllMenuItems(menu);
            return new ResponseEntity<>("Created new menu items", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/admin/menus/menu-item/{id}")
    @ApiOperation(value = "Update a Menu Item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Menu Item Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateMenuItem(@ApiParam("Id of the menu item to be updated") @PathVariable("id") String id,
                                            @ApiParam("Menu object with new data") @RequestBody Menu menu){
        try {
            menuService.updateMenuItem(id, menu);
            return new ResponseEntity<>("Updated menu item with id: "+id,HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/admin/menus/{id}")
    @ApiOperation(value = "Delete a menu item given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Menu Item Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteMenuItem(@ApiParam("ID for the menu item to be deleted") @PathVariable("id") String id){
        try {
            menuService.deleteMenuItem(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/menus")
    @ApiOperation(value = "Delete all the menu items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllMenuItems(){
        try {
            menuService.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
