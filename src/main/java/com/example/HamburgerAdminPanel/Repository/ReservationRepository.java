package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Optional<Reservation> findByFirstNameIgnoreCase(String firstName);
    Optional<Reservation> findByLastNameIgnoreCase(String lastName);
    Optional<Reservation> findByReservationId(String id);
}
