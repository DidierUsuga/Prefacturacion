package com.tcs.Banistmo.models.repositories;

import com.tcs.Banistmo.models.entities.Won;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WonRepository extends JpaRepository<Won, Long> {
}
