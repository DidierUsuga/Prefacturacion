package com.tcs.Banistmo.models.services.impl;

import com.tcs.Banistmo.models.entities.Empleado;
import com.tcs.Banistmo.models.entities.NovedadBanistmo;
import com.tcs.Banistmo.models.repositories.EmpleadoRepository;
import com.tcs.Banistmo.models.repositories.NovedadBanistmoRepository;
import com.tcs.Banistmo.models.services.NovedadBanistmoService;
import com.tcs.advices.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NovedadBanistmoServiceImpl implements NovedadBanistmoService {
    @Autowired
    private NovedadBanistmoRepository novedadBanistmoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<NovedadBanistmo> list() {
        return novedadBanistmoRepository.findAll();
    }

    @Override
    public NovedadBanistmo create(NovedadBanistmo novedadBanistmo) {
        Empleado empleado = empleadoRepository.findById(novedadBanistmo.getEmpleado().getId())
                .orElseThrow(() -> new NotFoundException("El empleado con el id: " + novedadBanistmo.getEmpleado().getId() + " no existe"));
        novedadBanistmo.setEmpleado(empleado);

        if (novedadBanistmo.getFechaFin().isBefore(novedadBanistmo.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser menor que la fecha de inicio");
        }

        // Dividir la novedad si las fechas están en meses diferentes
        if (novedadBanistmo.getFechaInicio().getMonth() != novedadBanistmo.getFechaFin().getMonth()) {
            // Primera novedad (hasta el final del mes de inicio)
            LocalDateTime finPrimerMes = novedadBanistmo.getFechaInicio().withDayOfMonth(novedadBanistmo.getFechaInicio().toLocalDate().lengthOfMonth())
                    .withHour(23).withMinute(59).withSecond(59);

            NovedadBanistmo novedad1 = new NovedadBanistmo();
            novedad1.setTipoNovedad(novedadBanistmo.getTipoNovedad());
            novedad1.setFechaInicio(novedadBanistmo.getFechaInicio());
            novedad1.setFechaFin(finPrimerMes);
            novedad1.setComentarioNovedad(novedadBanistmo.getComentarioNovedad());
            novedad1.setEmpleado(empleado);
            novedad1.calcularDiasYHoras();
            novedadBanistmoRepository.save(novedad1);

            // Segunda novedad (desde el inicio del mes siguiente)
            LocalDateTime inicioSegundoMes = novedadBanistmo.getFechaFin().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

            NovedadBanistmo novedad2 = new NovedadBanistmo();
            novedad2.setTipoNovedad(novedadBanistmo.getTipoNovedad());
            novedad2.setFechaInicio(inicioSegundoMes);
            novedad2.setFechaFin(novedadBanistmo.getFechaFin());
            novedad2.setComentarioNovedad(novedadBanistmo.getComentarioNovedad());
            novedad2.setEmpleado(empleado);
            novedad2.calcularDiasYHoras();
            return novedadBanistmoRepository.save(novedad2);
        } else {
            novedadBanistmo.calcularDiasYHoras();
            return novedadBanistmoRepository.save(novedadBanistmo);
        }
    }

    @Override
    public NovedadBanistmo update(Long id, NovedadBanistmo novedadBanistmo) {
        return novedadBanistmoRepository.findById(id)
                .map(novedadEditada -> {
                    Empleado empleado = empleadoRepository.findById(novedadBanistmo.getEmpleado().getId())
                            .orElseThrow(() -> new NotFoundException("El empleado con el id: " + novedadBanistmo.getEmpleado().getId() + " no existe"));
                    novedadEditada.setEmpleado(empleado);
                    if (novedadBanistmo.getFechaFin().isBefore(novedadBanistmo.getFechaInicio())) {
                        throw new IllegalArgumentException("La fecha de fin no puede ser menor que la fecha de inicio");
                    }
                    novedadEditada.setTipoNovedad(novedadBanistmo.getTipoNovedad());
                    novedadEditada.setFechaInicio(novedadBanistmo.getFechaInicio());
                    novedadEditada.setFechaFin(novedadBanistmo.getFechaFin());
                    novedadEditada.setComentarioNovedad(novedadBanistmo.getComentarioNovedad());

                    return novedadBanistmoRepository.save(novedadEditada);
                })
                .orElseThrow(() -> new NotFoundException("La novedad con el id: " + id + " no existe"));
    }

    @Override
    public void delete(Long id) {
        novedadBanistmoRepository.deleteById(id);
    }

    @Override
    public List<NovedadBanistmo> filtrate(int mes, int anio) {
        return novedadBanistmoRepository.findByMesAndAnio(mes, anio);
    }

    @Override
    public void deleteAll() {
        novedadBanistmoRepository.deleteAll();
    }
}