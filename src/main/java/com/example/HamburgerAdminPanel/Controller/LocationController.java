package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Repository.LocationRepository;
import com.example.HamburgerAdminPanel.Service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    LocationServiceImpl locationService;

    @GetMapping(value = "/location/{id}")
    public Location findByLocationId(@PathVariable("id") String locationId) {
            return locationService.findByLocationsId(locationId);
    }

    @PostMapping(value = "/location")
    public ResponseEntity<?> saveLocation(@RequestBody List<Location> location) {
        locationService.saveLocation(location);
        return new ResponseEntity<>("Location added successfully", HttpStatus.OK);
    }


    @PutMapping(value = "/location/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable("id") String id, @RequestBody Location location){
        locationService.updateLocation(id, location);
        return new ResponseEntity<>("Location updated successfully",HttpStatus.OK);
    }

    @GetMapping(value = "/location")
    public List<Location>  findAllLocations() {
        return locationService.findAllLocations();
    }

    @DeleteMapping(value = "/location/{id}")
    public void deleteLocationById(@PathVariable("id") String id){
        locationService.deleteById(id);
    }

    @DeleteMapping(value = "/location")
    public void deleteAllLoctions(){
        locationService.deleteAll();
    }
}