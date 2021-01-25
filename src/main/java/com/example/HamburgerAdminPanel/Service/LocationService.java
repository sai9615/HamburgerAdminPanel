package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Location;

import java.util.List;


public interface LocationService {
    Location findByLocationsId(String id);
    void updateLocation(String id, Location loc);
    List<Location> findAllLocations(int page, int size);
    Location findNearByLocation(String longitude, String Latitude);
    List<Location> filterByStatus(Boolean status, int page, int size);
    void saveLocations(List<Location> loc);
    void deleteById(String id);
    void deleteAll();
}
