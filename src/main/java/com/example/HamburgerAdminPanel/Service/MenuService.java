package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    Optional<Menu> findByMenusId(String id);
    List<Menu> findByMenuType(String type);
    Menu findByMenuItem(String itemName);
    void saveMenuItem(List<Menu> menu);
    void updateMenuItem(String menuId, Menu updatedMenu);
    void deleteMenuItem(String menuId);
    void deleteAll();
}
