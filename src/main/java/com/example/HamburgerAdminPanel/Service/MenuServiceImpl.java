package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Menu;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    /**
     * @param id
     * @return menu object
     */
    @Override
    public Optional<Menu> findByMenusId(String id) {
        Optional<Menu> menu = menuRepository.findByMenuId(id);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id " + id + " not found");
        } else {
            return menuRepository.findByMenuId(id);
        }
    }

    /**
     * @param type
     * @return list of menu items of same menu type
     */
    @Override
    public List<Menu> findByMenuType(String type) {
        List<Menu> menu = menuRepository.findByMenuType(type);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu type " + type + " not found");
        } else {
            return menu;
        }
    }

    /**
     * @param itemName
     * @return menu item
     */
    @Override
    public Menu findByMenuItem(String itemName) {
        Optional<Menu> menu = Optional.ofNullable(menuRepository.findByMenuItem(itemName));
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item  " + itemName + " not found");
        } else {
            return menu.get();
        }
    }

    /**
     * @param menu
     */
    @Override
    public void saveMenuItem(List<Menu> menu) {
        menu.forEach(item -> menuRepository.save(item));
    }

    /**
     * @param menuId
     * @param updatedMenu
     */
    @Override
    public void updateMenuItem(String menuId, Menu updatedMenu) {
        Optional<Menu> menu = menuRepository.findByMenuId(menuId);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id: " + menuId + " not found");
        }
        menu.ifPresent(menuItem -> {
            menuItem.setMenuType(updatedMenu.getMenuType());
            menuItem.setMenuItem(updatedMenu.getMenuItem());
            menuItem.setCost(updatedMenu.getCost());
        });
    }

    /**
     * @param menuId
     */
    @Override
    public void deleteMenuItem(String menuId) {
        Optional<Menu> menu = menuRepository.findByMenuId(menuId);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id: " + menuId + " not found");
        } else {
            menuRepository.deleteById(menuId);
        }
    }

    @Override
    public void deleteAll() {
        menuRepository.deleteAll();
    }
}
