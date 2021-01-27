package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * @param longitude
     * @param latitude
     * @return nearest location
     */
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
            throw new ResourceNotFoundException("Near by location is in active");
        }
        return location;
    }

    /**
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return distance between two locations given their coordinates
     */
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
     * @param status
     * @param page
     * @param size
     * @return list of locations filtered by their status
     */
    @Override
    public List<Location> filterByStatus(Boolean status, int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        List<Location> locations = locationRepository.findByStatus(status, paging);
        if(locations.isEmpty()){
            throw new ResourceNotFoundException("Locations with status: "+status+" doesn't exist");
        } else {
            return locations;
        }
    }

    /**
     * @return list of location objects
     */
    @Override
    public List<Location> findAllLocations(int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        Page<Location> locations = locationRepository.findAll(paging);
        return locations.getContent();
    }

    /**
     * Delete everything
     */
    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }
}
