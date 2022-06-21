package com.cvg.kosmos.service;

import com.cvg.kosmos.models.entity.Cita;
import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.models.entity.Doctor;
import com.cvg.kosmos.models.repository.ICitas;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {
    private final ICitas repository;
    private final DoctorService doctorService;
    private final ConsultorioService consultorioService;

    public CitaService(DoctorService doctorService, ConsultorioService consultorioService, ICitas repository) {
        this.doctorService = doctorService;
        this.consultorioService = consultorioService;
        this.repository = repository;
    }
    public Cita guardarCita(Cita cita){
        return this.repository.save(cita);
    }
    public List<Cita> listarCitas() {
        return this.repository.findAll();
    }

    public boolean existsByHorarioAndConsultorio(LocalDateTime horario, Consultorio consultorio) {
        return this.repository.existsByHorarioConsultaAndConsultorio(horario, consultorio);
    }
    public boolean existsByHorarioAndDoctor(LocalDateTime horario, Doctor doctor) {
        return this.repository.existsByHorarioConsultaAndDoctor(horario, doctor);
    }
    public Integer totalCitasDelDia(LocalDate horario, Long doctor) {
        return this.repository.totalCitasDelDia(horario, doctor);
    }

    public Optional<Cita> buscarPorId(Long id) {
        return this.repository.findById(id);
    }

    public List<Cita> totalCitasDelDiaPorPaciente(LocalDate horario, String paciente) {
        return this.repository.citasPacienteDia(horario, paciente);
    }
    public void cancelarCita(Cita c) {
        Optional<Cita> citaOptional = this.repository.findById(c.getId());
        if ( citaOptional.isPresent() ) {
            Cita cita = citaOptional.get();
            LocalDateTime now = LocalDateTime.now();
            Long minutes = ChronoUnit.MINUTES.between( now, cita.getHorarioConsulta() );
            if ( minutes > 0 ) {
                this.repository.deleteById(cita.getId());
            }
        }
    }
}
