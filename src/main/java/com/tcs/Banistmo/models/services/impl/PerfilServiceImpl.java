package com.tcs.Banistmo.models.services.impl;

import com.tcs.Banistmo.models.entities.Perfil;
import com.tcs.advices.NotFoundException;
import com.tcs.Banistmo.models.repositories.PerfilRepository;
import com.tcs.Banistmo.models.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public List<Perfil> list() {
        return perfilRepository.findAll();
    }

    @Override
    public Perfil create(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    public Perfil update(Long id, Perfil perfil) {
        return perfilRepository.findById(id)
                .map(perfilEditado -> {
                    perfilEditado.setNombrePerfil(perfil.getNombrePerfil());
                    perfilEditado.setSalario(perfil.getSalario());
                    return perfilRepository.save(perfilEditado);
                })
                .orElseThrow(() -> new NotFoundException("El perfil con el id: " + id + " no existe"));
    }

    @Override
    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }
}
