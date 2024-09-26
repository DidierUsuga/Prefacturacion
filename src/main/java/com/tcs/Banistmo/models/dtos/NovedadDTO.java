package com.tcs.Banistmo.models.dtos;

import com.tcs.Banistmo.models.entities.Empleado;
import com.tcs.Banistmo.models.entities.Perfil;
import com.tcs.Banistmo.models.entities.Won;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NovedadDTO {

    private Long id;

    private String tipoNovedad;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private Double cantidadDias;

    private String comentarioNovedad;

    private Empleado empleado;

    private Perfil perfil;

    private Won won;
}

