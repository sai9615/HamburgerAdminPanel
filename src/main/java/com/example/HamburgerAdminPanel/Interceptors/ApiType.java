package com.example.HamburgerAdminPanel.Interceptors;

import com.example.HamburgerAdminPanel.Entity.Interceptor;

public class ApiType {
    public Interceptor setApiName(String url, Interceptor interceptor) {
        if (url.contains("locations")) {
            interceptor.setApiName("locations");
        } else if (url.contains("menus")) {
            interceptor.setApiName("menus");
        } else if (url.contains("open-hours")) {
            interceptor.setApiName("open-hours");
        } else if (url.contains("reservations")) {
            interceptor.setApiName("reservations");
        } else if (url.contains("interceptors")) {
            interceptor.setApiName("interceptors");
        }
        return interceptor;
    }
}