package com.tcs.Banistmo.models.repositories;

import com.tcs.Banistmo.models.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByNumeroEmpleado(Long numeroEmpleado);
    Optional<Empleado> findByCedulaEmpleado(String cedulaEmpleado);
}
