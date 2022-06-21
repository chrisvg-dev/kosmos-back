package com.cvg.kosmos.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime horarioConsulta;
    private String nombrePaciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_doctor", referencedColumnName = "id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_consultorio", referencedColumnName = "id")
    private Consultorio consultorio;
}
