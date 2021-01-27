package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.OpenHours;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Service.OpenHoursServiceImpl;
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
public class OpenHoursController {

    @Autowired
    OpenHoursServiceImpl openHoursService;

    @GetMapping(value = "/user/open-hours")
    @ApiOperation(value = "Display all the open hours using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findAllOpenHours(@ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                              @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try {
            return new ResponseEntity<>(openHoursService.getAllDays(page,size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/day-of-week")
    @ApiOperation(value = "Filter and display open hours by the day of week")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "No Open Hours Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByDayOfWeek(@ApiParam("Day of the week") @RequestParam String day,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size){
        try {
            return new ResponseEntity<>(openHoursService.findByDayOfWeek(day, page, size), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/{id}")
    @ApiOperation(value = "Fetch an open hour object using it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByOpenHourId(@ApiParam("ID for the open hour") @PathVariable("id") String id){
        try {
            return new ResponseEntity<>(openHoursService.findById(id), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/dates")
    @ApiOperation(value = "Fetch an open hour object on a given date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByDate(@ApiParam("Date to find by")@RequestParam String date){
        try {
            return new ResponseEntity<>(openHoursService.findByDate(date), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/admin/open-hours")
    @ApiOperation(value = "Add a list of open hours to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> addOpenHours(@ApiParam("Details about the open hours to be added") @RequestBody List<OpenHours> openHours){
        try {
            openHoursService.postNewDays(openHours);
            return new ResponseEntity<>("Created new open hours", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/admin/open-hours/{id}")
    @ApiOperation(value = "Update a open hour given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> addOpenHours(@ApiParam("Id of the open hour to be updated") @PathVariable("id") String id,
                                          @ApiParam("Open hour object with updated data") @RequestBody OpenHours openHours){
        try {
            openHoursService.updateDay(id, openHours);
            return new ResponseEntity<>("Updated open hour", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/open-hours")
    @ApiOperation(value = "Delete all open hour")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllHours(){
        try {
            openHoursService.deleteAllDays();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/admin/open-hours/{id}")
    @ApiOperation(value = "Delete a open hour given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Open Hour Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteOpenHoursById(@ApiParam("ID for the open hour to be deleted") @PathVariable("id") String id){
        try {
            openHoursService.deleteADay(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
