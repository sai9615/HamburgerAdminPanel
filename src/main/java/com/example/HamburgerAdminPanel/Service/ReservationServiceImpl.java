package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Reservation;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.ReservationRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    /**
     * @param firstName
     * @return reservation
     */
    @Override
    public Reservation findByFirstName(String firstName) {
        Optional<Reservation> reservation = reservationRepository.findByFirstName(firstName);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with First Name" + firstName + " not found");
        }
    }

    /**
     * @param lastName
     * @return reservation
     */
    @Override
    public Reservation findByLastName(String lastName) {
        Optional<Reservation> reservation = reservationRepository.findByLastName(lastName);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with Last Name" + lastName + " not found");
        }
    }

    /**
     * @param id
     * @return reservation
     */
    @Override
    public Reservation findByReservationId(String id) {
        Optional<Reservation> reservation = reservationRepository.findByReservationId(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new ResourceNotFoundException("Reservation with id: " + id + " doesn't exist");
        }
    }

    /**
     * @return all reservations
     */
    @Override
    public List<Reservation> findAllReservations(int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        Page<Reservation> reservations = reservationRepository.findAll(paging);
        return reservations.getContent();
    }

    /**
     * @param reservations
     */
    @Override
    public void createReservation(List<Reservation> reservations) {
        Date currentDate = new Date();
        List<Reservation> allReservations = reservationRepository.findAll();
        reservations.forEach(reservation -> {
                    if (reservation.getDay().before(currentDate)) {
                        throw new InvalidInputException("Please select a date after current date");
                    }
                    for (Reservation reserve : allReservations) {
                        if (reserve.getDay().equals(reservation.getDay())) {
                            throw new InvalidInputException("Can't reserve, reservation exist for id: " + reserve.getReservationId() + " on day " + reserve.getDay());
                        }
                    }
                    reservationRepository.save(reservation);
                    allReservations.add(reservation);
                }
        );
    }

    /**
     * @param id
     * @param reservation
     */
    @Override
    public void updateReservation(String id, Reservation reservation) {
        val reservationOptional = reservationRepository.findByReservationId(id);
        if (reservationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Reservation with id: " + id + " doesn't exist");
        }
        Date currentDate = new Date();
        if (reservation.getDay().before(currentDate)) {
            throw new InvalidInputException("Please select a date after current date");
        }
        List<Reservation> allReservations = reservationRepository.findAll();
        for (Reservation reserve : allReservations) {
            if (reserve.getDay().equals(reservation.getDay())) {
                throw new InvalidInputException("Can't reserve, reservation exist for id: " + reserve.getReservationId());
            }
        }
        reservationOptional.ifPresent(reservations -> {
            reservations.setDay(reservation.getDay());
            reservations.setFirstName(reservation.getFirstName());
            reservations.setLastName(reservation.getLastName());
            reservationRepository.save(reservations);
        });
    }

    /**
     * @param id
     */
    @Override
    public void deleteReservation(String id) {
        Optional<Reservation> reservation = reservationRepository.findByReservationId(id);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new InvalidInputException("Reservation with id: " + id + " doesn't exist");
        }
    }

    /**
     * Delete All reservations
     */
    @Override
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }
}
