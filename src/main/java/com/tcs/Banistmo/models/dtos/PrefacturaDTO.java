package com.tcs.Banistmo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrefacturaDTO {
    private Long numeroWon;
    private Long numeroEmpleado;
    private String nombreEmpleado;
    private Double tarifa;
    private Double totalDiasMes;
    private String novedad;
    private String justificacion;
    private Double totalValor;
}
