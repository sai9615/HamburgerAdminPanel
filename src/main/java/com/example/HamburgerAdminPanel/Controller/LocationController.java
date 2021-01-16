package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableSwagger2
public class LocationController {
    @Autowired
    LocationServiceImpl locationService;

    @GetMapping(value = "/location/{id}")
    public ResponseEntity<?> findByLocationId(@PathVariable("id") String locationId) {
        try{
            return new ResponseEntity<>(locationService.findByLocationsId(locationId), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/location")
    public ResponseEntity<?>  findAllLocations() {
        try {
            List<Location> locations =locationService.findAllLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/location")
    public ResponseEntity<?> saveLocations(@RequestBody List<Location> location) {
        try {
            locationService.saveLocations(location);
            return new ResponseEntity<>("Created new Location's", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/location/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable("id") String id, @RequestBody Location location){
        try{
            locationService.updateLocation(id, location);
            return new ResponseEntity<>("Location updated successfully",HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/location/{id}")
    public ResponseEntity<?> deleteLocationById(@PathVariable("id") String id){
        try {
            locationService.deleteById(id);
            return new ResponseEntity<>("Deleted item with id: "+id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/location")
    public ResponseEntity<?> deleteAllLocations(){
        try {
            locationService.deleteAll();
            return new ResponseEntity<>("Deleted all items", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}