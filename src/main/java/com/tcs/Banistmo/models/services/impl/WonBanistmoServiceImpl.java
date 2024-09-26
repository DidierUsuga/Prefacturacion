package com.tcs.Banistmo.models.services.impl;

import com.tcs.Banistmo.models.entities.Won;
import com.tcs.Banistmo.models.repositories.WonRepository;
import com.tcs.Banistmo.models.services.WonBanistmoService;
import com.tcs.advices.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WonBanistmoServiceImpl implements WonBanistmoService {

    @Autowired
    private WonRepository wonRepository;

    @Override
    public List<Won> list() {
        return wonRepository.findAll();
    }

    @Override
    public Won create(Won won) {
        return wonRepository.save(won);
    }

    @Override
    public Won update(Won won, Long id) {
        return wonRepository.findById(id)
                .map(wonEditado -> {
                    wonEditado.setNumeroWon(won.getNumeroWon());
                    wonEditado.setNombreWon(won.getNombreWon());
                    wonEditado.setEstadoWon(won.getEstadoWon());
                    return wonRepository.save(wonEditado);
                })
                .orElseThrow(() -> new NotFoundException("El won con el id: " + id + " no existe"));
    }

    @Override
    public void delete(Long id) {
        wonRepository.deleteById(id);
    }
}
