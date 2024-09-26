package com.tcs.Banistmo.models.services.impl;

import com.tcs.advices.NotFoundException;
import com.tcs.Banistmo.models.entities.Empleado;
import com.tcs.Banistmo.models.repositories.EmpleadoRepository;
import com.tcs.Banistmo.models.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> list() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado create(Empleado empleado) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findByNumeroEmpleado(empleado.getNumeroEmpleado());
        if (empleadoExistente.isPresent()) {
            throw new IllegalArgumentException("El número de empleado ya existe");
        }
        Optional<Empleado> empleadoExistenteByCedula = empleadoRepository.findByCedulaEmpleado(empleado.getCedulaEmpleado());
        if (empleadoExistenteByCedula.isPresent()) {
            throw new IllegalArgumentException("La cédula de empleado ya está registrada");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado update(Long id, Empleado empleado) {
        return empleadoRepository.findById(id)
                .map(empleadoEditado -> {
//                    Optional<Empleado> empleadoExistente = empleadoRepository.findByNumeroEmpleado(empleado.getNumeroEmpleado());
//                    if (empleadoExistente.isPresent()) {
//                        throw new IllegalArgumentException("El número de empleado ya existe");
//                    }
                    empleadoEditado.setNumeroEmpleado(empleado.getNumeroEmpleado());
                    empleadoEditado.setCedulaEmpleado(empleado.getCedulaEmpleado());
                    empleadoEditado.setNombreEmpleado(empleado.getNombreEmpleado());
                    empleadoEditado.setLiderTcs(empleado.getLiderTcs());
                    empleadoEditado.setEstadoEmpleado(empleado.getEstadoEmpleado());
                    empleadoEditado.setPerfil(empleado.getPerfil());
                    empleadoEditado.setWon(empleado.getWon());
                    return empleadoRepository.save(empleadoEditado);
                })
                .orElseThrow(() -> new NotFoundException("El empleado con el id: " + id + " no existe"));
    }

    @Override
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }
}
