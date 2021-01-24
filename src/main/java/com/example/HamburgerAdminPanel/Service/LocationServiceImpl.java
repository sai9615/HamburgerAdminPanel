package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    LocationRepository locationRepository;

    /**
     * @param id
     * @return location object
     */
    @Override
    public Location findByLocationsId(String id) {
        Optional<Location> optional  = locationRepository.findByLocationId(id);
        if(optional.isPresent()){
           Location location =  optional.get();
           if(Boolean.FALSE.equals(location.getStatus())){
               throw new ResourceNotFoundException("Location is in-active, please set the status to active to get location details");
           }
           return location;
        } else {
            throw new ResourceNotFoundException("Location with id not found");
        }
    }

    /**
     * @param id
     * @param loc
     */
    @Override
    public void updateLocation(String id, Location loc) {
        Optional<Location> optional  = locationRepository.findByLocationId(id);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("Location with id "+id+" doesn't exist");
        }
        optional.ifPresent(location -> {
            location.setLongitude(loc.getLongitude());
            location.setLatitude(loc.getLatitude());
            location.setPhone(loc.getPhone());
            location.setAddress(loc.getAddress());
            location.setStatus(loc.getStatus());
            locationRepository.save(location);});
    }

    /**
     * @param locations
     */
    @Override
    public void saveLocations(List<Location> locations){
        locations.forEach(loc->locationRepository.save(loc));
    }


    /**
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Optional<Location> optional  = locationRepository.findByLocationId(id);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("Location with id "+id+" doesn't exist");
        }
        locationRepository.deleteById(id);
    }

    @Override
    public Location findNearByLocation(String longitude, String latitude) {
        List<Location> locations = locationRepository.findAll();
        if(locations.isEmpty()){
            throw new ResourceNotFoundException("No locations in Database to filter by");
        }
        ArrayList<Double> distance  = new ArrayList<>();
        for(Location location : locations){
            Double dist = distance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            distance.add(dist);
        }
        int minIndex = distance.indexOf(Collections.min(distance));
        Location location = locations.get(minIndex);
        if(Boolean.FALSE.equals(location.getStatus())){
            throw new ResourceNotFoundException("No near by locations");
        }
        return location;
    }

    @Override
    public List<Location> filterByStatus(Boolean status) {
        List<Location> locations = locationRepository.findByStatus(status);
        if(locations.isEmpty()){
            throw new ResourceNotFoundException("Locations with status doesn't exist");
        } else {
            return locations;
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60;
        dist = dist * 1852;
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * @return list of location objects
     */
    @Override
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    /**
     * Delete everything
     */
    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }
}
