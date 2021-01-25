package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.OpenHours;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.OpenHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OpenHoursServiceImpl implements OpenHoursService{

    @Autowired
    OpenHoursRepository openHoursRepository;

    @Override
    public OpenHours findByDayOfWeek(String dayOfWeek) {
        Optional<OpenHours> openHours = openHoursRepository.findByDayOfWeek(dayOfWeek);
        if (openHours.isEmpty()) {
            throw new ResourceNotFoundException("No such day found");
        } else {
            return openHours.get();
        }
    }

    public OpenHours findById(String id){
        Optional<OpenHours> openHours = openHoursRepository.findById(id);
        if (openHours.isEmpty()) {
            throw new ResourceNotFoundException("No such id: "+id+" exist");
        } else {
            return openHours.get();
        }
    }

    @Override
    public List<OpenHours> getAllDays() {
        List<OpenHours> openHours = openHoursRepository.findAll();
        if (openHours.isEmpty()) {
            throw new ResourceNotFoundException("No data in Database");
        } else {
            return openHours;
        }
    }

    @Override
    public void postNewDays(List<OpenHours> openHours) {
        List<OpenHours> existingOpenHours = openHoursRepository.findAll();
        Date currentDate = new Date();
        openHours.forEach(openHour -> {
                if (openHour.getFromTime().before(currentDate)) {
                    throw new InvalidInputException("Please select a date after current date for id: " +openHour.getId());
                }
                for (OpenHours openHrs : existingOpenHours) {
                    if (openHour.getFromTime().equals(openHrs.getFromTime())) {
                        throw new InvalidInputException("Can't create a new open Hour due to existing open hour with id: " + openHrs.getId() + " on day " + openHrs.getToTime());
                    }
                }
                SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
                openHour.setDayOfWeek(simpleDateformat.format(openHour.getFromTime()));
                openHoursRepository.save(openHour);
                existingOpenHours.add(openHour);
            });
    }

    @Override
    public void updateDay(String id, OpenHours openHour) {
        List<OpenHours> existingOpenHours = openHoursRepository.findAll();
        Date currentDate = new Date();
        if(openHour.getFromTime().before(currentDate)){
            throw new InvalidInputException("Please select a date after current date");
        }
        for (OpenHours openHrs : existingOpenHours) {
            if (openHour.getFromTime().equals(openHrs.getFromTime())) {
                throw new InvalidInputException("Can't create a new open Hour due to existing open hour with id: " + openHrs.getId() + " on day " + openHrs.getToTime());
            }
        }
        Optional<OpenHours> openHr = openHoursRepository.findById(id);
        if(openHr.isEmpty()){
            throw new ResourceNotFoundException("No such id:" + id +" exist");
        }
        openHr.ifPresent(openHours -> {
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
            openHours.setDayOfWeek(simpleDateformat.format(openHour.getFromTime()));
            openHours.setFromTime(openHour.getFromTime());
            openHours.setToTime(openHour.getToTime());
            openHoursRepository.save(openHours);
        });

    }

    @Override
    public void deleteAllDays() {
        openHoursRepository.deleteAll();
    }

    @Override
    public void deleteADay(String id) {
        try{
            openHoursRepository.deleteById(id);
        } catch (Exception e){
            throw  new ResourceNotFoundException("id: "+id+ " not found");
        }
    }
}
