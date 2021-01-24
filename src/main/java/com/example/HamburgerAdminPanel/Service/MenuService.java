package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    Menu findByItemId(String id);
    List<Menu> findByCategory(String type);
    List<Menu> findAllMenuItems();
    Menu findByMenuItem(String itemName);
    List<Menu> filterByStatus(Boolean status);
    void saveAllMenuItems(List<Menu> menu);
    void updateMenuItem(String menuId, Menu updatedMenu);
    void deleteMenuItem(String menuId);
    void deleteAll();
}
