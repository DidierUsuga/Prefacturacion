package com.tcs.Banistmo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "novedad_banistmo")
public class NovedadBanistmo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_novedad", nullable = false)
    private String tipoNovedad;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "cantidad_dias", nullable = false)
    private Double cantidadDias;

    @Column(name = "cantidad_horas", nullable = false)
    private Double cantidadHoras;

    @Column(name = "comentario_novedad")
    private String comentarioNovedad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado")
    @JsonIgnore
    private Empleado empleado;

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        calcularDiasYHoras();
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        calcularDiasYHoras();
    }

    public void calcularDiasYHoras() {
        if (this.fechaInicio != null && this.fechaFin != null) {
            Duration duration = Duration.between(this.fechaInicio, this.fechaFin);
            long totalMinutes = duration.toMinutes();

            // Calcular la cantidad de días
            BigDecimal dias = BigDecimal.valueOf(totalMinutes / (60.0 * 24.0));
            this.cantidadDias = dias.setScale(2, RoundingMode.HALF_UP).doubleValue();

            // Si queremos 9 horas por día, multiplicamos la cantidad de días por 9
            this.cantidadHoras = this.cantidadDias * 9.0;
        }
    }

    // Método para obtener el rol del empleado
    public Perfil getRolEmpleado() {
        return empleado != null ? empleado.getPerfil() : null;
    }

    // Método para obtener el won del empleado
    public Won getwonEmpleado() {
        return empleado != null ? empleado.getWon() : null;
    }
}
