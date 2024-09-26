package com.tcs.Bancolombia.models.entities;

import com.tcs.Bancolombia.models.dtos.EmpleadoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "novedad_bancolombia")
public class Novedad {
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

    @Column(name = "dias_habiles", nullable = false)
    private Double diasHabiles;

    @Column(name = "comentario_novedad")
    private String comentarioNovedad;

    @Transient
    private EmpleadoDTO empleadoDTO;

    @Column(name = "num_empleado")
    private Long numEmpleado;

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        calculateDaysAndHours();
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        calculateDaysAndHours();
    }

    public void calculateDaysAndHours() {
        if (this.fechaInicio != null && this.fechaFin != null) {
            long totalDays = ChronoUnit.DAYS.between(this.fechaInicio.toLocalDate(), this.fechaFin.toLocalDate()) + 1;
            this.cantidadDias = (double) totalDays;
            this.cantidadHoras = this.cantidadDias * 9.0;
            this.diasHabiles = (double) calculateBusinessDays(this.fechaInicio.toLocalDate(), this.fechaFin.toLocalDate());
        }
    }

    private int calculateBusinessDays(LocalDate start, LocalDate end) {
        int businessDays = 0;
        LocalDate current = start;
        while (!current.isAfter(end)) {
            if (isBusinessDay(current)) {
                businessDays++;
            }
            current = current.plusDays(1);
        }
        return businessDays;
    }

    private boolean isBusinessDay(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Lunes a viernes
    }
}
