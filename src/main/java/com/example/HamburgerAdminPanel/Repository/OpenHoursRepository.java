package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.OpenHours;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpenHoursRepository extends MongoRepository <OpenHours,String> {
    Optional<OpenHours> findById(String id);
    Optional<List<OpenHours>> findByDayOfWeekIgnoreCase(String dayOfWeek, Pageable pageable);
    Optional<OpenHours> findByDate(Date date);
}
