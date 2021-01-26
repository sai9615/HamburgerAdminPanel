package com.example.HamburgerAdminPanel.Service;

import com.example.HamburgerAdminPanel.Entity.Interceptor;
import com.example.HamburgerAdminPanel.Exception.ResourceNotFoundException;
import com.example.HamburgerAdminPanel.Repository.InterceptorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InterceptorServiceImpl implements InterceptorService{
    @Autowired
    InterceptorRepository interceptorRepository;

    @Override
    public Interceptor getInterceptionById(long id) {
        Optional<Interceptor> interceptor = interceptorRepository.findByInterceptionId(id);
        if(interceptor.isEmpty()){
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
        Optional<List<Interceptor>> interceptor = interceptorRepository.findByApiName(apiName, paging);
        if(interceptor.isEmpty()){
            throw new ResourceNotFoundException("Interception with api name: "+apiName+" doesn't exist in database");
        }
        return interceptor.get();
    }

    @Override
    public List<Interceptor> findAllByGivenDate(Date date, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Optional<List<Interceptor>> interceptor = interceptorRepository.findAllByDate(date, paging);
        if(interceptor.isEmpty()){
            throw new ResourceNotFoundException("Interception on date: "+date.toString()+" doesn't exist in database");
        }
        return interceptor.get();
    }

    @Override
    public void saveInterception(Interceptor interceptor) {
            interceptorRepository.save(interceptor);
    }

    @Override
    public void deleteInterceptionById(long id) {
        try {
            interceptorRepository.deleteByInterceptionId(id);
        } catch (Exception e){
            throw new ResourceNotFoundException("Id: "+id+" not found");
        }
    }

    @Override
    public void deleteByApiName(String apiName) {
        try {
            interceptorRepository.deleteByApiName(apiName);
        } catch (Exception e){
            throw new ResourceNotFoundException("API: "+apiName+" not found");
        }
    }

    @Override
    public void deleteAll() {
        interceptorRepository.deleteAll();
    }

}
