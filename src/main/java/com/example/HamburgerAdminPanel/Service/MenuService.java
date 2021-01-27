package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    Menu findByItemId(String id);
    List<Menu> findByCategory(String type, int page, int size);
    List<Menu> findAllMenuItems(int page, int size);
    List<Menu> findByMenuTypeAndCategory(String type, String category, int page, int size);
    Menu findByMenuItem(String itemName);
    List<Menu> findByMenuType(String menuType, int page, int size);
    List<Menu> filterByStatus(Boolean status, int page, int size);
    void saveAllMenuItems(List<Menu> menu);
    void updateMenuItem(String menuId, Menu updatedMenu);
    void deleteMenuItem(String menuId);
    void deleteAll();
}
