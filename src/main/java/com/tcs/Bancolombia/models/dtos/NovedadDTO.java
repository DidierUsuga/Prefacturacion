package com.tcs.Bancolombia.models.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovedadDTO {
    private Long id;
    private String tipoNovedad;
    private String comentarioNovedad;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double cantidadDias;
    private Double cantidadHoras;
    private Double diasHabiles;
    private EmpleadoDTO empleadoDTO;
}
