package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Reservation;
import com.example.HamburgerAdminPanel.Service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@EnableSwagger2
public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    @GetMapping("/user/reservations")
    public ResponseEntity<?> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(reservationService.findAllReservations(page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/reservations/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable("id") String id ){
        try{
            return new ResponseEntity<>(reservationService.findByReservationId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/reservations/getByName")
    public ResponseEntity<?> getReservationByFirstName(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName){
        try{
            if(firstName!= null){
                return new ResponseEntity<>(reservationService.findByFirstName(firstName), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(reservationService.findByLastName(lastName), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/reservations")
    public  ResponseEntity<?> saveAllReservations(@RequestBody List<Reservation> reservations){
        try{
            reservationService.createReservation(reservations);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/reservations/{id}")
    public  ResponseEntity<?> updateReservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
        try{
            reservationService.updateReservation(id, reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/reservations/{id}")
    public ResponseEntity<?> deleteReservationById(@PathVariable("id") String id){
        try{
            reservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/reservations")
    public ResponseEntity<?> deleteAllReservations(){
        try{
            reservationService.deleteAllReservations();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
