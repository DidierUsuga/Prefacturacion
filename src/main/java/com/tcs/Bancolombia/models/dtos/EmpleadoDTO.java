package com.tcs.Bancolombia.models.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private Long numEmpleado;
    private int cedula;
    private boolean estado;
    private String nombre;
    private PerfilDTO perfil;
    private ProjectManagerDTO projectManager;
    private WonDTO won;
}
