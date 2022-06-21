package com.cvg.kosmos.service;

import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.models.entity.Doctor;
import com.cvg.kosmos.models.repository.IConsultorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultorioService {

    private final IConsultorio repository;

    public ConsultorioService(IConsultorio repository) {
        this.repository = repository;
    }
    public Optional<Consultorio> findById(Long id){
        return this.repository.findById(id);
    }
    public List<Consultorio> findAll(){
        return this.repository.findAll();
    }
}
