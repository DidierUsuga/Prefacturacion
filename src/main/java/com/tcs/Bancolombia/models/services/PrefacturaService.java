package com.tcs.Bancolombia.models.services;

import com.tcs.Bancolombia.models.dtos.EmpleadoDTO;
import com.tcs.Bancolombia.models.dtos.PrefacturaDTO;
import com.tcs.Bancolombia.models.entities.Novedad;
import com.tcs.Bancolombia.models.repositories.NovedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrefacturaService {

    @Autowired
    private NovedadRepository novedadRepository;

    @Autowired
    private EmpleadoService empleadoService;

    public List<PrefacturaDTO> generate(int mes, int anio) {
        List<EmpleadoDTO> empleados = empleadoService.fetchData();
        return empleados.stream()
                .map(empleado -> createPrefacturaDTO(empleado, mes, anio))
                .collect(Collectors.toList());
    }

    private PrefacturaDTO createPrefacturaDTO(EmpleadoDTO empleado, int mes, int anio) {
        List<Novedad> novedades = novedadRepository.findByNumEmpleado(empleado.getNumEmpleado())
                .stream()
                .filter(novedad -> isNovedadInMonth(novedad, mes, anio))
                .collect(Collectors.toList());

        double totalHorasDisponibles = 180.0;
        Double tarifaMes = calcularTarifaMes(empleado);
        Double tarifaHora = calcularTarifaPorHora(tarifaMes);
        Double horasNoFacturables = calcularHorasNoFacturables(novedades);
        Double valorAFacturar = calcularValorAFacturar(tarifaMes, totalHorasDisponibles, horasNoFacturables);
        Double afectacionPorNovedades = calcularAfectacionPorNovedades(tarifaHora, novedades);
        Double horasFacturables = calcularHorasFacturables(totalHorasDisponibles, horasNoFacturables);
        Double diasNoFacturables = calcularDiasNoFacturables(novedades);
        String tipoNovedad = novedades.isEmpty() ? "0" : novedades.stream()
                .map(Novedad::getTipoNovedad)
                .distinct()
                .collect(Collectors.joining(", "));
        String comentarioNovedad = novedades.isEmpty() ? "0" : novedades.stream()
                .map(Novedad::getComentarioNovedad)
                .collect(Collectors.joining(", "));
        Double diasDeCosto = calcularDiasDeCosto(novedades);
        Double diasHabiles = Double.valueOf(calcularDiasHabiles(novedades));

        return new PrefacturaDTO(
                empleado.getNumEmpleado(),
                String.valueOf(empleado.getCedula()),
                empleado.getWon().getNumWon(),
                empleado.getNombre(),
                empleado.getProjectManager().getNombrePm(),
                empleado.getPerfil().getNombrePerfil(),
                tipoNovedad,
                comentarioNovedad,
                tarifaMes,
                tarifaHora,
                valorAFacturar,
                afectacionPorNovedades,
                horasFacturables,
                horasNoFacturables,
                diasHabiles,
                diasNoFacturables,
                diasDeCosto
        );
    }

    private boolean isNovedadInMonth(Novedad novedad, int mes, int anio) {
        LocalDate fechaInicio = novedad.getFechaInicio().toLocalDate();
        LocalDate fechaFin = novedad.getFechaFin().toLocalDate();
        YearMonth yearMonth = YearMonth.of(anio, mes);
        return (fechaInicio.isBefore(yearMonth.atEndOfMonth()) && fechaFin.isAfter(yearMonth.atDay(1)));
    }

    private Double calcularTarifaMes(EmpleadoDTO empleado) {
        return empleado.getPerfil().getSalario();
    }

    private Double calcularTarifaPorHora(Double tarifaMes) {
        return tarifaMes / 180.0;
    }

    private Double calcularValorAFacturar(Double tarifaMes, double totalHorasDisponibles, double horasNoFacturables) {
        Double tarifaPorHora = calcularTarifaPorHora(tarifaMes);
        Double horasFacturables = calcularHorasFacturables(totalHorasDisponibles, horasNoFacturables);
        return tarifaPorHora * horasFacturables;
    }

    private Double calcularAfectacionPorNovedades(Double tarifaHora, List<Novedad> novedades) {
        Double horasNoFacturables = calcularHorasNoFacturables(novedades);
        return tarifaHora * horasNoFacturables;
    }

    private Double calcularHorasNoFacturables(List<Novedad> novedades) {
        return novedades.stream()
                .mapToDouble(novedad -> novedad.getCantidadDias() * 9)
                .sum();
    }

    private Double calcularHorasFacturables(double totalHorasDisponibles, double horasNoFacturables) {
        double horasFacturables = totalHorasDisponibles - horasNoFacturables;
        return Math.max(horasFacturables, 0.0);
    }

    private Double calcularDiasNoFacturables(List<Novedad> novedades) {
        return novedades.stream()
                .mapToDouble(Novedad::getCantidadDias)
                .sum();
    }

    private Double calcularDiasDeCosto(List<Novedad> novedades) {
        Double diasNoFacturables = calcularDiasNoFacturables(novedades);
        return 30.0 - diasNoFacturables;
    }

    private Integer calcularDiasHabiles(List<Novedad> novedades) {
        return novedades.stream()
                .mapToInt(novedad -> novedad.getDiasHabiles().intValue())
                .sum();
    }
}
