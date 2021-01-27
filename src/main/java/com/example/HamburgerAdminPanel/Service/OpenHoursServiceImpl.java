package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.OpenHours;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.OpenHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OpenHoursServiceImpl implements OpenHoursService{

    @Autowired
    OpenHoursRepository openHoursRepository;

    @Override
    public List<OpenHours> findByDayOfWeek(String dayOfWeek, int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        Optional<List<OpenHours>> openHours = openHoursRepository.findByDayOfWeekIgnoreCase(dayOfWeek, paging);
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
    public List<OpenHours> getAllDays(int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        Page<OpenHours> openHours = openHoursRepository.findAll(paging);
        return openHours.getContent();
    }

    @Override
    public OpenHours findByDate(String date) {
        Optional<OpenHours> openHours;
        try {
            openHours = openHoursRepository.findByDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
        } catch (ParseException e) {
            throw new InvalidInputException("Invalid date");
        }
        if(openHours.isEmpty()){
            throw new ResourceNotFoundException("No open hours on date: "+date+ " exist");
        }
        return openHours.get();
    }

    @Override
    public void postNewDays(List<OpenHours> openHours) {
        List<OpenHours> existingOpenHours = openHoursRepository.findAll();
        Date currentDate = new Date();
        openHours.forEach(openHour -> {
                if (openHour.getDate().before(currentDate)) {
                    throw new InvalidInputException("Please select a date after current date for id: " +openHour.getId());
                }
                for (OpenHours openHrs : existingOpenHours) {
                    if (openHour.getDate().equals(openHrs.getDate())) {
                        throw new InvalidInputException("Can't create a new open Hour due to existing open hour with id: " + openHrs.getId() + " on day " + openHrs.getToTime());
                    }
                }
                SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
                openHour.setDayOfWeek(simpleDateformat.format(openHour.getDate()));
                openHoursRepository.save(openHour);
                existingOpenHours.add(openHour);
            });
    }

    @Override
    public void updateDay(String id, OpenHours openHour) {
        List<OpenHours> existingOpenHours = openHoursRepository.findAll();
        Date currentDate = new Date();
        if(openHour.getDate().before(currentDate)){
            throw new InvalidInputException("Please select a date after current date");
        }
        for (OpenHours openHrs : existingOpenHours) {
            if (openHour.getDate().equals(openHrs.getDate())) {
                throw new InvalidInputException("Can't create a new open Hour due to existing open hour with id: " + openHrs.getId() + " on day " + openHrs.getToTime());
            }
        }
        Optional<OpenHours> openHr = openHoursRepository.findById(id);
        if(openHr.isEmpty()){
            throw new ResourceNotFoundException("No such id:" + id +" exist");
        }
        openHr.ifPresent(openHours -> {
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
            openHours.setDayOfWeek(simpleDateformat.format(openHour.getDate()));
            openHours.setFromTime(openHour.getFromTime());
            openHours.setToTime(openHour.getToTime());
            openHours.setDate(openHour.getDate());
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
