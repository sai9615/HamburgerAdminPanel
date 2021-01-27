package com.example.HamburgerAdminPanel.Controller;

import com.example.HamburgerAdminPanel.Entity.Reservation;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Service.ReservationServiceImpl;
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
public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    @GetMapping("/user/reservations")
    @ApiOperation(value = "Display all the reservations using pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getAllReservations(@ApiParam("Page number for pagination, default set to 0") @RequestParam(defaultValue = "0") int page,
                                                @ApiParam("Size of the page, default set to 5") @RequestParam(defaultValue = "3") int size){
        try{
            return new ResponseEntity<>(reservationService.findAllReservations(page, size), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/reservations/{id}")
    @ApiOperation(value = "Fetch a reservation using it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getReservationById(@ApiParam("ID of the reservation") @PathVariable("id") String id ){
        try{
            return new ResponseEntity<>(reservationService.findByReservationId(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/reservations/getByName")
    @ApiOperation(value = "Fetch a reservation by last name or first name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getReservationByFirstNameOrLastName(@ApiParam("First Name") @RequestParam(required = false) String firstName,
                                                                 @ApiParam("Last Name") @RequestParam(required = false) String lastName){
        try{
            if(firstName!= null){
                return new ResponseEntity<>(reservationService.findByFirstName(firstName), HttpStatus.OK);
            } else if(lastName != null) {
                return new ResponseEntity<>(reservationService.findByLastName(lastName), HttpStatus.OK);
            } else  {
                throw new ResourceNotFoundException("No name provided");
            }
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/reservations")
    @ApiOperation(value = "Add a list of reservations to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public  ResponseEntity<?> saveAllReservations(@ApiParam("Details about the reservations to be added") @RequestBody List<Reservation> reservations){
        try{
            reservationService.createReservation(reservations);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/reservations/{id}")
    @ApiOperation(value = "Update a reservation given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Reservation Not Found"),
            @ApiResponse(code = 422, message = "Invalid Data"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public  ResponseEntity<?> updateReservation(@ApiParam("Id of the reservation to be updated") @PathVariable("id") String id,
                                                @ApiParam("Reservation object with updated data") @RequestBody Reservation reservation) {
        try{
            reservationService.updateReservation(id, reservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/reservations/{id}")
    @ApiOperation(value = "Delete a reservation given it's ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 404, message = "Reservation Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteReservationById(@ApiParam("ID for the reservation to be deleted") @PathVariable("id") String id){
        try{
            reservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/reservations")
    @ApiOperation(value = "Delete all the reservations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> deleteAllReservations(){
        try{
            reservationService.deleteAllReservations();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
