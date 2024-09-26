package com.tcs.Banistmo.models.services;

import com.tcs.Banistmo.models.entities.Won;

import java.util.List;

public interface WonBanistmoService {
    List<Won> list();

    Won create(Won won);

    Won update(Won won, Long id);

    void delete(Long id);

}
