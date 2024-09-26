package com.tcs.Bancolombia.controllers;

import com.tcs.Bancolombia.models.dtos.CalendarioDTO;
import com.tcs.Bancolombia.models.entities.Calendario;
import com.tcs.Bancolombia.models.mappers.CalendarioMapper;
import com.tcs.Bancolombia.models.services.CalendarioService;
import com.tcs.advices.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar-bancolombia")
public class CalendarioController {
    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        List<CalendarioDTO> calendarioDTOList = CalendarioMapper.INSTANCE.CALENDARIO_DTO_LIST(calendarioService.list());
        return new ResponseEntity<>(calendarioDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CalendarioDTO calendarioDTO){
        try {
            Calendario calendario = CalendarioMapper.INSTANCE.CALENDARIO(calendarioDTO);
            calendarioService.create(calendario);
            Map<String, Object> resposeBody = new HashMap<>();
            resposeBody.put("message","Día festivo creado correctamente");
            return ResponseEntity.ok(resposeBody);
        }catch (IllegalArgumentException e){
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message","Error al crear el día festivo: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CalendarioDTO calendarioDTO, @PathVariable Long id){
        try {
            Calendario calendarioUpdate = CalendarioMapper.INSTANCE.CALENDARIO(calendarioDTO);
            calendarioService.update(id, calendarioUpdate);
            Map<String, Object> resposeBody = new HashMap<>();
            resposeBody.put("message","Día festivo editado correctamente");
            return ResponseEntity.ok(resposeBody);
        }catch (IllegalArgumentException e){
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message","Error al editar el día festivo: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            calendarioService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Día festivo eliminado correctamente");
            return ResponseEntity.ok(responseBody);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
