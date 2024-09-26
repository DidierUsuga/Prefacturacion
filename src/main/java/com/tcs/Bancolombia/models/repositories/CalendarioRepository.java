package com.tcs.Bancolombia.models.repositories;

import com.tcs.Bancolombia.models.entities.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
}
