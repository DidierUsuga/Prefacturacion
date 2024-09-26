package com.tcs.Bancolombia.models.services;

import com.tcs.Bancolombia.models.dtos.EmpleadoDTO;
import com.tcs.Bancolombia.models.dtos.NovedadDTO;
import com.tcs.Bancolombia.models.entities.Novedad;
import com.tcs.Bancolombia.models.repositories.NovedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NovedadService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NovedadRepository novedadRepository;

    @Value("${endpoint.findByNumEmployee}")
    private String endpoint;

    public List<Novedad> list() {
        List<Novedad> novedades = novedadRepository.findAll();
        novedades.forEach(novedad -> {
            if (novedad.getNumEmpleado() != null) {
                EmpleadoDTO empleadoDTO = getEmployees(novedad.getNumEmpleado());
                novedad.setEmpleadoDTO(empleadoDTO);
            }
        });
        return novedades;
    }

    public Novedad create(NovedadDTO novedadDTO) {
        if (novedadDTO.getFechaFin().isBefore(novedadDTO.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser menor que la fecha de inicio");
        }

        // Obtener información del empleado
        EmpleadoDTO empleadoDTO = getEmployees(novedadDTO.getEmpleadoDTO().getNumEmpleado());
        if (empleadoDTO == null || !empleadoDTO.isEstado()) {
            throw new IllegalArgumentException("Empleado no encontrado o inactivo");
        }

        // Dividir la novedad si las fechas están en meses diferentes
        if (novedadDTO.getFechaInicio().getMonth() != novedadDTO.getFechaFin().getMonth()) {
            LocalDateTime finPrimerMes = novedadDTO.getFechaInicio()
                    .withDayOfMonth(novedadDTO.getFechaInicio().toLocalDate().lengthOfMonth())
                    .withHour(23).withMinute(59).withSecond(59);

            Novedad novedad1 = new Novedad();
            novedad1.setTipoNovedad(novedadDTO.getTipoNovedad());
            novedad1.setComentarioNovedad(novedadDTO.getComentarioNovedad());
            novedad1.setFechaInicio(novedadDTO.getFechaInicio());
            novedad1.setFechaFin(finPrimerMes);
            novedad1.setEmpleadoDTO(empleadoDTO);
            novedad1.setNumEmpleado(empleadoDTO.getNumEmpleado());
            novedad1.calculateDaysAndHours();
            novedadRepository.save(novedad1);

            LocalDateTime inicioSegundoMes = novedadDTO.getFechaFin()
                    .withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

            Novedad novedad2 = new Novedad();
            novedad2.setTipoNovedad(novedadDTO.getTipoNovedad());
            novedad2.setComentarioNovedad(novedadDTO.getComentarioNovedad());
            novedad2.setFechaInicio(inicioSegundoMes);
            novedad2.setFechaFin(novedadDTO.getFechaFin());
            novedad2.setEmpleadoDTO(empleadoDTO);
            novedad2.setNumEmpleado(empleadoDTO.getNumEmpleado());
            novedad2.calculateDaysAndHours();
            return novedadRepository.save(novedad2);
        } else {
            Novedad novedad = new Novedad();
            novedad.setTipoNovedad(novedadDTO.getTipoNovedad());
            novedad.setComentarioNovedad(novedadDTO.getComentarioNovedad());
            novedad.setFechaInicio(novedadDTO.getFechaInicio());
            novedad.setFechaFin(novedadDTO.getFechaFin());
            novedad.setEmpleadoDTO(empleadoDTO);
            novedad.setNumEmpleado(empleadoDTO.getNumEmpleado());
            novedad.calculateDaysAndHours();
            try {
                return novedadRepository.save(novedad);
            } catch (DataAccessException ex) {
                throw new IllegalArgumentException("Error al guardar la novedad");
            }
        }
    }

    public Novedad update(Long id, NovedadDTO novedadDTO) {
        Optional<Novedad> novedadOptional = novedadRepository.findById(id);
        if (novedadOptional.isPresent()) {
            Novedad novedadExistente = novedadOptional.get();

            if (novedadDTO.getFechaFin().isBefore(novedadDTO.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha de fin no puede ser menor que la fecha de inicio");
            }

            // Obtener información del empleado
            EmpleadoDTO empleadoDTO = getEmployees(novedadDTO.getEmpleadoDTO().getNumEmpleado());
            if (empleadoDTO == null || !empleadoDTO.isEstado()) {
                throw new IllegalArgumentException("Empleado no encontrado o inactivo");
            }

            // Dividir la novedad si las fechas están en meses diferentes
            if (novedadDTO.getFechaInicio().getMonth() != novedadDTO.getFechaFin().getMonth()) {
                LocalDateTime finPrimerMes = novedadDTO.getFechaInicio()
                        .withDayOfMonth(novedadDTO.getFechaInicio().toLocalDate().lengthOfMonth())
                        .withHour(23).withMinute(59).withSecond(59);

                Novedad novedad1 = new Novedad();
                novedad1.setTipoNovedad(novedadDTO.getTipoNovedad());
                novedad1.setComentarioNovedad(novedadDTO.getComentarioNovedad());
                novedad1.setFechaInicio(novedadDTO.getFechaInicio());
                novedad1.setFechaFin(finPrimerMes);
                novedad1.setEmpleadoDTO(empleadoDTO);
                novedad1.setNumEmpleado(empleadoDTO.getNumEmpleado());
                novedad1.calculateDaysAndHours();
                novedadRepository.save(novedad1);

                LocalDateTime inicioSegundoMes = novedadDTO.getFechaFin()
                        .withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

                Novedad novedad2 = new Novedad();
                novedad2.setTipoNovedad(novedadDTO.getTipoNovedad());
                novedad2.setComentarioNovedad(novedadDTO.getComentarioNovedad());
                novedad2.setFechaInicio(inicioSegundoMes);
                novedad2.setFechaFin(novedadDTO.getFechaFin());
                novedad2.setEmpleadoDTO(empleadoDTO);
                novedad2.setNumEmpleado(empleadoDTO.getNumEmpleado());
                novedad2.calculateDaysAndHours();
                return novedadRepository.save(novedad2);
            } else {
                novedadExistente.setTipoNovedad(novedadDTO.getTipoNovedad());
                novedadExistente.setComentarioNovedad(novedadDTO.getComentarioNovedad());
                novedadExistente.setFechaInicio(novedadDTO.getFechaInicio());
                novedadExistente.setFechaFin(novedadDTO.getFechaFin());
                novedadExistente.setEmpleadoDTO(empleadoDTO);
                novedadExistente.setNumEmpleado(empleadoDTO.getNumEmpleado());
                novedadExistente.calculateDaysAndHours();
                try {
                    return novedadRepository.save(novedadExistente);
                } catch (DataAccessException ex) {
                    throw new IllegalArgumentException("Error al actualizar la novedad");
                }
            }
        } else {
            throw new IllegalArgumentException("Novedad no encontrada");
        }
    }

    public List<Novedad> filtrate(int mes, int anio) {
        List<Novedad> novedades = novedadRepository.findByMesAndAnio(mes, anio);
        if (novedades.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron novedades para el mes y año especificado.");
        }
        novedades.forEach(novedad -> {
            if (novedad.getNumEmpleado() != null) {
                EmpleadoDTO empleadoDTO = getEmployees(novedad.getNumEmpleado());
                novedad.setEmpleadoDTO(empleadoDTO);
            }
        });
        return novedades;
    }


    public void delete(Long id) {
        if (novedadRepository.existsById(id)) {
            novedadRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Novedad no encontrada con ID: " + id);
        }
    }

    public void deleteAll() {
        novedadRepository.deleteAll();
    }

    public EmpleadoDTO getEmployees(Long id) {
        try {
            return restTemplate.getForObject(endpoint + id, EmpleadoDTO.class);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}
