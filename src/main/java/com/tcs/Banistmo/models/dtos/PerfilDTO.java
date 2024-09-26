package com.tcs.Banistmo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    private Long id;

    private String nombrePerfil;

    private Double salario;
}
