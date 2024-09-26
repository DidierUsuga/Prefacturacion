package com.tcs.Bancolombia.models.repositories;

import com.tcs.Bancolombia.models.entities.TipoNovedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoNovedadRepository extends JpaRepository<TipoNovedad, Long> {
}
