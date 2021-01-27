package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Service.LocationServiceImpl;
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
@RequestMapping("/api")
@EnableSwagger2
public class LocationController {
    @Autowired
    LocationServiceImpl locationService;

    @GetMapping(value = "/user/locations/{id}")
    @ApiOperation(value = "Fetch a location's data using it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByLocationId(@ApiParam("ID of the location") @PathVariable("id") String locationId) {
        try{
            return new ResponseEntity<>(locationService.findByLocationsId(locationId), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/locations")
    @ApiOperation(value = "Display all the locations using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?>  findAllLocations(@ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                               @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size) {
        try {
            List<Location> locations =locationService.findAllLocations(page, size);
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/locations/search-nearest-location")
    @ApiOperation(value = "Display the nearby location based on coordinates provided")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Locations Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> searchNearByLocation(@ApiParam("Longitude of the location") @RequestParam(required = true) String longitude,
                                                  @ApiParam("Latitude of the location") @RequestParam(required = true) String latitude){
        try{
            return new ResponseEntity<>(locationService.findNearByLocation(longitude, latitude), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/locations/filter-by-status")
    @ApiOperation(value = "Filter and display all the locations by their status using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Locations Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> searchByStatus(@ApiParam("Status of the locations to be filtered by") @RequestParam(required = true) String status,
                                            @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                            @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(locationService.filterByStatus(Boolean.parseBoolean(status), page, size), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/admin/locations")
    @ApiOperation(value = "Add a list of locations to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> saveLocations(@ApiParam("Details about the locations to be added") @RequestBody List<Location> location) {
        try {
            locationService.saveLocations(location);
            return new ResponseEntity<>("Created new Location's", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/admin/locations/{id}")
    @ApiOperation(value = "Update a location given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateLocation(@ApiParam("Id of the location to be updated") @PathVariable("id") String id,
                                            @ApiParam("Location object with updated data") @RequestBody Location location){
        try{
            locationService.updateLocation(id, location);
            return new ResponseEntity<>("Location updated successfully",HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/admin/locations/{id}")
    @ApiOperation(value = "Delete a location given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "location Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteLocationById(@ApiParam("ID for the location to be deleted") @PathVariable("id") String id){
        try {
            locationService.deleteById(id);
            return new ResponseEntity<>("Deleted item with id: "+id, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/admin/locations")
    @ApiOperation(value = "Delete all the locations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllLocations(){
        try {
            locationService.deleteAll();
            return new ResponseEntity<>("Deleted all locations", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}