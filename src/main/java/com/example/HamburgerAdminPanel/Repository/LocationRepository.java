package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    Optional<Location> findByLocationId(String locationId);
    List<Location> findByStatus(Boolean status, Pageable pageable);
}
