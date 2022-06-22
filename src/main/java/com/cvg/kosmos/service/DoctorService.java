package com.cvg.kosmos.service;

import com.cvg.kosmos.models.entity.Doctor;
import com.cvg.kosmos.models.repository.IDoctor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final IDoctor repository;
    public DoctorService(IDoctor repository) {
        this.repository = repository;
    }
    public Optional<Doctor> findById(Long id){
        return this.repository.findById(id);
    }
    public List<Doctor> findAll(){
        return this.repository.findAll();
    }
}