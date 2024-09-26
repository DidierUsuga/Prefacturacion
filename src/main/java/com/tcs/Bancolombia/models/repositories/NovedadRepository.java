package com.tcs.Bancolombia.models.repositories;

import com.tcs.Bancolombia.models.entities.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Long> {
    @Query("SELECT n FROM Novedad n WHERE MONTH(n.fechaInicio) = :mes AND YEAR(n.fechaInicio) = :anio")
    List<Novedad> findByMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);
    List<Novedad> findByNumEmpleado(Long numEmpleado);
}
