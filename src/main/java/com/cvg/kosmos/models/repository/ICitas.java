package com.cvg.kosmos.models.repository;

import com.cvg.kosmos.models.entity.Cita;
import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.models.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICitas extends JpaRepository<Cita, Long> {
    List<Cita> findByDoctor(Doctor doctor);
    List<Cita> findByConsultorio(Consultorio doctor);

    boolean existsByHorarioConsultaAndConsultorio(LocalDateTime horario, Consultorio consultorio);
    boolean existsByHorarioConsultaAndDoctor(LocalDateTime horario, Doctor doctor);

    @Query(value = "SELECT count(*) FROM citas c WHERE c.fk_doctor = :doctor AND DATE(c.horario_consulta) = :date", nativeQuery = true)
    Integer totalCitasDelDia(@Param("date") LocalDate dia, @Param("doctor") Long doctor);


    @Query(value = "SELECT * FROM citas c WHERE DATE(c.horario_consulta) = :date", nativeQuery = true)
    List<Cita> buscarPorFecha(@Param("date") LocalDate dia);
    @Query(value = "SELECT * FROM citas c WHERE c.fk_doctor = :doctor AND DATE(c.horario_consulta) = :date", nativeQuery = true)
    List<Cita> buscarPorFechaAndDoctor(@Param("date") LocalDate dia, @Param("doctor") Long doctorId);



    @Query(value = "SELECT * FROM citas c WHERE c.nombre_paciente = :paciente AND c.horario_consulta BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
    List<Cita> buscarCitasRangoFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin, @Param("paciente") String paciente);
}
