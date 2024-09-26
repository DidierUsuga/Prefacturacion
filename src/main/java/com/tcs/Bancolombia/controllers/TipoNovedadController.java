package com.tcs.Bancolombia.controllers;

import com.tcs.Bancolombia.models.dtos.TipoNovedadDTO;
import com.tcs.Bancolombia.models.entities.TipoNovedad;
import com.tcs.Bancolombia.models.mappers.TipoNovedadMapper;
import com.tcs.Bancolombia.models.services.TipoNovedadService;
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
@RequestMapping("/tipo-novedad")
public class TipoNovedadController {
    @Autowired
    private TipoNovedadService tipoNovedadService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<TipoNovedadDTO> tipoNovedadDTOList = TipoNovedadMapper.INSTANCE.TIPO_NOVEDAD_DTO_LIST(tipoNovedadService.list());
        return new ResponseEntity<>(tipoNovedadDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TipoNovedadDTO tipoNovedadDTO) {
        try {
            TipoNovedad tipoNovedad = TipoNovedadMapper.INSTANCE.TIPO_NOVEDAD(tipoNovedadDTO);
            tipoNovedadService.create(tipoNovedad);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message","Tipo de novedad creado correctamente");
            return ResponseEntity.ok(responseBody);
        }catch (IllegalArgumentException e){
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear el tipo de novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody TipoNovedadDTO tipoNovedadDTO, @PathVariable Long id) {
        try {
            TipoNovedad tipoNovedadUpdate = TipoNovedadMapper.INSTANCE.TIPO_NOVEDAD(tipoNovedadDTO);
            tipoNovedadService.update(id,tipoNovedadUpdate);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Tipo de Novedad editado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el tipo de novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            tipoNovedadService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message","Tipo de novedad eliminada correctamente");
            return ResponseEntity.ok(responseBody);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
