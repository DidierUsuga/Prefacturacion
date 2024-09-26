package com.tcs.Banistmo.models.repositories;

import com.tcs.Banistmo.models.entities.NovedadBanistmo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovedadBanistmoRepository extends JpaRepository<NovedadBanistmo, Long> {
    List<NovedadBanistmo> findByEmpleadoId(Long empleadoId);

    @Query("SELECT n FROM NovedadBanistmo n WHERE MONTH(n.fechaInicio) = :mes AND YEAR(n.fechaInicio) = :anio")
    List<NovedadBanistmo> findByMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);
}
