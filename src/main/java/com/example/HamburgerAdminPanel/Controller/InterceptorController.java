package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import com.example.HamburgerAdminPanel.Service.InterceptorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class InterceptorController {

    @Autowired
    InterceptorServiceImpl interceptorService;

    @GetMapping("/interceptors")
    public ResponseEntity<?> findAllInterceptions(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size){
    try{
        return new ResponseEntity<>(interceptorService.getAllInterceptions(page, size), HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/interceptors/{id}")
    public ResponseEntity<?> findInterceptionById(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(interceptorService.getInterceptionById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/interceptors/api")
    public ResponseEntity<?> findByApiName(@RequestParam String apiName, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(interceptorService.findByApiName(apiName, page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/interceptors")
    public ResponseEntity<?> saveInterceptorTime(@RequestBody Interceptor interceptor){
        interceptorService.saveInterception(interceptor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/interceptors/{id}")
    public ResponseEntity<?> deleteInterceptor(@PathVariable("id") long id){
        try {
            interceptorService.deleteInterceptionById(id);
            return new ResponseEntity<>("Id: "+id+" deleted successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/interceptors/api")
    public ResponseEntity<?> deleteByApiName(@RequestParam String apiName){
        try {
            interceptorService.deleteByApiName(apiName);
            return new ResponseEntity<>("All api's data for "+apiName+" deleted successfully", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/interceptors")
    public ResponseEntity<?> deleteAllData(){
        try {
            interceptorService.deleteAll();
            return new ResponseEntity<>("Deleted all the data", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
