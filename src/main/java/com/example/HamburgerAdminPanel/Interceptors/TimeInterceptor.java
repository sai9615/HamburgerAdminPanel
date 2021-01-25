package com.example.HamburgerAdminPanel.Interceptors;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import com.example.HamburgerAdminPanel.Service.InterceptorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Autowired
    InterceptorServiceImpl interceptorService;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception arg3)
            throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Total Time Taken: {} milliseconds", (endTime - startTime));
        Date currentDate = new Date();
        ApiType obj = new ApiType();
        if(response.getStatus() == 200){
            String url = request.getRequestURL().toString();
            Interceptor interceptor = new Interceptor();
            interceptor.setDate(currentDate);
            interceptor.setUrl(url);
            interceptor.setExecutionTime(endTime - startTime);
            interceptorService.saveInterception(obj.setApiName(url, interceptor));
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }
}
