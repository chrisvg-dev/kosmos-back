package com.cvg.kosmos.controller;

import com.cvg.kosmos.dto.CitaDto;
import com.cvg.kosmos.models.entity.Cita;
import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.models.entity.Doctor;
import com.cvg.kosmos.service.CitaService;
import com.cvg.kosmos.service.ConsultorioService;
import com.cvg.kosmos.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "*")
public class CitaController {
    private static final Logger LOG = LoggerFactory.getLogger( CitaController.class );
    private static final Integer MAX_CITAS = 8;
    private final CitaService citaService;
    private final DoctorService doctorService;
    private final ConsultorioService consultorioService;

    public CitaController(CitaService citaService, DoctorService doctorService, ConsultorioService consultorioService) {
        this.citaService = citaService;
        this.doctorService = doctorService;
        this.consultorioService = consultorioService;
    }

    @GetMapping
    public List<Cita> listarCitas(){
        return this.citaService.listarCitas();
    }
    @GetMapping("/doctor/{doctorId}")
    public List<Cita> listarCitasPorDoctor(@PathVariable Long doctorId){
        return null;
    }
    @GetMapping("/consultorio/{consultorioId}")
    public List<Cita> listarCitasPorConsultorio(@PathVariable Long consultorioId){
        return null;
    }

    @DeleteMapping("/cita/{citaId}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long citaId){
        Optional<Cita> cita = this.citaService.buscarPorId(citaId);
        try {
            if (cita.isPresent()) {
                this.citaService.cancelarCita( cita.get() ) ;
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Registro eliminado"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Elemento no encontrado"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Elemento no encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> guardarCita(@Valid @RequestBody CitaDto citaDto, BindingResult result) {
        LOG.info( citaDto.toString() );
        if (result.hasErrors()) return validate(result);
        Optional<Doctor> optionalDoctor = this.doctorService.findById(citaDto.getDoctorId());
        Optional<Consultorio> optionalConsultorio = this.consultorioService.findById(citaDto.getConsultorioId());

        if (optionalDoctor.isPresent() && optionalConsultorio.isPresent()) {
            Consultorio consultorio = optionalConsultorio.orElseThrow();
            Doctor doctor = optionalDoctor.orElseThrow();

            LocalDateTime fechaCita = this.dateFormatter( citaDto.getHorarioConsulta() );
            LocalDateTime twoAfter = fechaCita.plusHours(2L).minusMinutes(2L);
            List<Cita> citasCercanas = this.citaService.buscarCitasRangoFechas(fechaCita, twoAfter, citaDto.getNombrePaciente());

            if (citasCercanas.size() > 0) {
                return ResponseEntity.badRequest().body(Collections.singletonMap(
                        "message", "Ya tiene una cita agendada, solo puede tener citas 2 horas después de la cita registrada..."));
            }

            if (this.citaService.existsByHorarioAndDoctor( fechaCita, doctor )){
                return ResponseEntity.badRequest().body(Collections.singletonMap(
                        "message", "El doctor no está disponible a esa hora..."));
            }
            if (this.citaService.existsByHorarioAndConsultorio( fechaCita, consultorio )){
                return ResponseEntity.badRequest().body(Collections.singletonMap(
                        "message", "El consultorio no está disponible a esa hora..."));
            }
            if (this.citaService.totalCitasDelDia( fechaCita.toLocalDate(), doctor.getId()) >= MAX_CITAS){
                return ResponseEntity.badRequest().body(Collections.singletonMap(
                        "message", "El doctor ya no tiene espacio en su agenda..."));
            }
            Cita nuevaCita = new Cita();
            nuevaCita.setHorarioConsulta( fechaCita );
            nuevaCita.setNombrePaciente( citaDto.getNombrePaciente() );
            nuevaCita.setDoctor( doctor );
            nuevaCita.setConsultorio( consultorio );
            return ResponseEntity.status(HttpStatus.CREATED).body( this.citaService.guardarCita(nuevaCita) );
        }
        return ResponseEntity.badRequest().body(
                Collections.singletonMap("message", "No se pudo registrar la cita, revisa los datos...")
        );
    }
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        } );
        return ResponseEntity.badRequest().body(errores);
    }
    private LocalDateTime dateFormatter(String date){
        String newDate = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(newDate, formatter);
    }
}
