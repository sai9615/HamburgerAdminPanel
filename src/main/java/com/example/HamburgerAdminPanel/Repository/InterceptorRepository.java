package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterceptorRepository extends JpaRepository<Interceptor, String> {
    Optional<List<Interceptor>> findByApiNameIgnoreCase(String apiName, Pageable pageable);
    Optional<Interceptor> findByInterceptionId(long id);
    Optional<List<Interceptor>> findByDate(Date date, Pageable pageable);
    void deleteByApiNameIgnoreCase(String apiName);
    void deleteByInterceptionId(long id);
}
