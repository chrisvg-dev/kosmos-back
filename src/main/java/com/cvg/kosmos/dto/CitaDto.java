package com.cvg.kosmos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CitaDto {
    private Long id;
    @NotBlank
    private String horarioConsulta;
    @NotBlank
    private String nombrePaciente;
    @NotNull
    private Long doctorId;
    @NotNull
    private Long consultorioId;
}
