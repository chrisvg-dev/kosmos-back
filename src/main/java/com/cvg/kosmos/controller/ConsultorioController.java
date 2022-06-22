package com.cvg.kosmos.controller;

import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultorio")
@CrossOrigin(origins = "*")
public class ConsultorioController {
    private final ConsultorioService consultorioService;
    public ConsultorioController(ConsultorioService consultorioService) {
        this.consultorioService = consultorioService;
    }
    @GetMapping
    public List<Consultorio> listar() {
        return this.consultorioService.findAll();
    }
}
