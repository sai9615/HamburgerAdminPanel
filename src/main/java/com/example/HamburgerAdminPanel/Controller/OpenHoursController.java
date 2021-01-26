package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.OpenHours;
import com.example.HamburgerAdminPanel.Service.OpenHoursServiceImpl;
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
    public ResponseEntity<?> findAllOpenHours(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size){
        try {
            return new ResponseEntity<>(openHoursService.getAllDays(page,size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/day-of-week")
    public ResponseEntity<?> findByDayOfWeek(@RequestParam String day, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size){
        try {
            return new ResponseEntity<>(openHoursService.findByDayOfWeek(day, page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/{id}")
    public ResponseEntity<?> findByOpenHourId(@PathVariable("id") String id){
        try {
            return new ResponseEntity<>(openHoursService.findById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/open-hours/dates")
    public ResponseEntity<?> findByDate(@RequestParam String date){
        try {
            return new ResponseEntity<>(openHoursService.findByDate(date), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/admin/open-hours")
    public ResponseEntity<?> addOpenHours(@RequestBody List<OpenHours> openHours){
        try {
            openHoursService.postNewDays(openHours);
            return new ResponseEntity<>("Created new open hours", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/admin/open-hours/{id}")
    public ResponseEntity<?> addOpenHours(@PathVariable("id") String id, @RequestBody OpenHours openHours){
        try {
            openHoursService.updateDay(id, openHours);
            return new ResponseEntity<>("Updated open hour", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/admin/open-hours")
    public ResponseEntity<?> deleteAllHours(){
        try {
            openHoursService.deleteAllDays();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/admin/open-hours/{id}")
    public ResponseEntity<?> deleteOpenHoursById(@PathVariable("id") String id){
        try {
            openHoursService.deleteADay(id);
            return new ResponseEntity<>("Deleted item with id: "+id,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
