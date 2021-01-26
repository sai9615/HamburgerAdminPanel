package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterceptorRepository extends PagingAndSortingRepository<Interceptor, String> {
    Optional<List<Interceptor>> findByApiName(String apiName, Pageable pageable);
    Optional<Interceptor> findByInterceptionId(long id);
    Optional<List<Interceptor>> findAllByDate(Date date, Pageable pageable);
    void deleteByApiName(String apiName);
    void deleteByInterceptionId(long id);
}
