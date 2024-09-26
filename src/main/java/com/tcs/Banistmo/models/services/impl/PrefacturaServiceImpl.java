package com.tcs.Banistmo.models.services.impl;

import com.tcs.Banistmo.models.dtos.PrefacturaDTO;
import com.tcs.Banistmo.models.entities.Empleado;
import com.tcs.Banistmo.models.entities.NovedadBanistmo;
import com.tcs.Banistmo.models.entities.Perfil;
import com.tcs.Banistmo.models.repositories.EmpleadoRepository;
import com.tcs.Banistmo.models.repositories.NovedadBanistmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrefacturaServiceImpl {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private NovedadBanistmoRepository novedadBanistmoRepository;

    public List<PrefacturaDTO> generarPrefacturacion(int mes, int anio) {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream()
                .map(empleado -> crearPrefacturacionDTO(empleado, mes, anio))
                .collect(Collectors.toList());
    }

    private PrefacturaDTO crearPrefacturacionDTO(Empleado empleado, int mes, int anio) {
        List<NovedadBanistmo> novedades = novedadBanistmoRepository.findByEmpleadoId(empleado.getId())
                .stream()
                .filter(novedad -> isNovedadInMonth(novedad, mes, anio))
                .collect(Collectors.toList());

        double tarifa = calcularTarifaMes(empleado);
        double totalDiasMes = calcularDiasDeCosto(novedades);
        double totalValor = (tarifa / 30) * totalDiasMes;
        String tipoNovedad = novedades.isEmpty() ? "0" : novedades.stream()
                .map(NovedadBanistmo::getTipoNovedad)
                .distinct()
                .collect(Collectors.joining(", "));
        String comentarioNovedad = novedades.isEmpty() ? "0" : novedades.stream()
                .map(NovedadBanistmo::getComentarioNovedad)
                .collect(Collectors.joining(", "));

        return new PrefacturaDTO(
                empleado.getWon().getNumeroWon(),
                empleado.getNumeroEmpleado(),
                empleado.getNombreEmpleado(),
                tarifa,
                totalDiasMes,
                tipoNovedad,
                comentarioNovedad,
                totalValor
        );
    }

    private boolean isNovedadInMonth(NovedadBanistmo novedad, int mes, int anio) {
        LocalDate fechaInicio = novedad.getFechaInicio().toLocalDate();
        LocalDate fechaFin = novedad.getFechaFin().toLocalDate();
        YearMonth yearMonth = YearMonth.of(anio, mes);
        return (fechaInicio.isBefore(yearMonth.atEndOfMonth()) && fechaFin.isAfter(yearMonth.atDay(1)));
    }

    private double calcularTarifaMes(Empleado empleado) {
        Perfil perfilEmpleado = empleado.getPerfil();
        return Double.valueOf(perfilEmpleado.getSalario());
    }

    private double calcularDiasDeCosto(List<NovedadBanistmo> novedades) {
        double diasNoFacturables = calcularDiasNoFacturables(novedades);
        return 30.0 - diasNoFacturables;
    }

    private double calcularDiasNoFacturables(List<NovedadBanistmo> novedades) {
        return novedades.stream()
                .mapToDouble(NovedadBanistmo::getCantidadDias)
                .sum();
    }
}
