package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Menu;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    /**
     * @param id
     * @return menu object
     */
    @Override
    public Menu findByItemId(String id) {
        Optional<Menu> menu = menuRepository.findByItemId(id);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id " + id + " not found");
        } else {
            Menu obj;
            obj = menu.get();
            if(Boolean.FALSE.equals(obj.getStatus())){
                throw new ResourceNotFoundException("Menu item is in-active, please set the status to active to get item details");
            }
            return obj;
        }
    }

    /**
     * @param category
     * @return list of menu items of same menu type
     */
    @Override
    public List<Menu> findByCategory(String category) {
        Optional<List<Menu>> menu = menuRepository.findByCategory(category);
        if (menu.isEmpty()) {
            throw new InvalidInputException("Category " + category + " not found");
        } else if(menu.get().isEmpty()){
            throw new ResourceNotFoundException("Category " + category + " not found");
        } else {
            List<Menu> menuList = new ArrayList<>();
            for(Menu obj: menu.get()){
                if(Boolean.TRUE.equals(obj.getStatus())){
                    menuList.add(obj);
                }
            }
            if(menuList.isEmpty()){
               throw new ResourceNotFoundException("All Menu item's in "+category+" are in-active, please set the status to active to get item details");
            }
            return menuList;
        }
    }

    /**
     * @param itemName
     * @return menu item
     */
    @Override
    public Menu findByMenuItem(String itemName) {
        Optional<Menu> menu = menuRepository.findByItemName(itemName);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item  " + itemName + " not found");
        } else {
            Menu obj;
            obj = menu.get();
            if(Boolean.FALSE.equals(obj.getStatus())){
                throw new ResourceNotFoundException("Menu item is in-active, please set the status to active to get item details");
            }
            return obj;
        }
    }

    @Override
    public List<Menu> filterByStatus(Boolean status) {
       Optional<List<Menu>> menuList = menuRepository.findByStatus(status);
       if(menuList.isEmpty()){
           throw new ResourceNotFoundException("No menu item with status " + status +" exist in database");
       }
       return menuList.get();
    }

    /**
     * @param menuList
     */
    @Override
    public void saveAllMenuItems(List<Menu> menuList) {
        menuList.forEach(item -> menuRepository.save(item));
    }

    /**
     * @param itemId
     * @param updatedMenu
     */
    @Override
    public void updateMenuItem(String itemId, Menu updatedMenu) {
        Optional<Menu> menu = menuRepository.findByItemId(itemId);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id: " + itemId + " not found");
        }
        menu.ifPresent(menuItem -> {
            menuItem.setCategory(updatedMenu.getCategory());
            menuItem.setMenuType(updatedMenu.getMenuType());
            menuItem.setItemName(updatedMenu.getItemName());
            menuItem.setPrice(updatedMenu.getPrice());
            menuItem.setStatus(updatedMenu.getStatus());
            menuRepository.save(menuItem);
        });
    }

    /**
     * @param itemId
     */
    @Override
    public void deleteMenuItem(String itemId) {
        Optional<Menu> menu = menuRepository.findByItemId(itemId);
        if (menu.isEmpty()) {
            throw new ResourceNotFoundException("Menu item with id: " + itemId + " not found");
        } else {
            menuRepository.deleteById(itemId);
        }
    }

    /**
     * @return list of menu items
     */
    @Override
    public List<Menu> findAllMenuItems(){
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> findByMenuTypeAndCategory(String menuType, String category) {
        Optional<List<Menu>> menus = menuRepository.findByMenuTypeAndCategory(menuType, category);
        if(menus.isEmpty()){
            throw new ResourceNotFoundException("Menu type " + menuType +  "and category "+ category+ " not found");
        } else {
            ArrayList<Menu> menuList = new ArrayList<>();
            for(Menu menuObj: menus.get()){
                if(Boolean.TRUE.equals(menuObj.getStatus())){
                    menuList.add(menuObj);
                }
            }
            if(!menuList.isEmpty()){
                return menuList;
            } else {
                throw new ResourceNotFoundException("No active menu items found");
            }
        }
    }

    @Override
    public void deleteAll() {
        menuRepository.deleteAll();
    }
}
