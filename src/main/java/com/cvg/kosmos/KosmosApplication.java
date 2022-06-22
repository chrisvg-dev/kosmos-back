package com.cvg.kosmos;

import com.cvg.kosmos.models.entity.Consultorio;
import com.cvg.kosmos.models.entity.Doctor;
import com.cvg.kosmos.models.repository.IConsultorio;
import com.cvg.kosmos.models.repository.IDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class KosmosApplication implements CommandLineRunner  {
	private final IDoctor doctores;
	private final IConsultorio consultorios;

	public KosmosApplication(IDoctor doctores, IConsultorio consultorios) {
		this.doctores = doctores;
		this.consultorios = consultorios;
	}
	public static void main(String[] args) {
		SpringApplication.run(KosmosApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		List<Doctor> doctores = Arrays.asList(
				new Doctor(0L, "CRISTHIAN", "VILLEGAS", "GARCIA", "MED INTERNA"),
				new Doctor(0L, "ANGELA", "VILLEGAS", "GARCIA", "TRAUMATOLOGA"),
				new Doctor(0L, "ESMERALDA", "VILLEGAS", "GARCIA", "CARDIOLOGO"),
				new Doctor(0L, "ANAHY", "VILLEGAS", "GARCIA", "ODONTOLOGA"),
				new Doctor(0L, "RICARDO", "VILLEGAS", "GARCIA", "MEDICO GENERAL")
		);
		this.doctores.saveAll(doctores);
		List<Consultorio> consultorios = Arrays.asList(
				new Consultorio(0L, 1, "PRIMER PISO"),
				new Consultorio(0L,2, "PRIMER PISO"),
				new Consultorio(0L,3, "SEGUNDO PISO"),
				new Consultorio(0L,4, "TERCER PISO"),
				new Consultorio(0L,5, "CUARTO PISO")
		);
		this.consultorios.saveAll(consultorios);
	}
}
