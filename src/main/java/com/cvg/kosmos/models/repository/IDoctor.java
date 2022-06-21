package com.cvg.kosmos.models.repository;

import com.cvg.kosmos.models.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctor extends JpaRepository<Doctor, Long> {
}
