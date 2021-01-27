package com.example.HamburgerAdminPanel.Repository;

import com.example.HamburgerAdminPanel.Entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
    Optional<Menu> findByItemId(String itemId);
    Optional<List<Menu>> findByCategoryIgnoreCase(String category, Pageable pageable);
    Optional<List<Menu>> findByMenuTypeIgnoreCase(String type, Pageable pageable);
    Optional<Menu> findByItemNameIgnoreCase(String menuItem);
    Optional<List<Menu>> findByStatus(Boolean status, Pageable pageable);
    Optional<List<Menu>> findByMenuTypeAndCategoryIgnoreCase(String type, String category, Pageable pageable);
}
