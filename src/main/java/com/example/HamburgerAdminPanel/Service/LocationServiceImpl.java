package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Location;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
           return optional.get();
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
