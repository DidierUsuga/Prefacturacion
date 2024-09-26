package com.tcs.Banistmo.models.repositories;

import com.tcs.Banistmo.models.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
