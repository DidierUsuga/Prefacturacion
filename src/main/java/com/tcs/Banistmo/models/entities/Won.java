package com.tcs.Banistmo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "WonBanistmo")
public class Won implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_won", nullable = false)
    private Long numeroWon;

    @Column(name = "nombre_won", nullable = false)
    private String nombreWon;

    @Column(name = "estado_won", nullable = false)
    private Boolean estadoWon;

    @OneToMany(mappedBy = "won", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Empleado> empleadoList = new ArrayList<>();
}
