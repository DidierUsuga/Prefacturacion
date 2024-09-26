package com.tcs.Banistmo.models.services;

import com.tcs.Banistmo.models.entities.Empleado;

import java.util.List;

public interface EmpleadoService {
    List<Empleado> list();

    Empleado create(Empleado empleado);

    Empleado update(Long id, Empleado empleado);

    void delete(Long id);
}
