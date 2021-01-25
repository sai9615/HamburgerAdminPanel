package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation findByFirstName(String firstName);
    Reservation findByLastName(String LastName);
    Reservation findByReservationId(String id);
    List<Reservation> findAllReservations(int page, int size);
    void createReservation(List<Reservation> reservations);
    void updateReservation(String id, Reservation reservation);
    void deleteReservation(String id);
    void deleteAllReservations();
}
