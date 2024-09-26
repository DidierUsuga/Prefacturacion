package com.tcs.Bancolombia.models.services;

import com.tcs.Bancolombia.models.entities.Calendario;
import com.tcs.Bancolombia.models.repositories.CalendarioRepository;
import com.tcs.advices.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarioService {

    @Autowired
    private CalendarioRepository calendarioRepository;

    public List<Calendario> list(){
        return calendarioRepository.findAll();
    }

    public Calendario create(Calendario calendario){
        return calendarioRepository.save(calendario);
    }

    public Calendario update(Long id, Calendario calendario){
        return calendarioRepository.findById(id)
                .map(calendarioUpdate -> {
                    calendarioUpdate.setDiaFestivo(calendario.getDiaFestivo());
                    return calendarioRepository.save(calendarioUpdate);
                })
                .orElseThrow(()->new NotFoundException("El día festivo con el id: " + id + " no existe"));
    }

    public void delete(Long id){
        calendarioRepository.deleteById(id);
    }
}
