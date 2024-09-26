package com.tcs.Banistmo.models.services;

import com.tcs.Banistmo.models.entities.NovedadBanistmo;

import java.util.List;

public interface NovedadBanistmoService {
    List<NovedadBanistmo> list();

    NovedadBanistmo create(NovedadBanistmo novedadBanistmo);

    NovedadBanistmo update(Long id, NovedadBanistmo novedadBanistmo);

    void delete(Long id);

    List<NovedadBanistmo> filtrate(int mes, int anio);

    void deleteAll();
}
