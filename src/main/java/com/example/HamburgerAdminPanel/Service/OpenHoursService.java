package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.OpenHours;

import java.util.List;

public interface OpenHoursService {
    List<OpenHours> findByDayOfWeek(String dayOfWeek, int page, int size);
    OpenHours findById(String id);
    List<OpenHours> getAllDays(int page, int size);
    void postNewDays(List<OpenHours> openHours);
    void updateDay(String id, OpenHours openHours);
    void deleteAllDays();
    void deleteADay(String id);
}
