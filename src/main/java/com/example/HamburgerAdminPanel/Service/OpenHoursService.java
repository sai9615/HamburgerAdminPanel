package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.OpenHours;

import java.util.List;

public interface OpenHoursService {
    OpenHours findByDayOfWeek(String dayOfWeek);
    OpenHours findById(String id);
    List<OpenHours> getAllDays();
    void postNewDays(List<OpenHours> openHours);
    void updateDay(String id, OpenHours openHours);
    void deleteAllDays();
    void deleteADay(String id);
}
