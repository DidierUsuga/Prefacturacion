package com.tcs.Banistmo.models.services;

import com.tcs.Banistmo.models.entities.Perfil;

import java.util.List;

public interface PerfilService {
    List<Perfil> list();

    Perfil create(Perfil perfil);

    Perfil update(Long id, Perfil perfil);

    void delete(Long id);
}
