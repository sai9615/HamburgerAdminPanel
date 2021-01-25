package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Interceptor;

public interface InterceptorService {

    Interceptor getInterceptionById(long id);
    Iterable<Interceptor> getAllInterceptions(int page, int size);
    Iterable<Interceptor> findByApiName(String apiName, int page, int size);
    void saveInterception(Interceptor interceptor);
    void deleteInterceptionById(long id);
    void deleteByApiName(String apiName);
    void deleteAll();

}
