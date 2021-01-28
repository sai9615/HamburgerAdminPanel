package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Service.InterceptorServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping(value = "/api")
@EnableSwagger2
public class InterceptorController {

    @Autowired
    InterceptorServiceImpl interceptorService;

    @GetMapping("/user/interceptors")
    @ApiOperation(value = "Display all the interceptors data using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findAllInterceptions(@ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                                  @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "5") int size){
    try{
        return new ResponseEntity<>(interceptorService.getAllInterceptions(page, size), HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/interceptors/{id}")
    @ApiOperation(value = "Fetch an interceptor's data using it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "ID Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findInterceptionById(@ApiParam("ID of the interception") @PathVariable("id") long id){
        try{
            return new ResponseEntity<>(interceptorService.getInterceptionById(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/interceptors/api")
    @ApiOperation(value = "Filter and display all the interceptors data via their api name, using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Api Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByApiName(@ApiParam("API name to be filtered by") @RequestParam String apiName,
                                           @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                           @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "5") int size){
        try{
            return new ResponseEntity<>(interceptorService.findByApiName(apiName, page, size), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/interceptors/date")
    @ApiOperation(value = "Filter and display all the interceptors data based on the date, using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Date Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> findByDate(@ApiParam("Date (dd-MM-yy) to be filtered by ") @RequestParam String date,
                                        @ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                        @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "5") int size){
        try{
            return new ResponseEntity<>(interceptorService.findAllByGivenDate(date, page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/interceptors")
    @ApiOperation(value = "Add an interception to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> saveInterceptorTime(@ApiParam("Details about the interception to be added") @RequestBody Interceptor interceptor){
        try{
            interceptorService.saveInterception(interceptor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/interceptors/{id}")
    @ApiOperation(value = "Delete an intercepted data based on it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "ID Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteInterceptor(@ApiParam("Id of the interception to be deleted") @PathVariable("id") long id){
        try {
            interceptorService.deleteInterceptionById(id);
            return new ResponseEntity<>("Id: "+id+" deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/interceptors/api")
    @ApiOperation(value = "Delete all the intercepted data for the given API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "API not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteByApiName(@ApiParam("Name of the api for which you want to delete all the interceptions") @RequestParam String apiName){
        try {
            interceptorService.deleteByApiName(apiName);
            return new ResponseEntity<>("All api's data for "+apiName+" deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/interceptors")
    @ApiOperation(value = "Delete all the intercepted data from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllData(){
        try {
            interceptorService.deleteAll();
            return new ResponseEntity<>("Deleted all the data", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
