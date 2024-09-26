package com.tcs.Bancolombia.models.services;

import com.tcs.Bancolombia.models.entities.TipoNovedad;
import com.tcs.Bancolombia.models.repositories.TipoNovedadRepository;
import com.tcs.advices.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoNovedadService {
    @Autowired
    private TipoNovedadRepository tipoNovedadRepository;

    public List<TipoNovedad> list() {
        return tipoNovedadRepository.findAll();
    }

    public TipoNovedad create(TipoNovedad tipoNovedad) {
        return tipoNovedadRepository.save(tipoNovedad);
    }

    public TipoNovedad update(Long id, TipoNovedad tipoNovedad){
        return tipoNovedadRepository.findById(id)
                .map(tipoNovedadUpdate ->{
                    tipoNovedadUpdate.setTipoNovedad(tipoNovedad.getTipoNovedad());
                    return tipoNovedadRepository.save(tipoNovedadUpdate);
                })
                .orElseThrow(()->new NotFoundException("El tipo novedad con el id: " + id + " no existe"));
    }

    public void delete(Long id){
        tipoNovedadRepository.deleteById(id);
    }
}
