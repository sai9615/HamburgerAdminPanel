package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import com.example.HamburgerAdminPanel.Exception.InvalidInputException;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.InterceptorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InterceptorServiceImpl implements InterceptorService{

    @Autowired
    InterceptorRepository interceptorRepository;

    @Override
    public Interceptor getInterceptionById(long id) {
        Optional<Interceptor> interceptor = interceptorRepository.findByInterceptionId(id);
        if(interceptor.isEmpty()){
            log.debug(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" Interception with Id doesn't exist in database");
            throw new ResourceNotFoundException("Interception with Id doesn't exist in database");
        }
        return interceptor.get();
    }

    @Override
    public Iterable<Interceptor> getAllInterceptions(int page, int size) {
        Pageable paging =  PageRequest.of(page,size);
        Page<Interceptor> interceptors = interceptorRepository.findAll(paging);
        return interceptors.getContent();
    }

    @Override
    public Iterable<Interceptor> findByApiName(String apiName, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Optional<List<Interceptor>> interceptor = interceptorRepository.findByApiNameIgnoreCase(apiName, paging);
        if(interceptor.isEmpty()){
            log.debug(this.getClass().getName()+": "+  this.getClass().getEnclosingMethod().getName()+" Interception with api name: "+apiName+" doesn't exist in database");
            throw new ResourceNotFoundException("Interception with api name: "+apiName+" doesn't exist in database");
        }
        return interceptor.get();
    }

    @Override
    public List<Interceptor> findAllByGivenDate(String date, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Optional<List<Interceptor>> interceptor;
        try {
            interceptor = interceptorRepository.findByDate(new SimpleDateFormat("dd-MM-yy").parse(date), paging);
        } catch (ParseException e) {
            log.debug(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" Invalid date provided");
            throw new InvalidInputException("Invalid date");
        }
        if (interceptor.isEmpty()) {
            throw new ResourceNotFoundException("Interception on date: " + date.toString() + " doesn't exist in database");
        }
        return interceptor.get();
    }

    @Override
    public void saveInterception(Interceptor interceptor) {
            interceptorRepository.save(interceptor);
            log.info(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" saved interceptor data to database");
    }

    @Override
    public void deleteInterceptionById(long id) {
        try {
            interceptorRepository.deleteByInterceptionId(id);
        } catch (Exception e){
            log.debug(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" Id: "+id+" not found");
            throw new ResourceNotFoundException("Id: "+id+" not found");
        }
    }

    @Override
    public void deleteByApiName(String apiName) {
        try {
            interceptorRepository.deleteByApiNameIgnoreCase(apiName);
        } catch (Exception e){
            log.debug(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" API: "+apiName+" not found");
            throw new ResourceNotFoundException("API: "+apiName+" not found");
        }
    }

    @Override
    public void deleteAll() {
        interceptorRepository.deleteAll();
        log.debug(this.getClass().getName()+": "+ this.getClass().getEnclosingMethod().getName()+" Deleted all the data");
    }

}
