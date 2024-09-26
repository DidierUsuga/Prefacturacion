package com.tcs.Banistmo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EmpleadoBanistmo")
public class Empleado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_empleado", nullable = false)
    private Long numeroEmpleado;

    @Column(name = "cedula_empleado", nullable = false)
    private String cedulaEmpleado;

    @Column(name = "nombre_empleado", nullable = false)
    private String nombreEmpleado;

    @Column(name = "lider_tcs", nullable = false)
    private String liderTcs;

    @Column(name = "estado_empleado", nullable = false)
    private Boolean estadoEmpleado;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    @JsonIgnore
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "id_won", nullable = false)
    @JsonIgnore
    private Won won;
}
