package com.tcs.Banistmo.models.dtos;

import com.tcs.Banistmo.models.entities.Perfil;
import com.tcs.Banistmo.models.entities.Won;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id;

    private Long numeroEmpleado;

    private String cedulaEmpleado;

    private String nombreEmpleado;

    private String liderTcs;

    private Boolean estadoEmpleado;

    private Perfil perfil;

    private Won won;
}
