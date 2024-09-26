package com.tcs.Banistmo.controllers;

import com.tcs.Banistmo.models.mappers.NovedadMapper;
import com.tcs.Banistmo.models.dtos.NovedadDTO;
import com.tcs.Banistmo.models.entities.NovedadBanistmo;
import com.tcs.Banistmo.models.services.NovedadBanistmoService;
import com.tcs.advices.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("NovedadBanistmoController")
@RequestMapping("/news-banistmo")
public class NovedadController {
    @Autowired
    private NovedadBanistmoService novedadBancolombiaService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<NovedadDTO> novedadDTOList = NovedadMapper.INSTANCE.NOVEDAD_DTO_LIST(novedadBancolombiaService.list());
        return new ResponseEntity<>(novedadDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody NovedadDTO novedadDTO) {
        try {
            NovedadBanistmo novedadBanistmo = NovedadMapper.INSTANCE.NOVEDAD(novedadDTO);
            novedadBancolombiaService.create(novedadBanistmo);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad creada correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody NovedadDTO novedadDTO, @PathVariable Long id) {
        try {
            NovedadBanistmo novedadActualizada = NovedadMapper.INSTANCE.NOVEDAD(novedadDTO);
            novedadBancolombiaService.update(id, novedadActualizada);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad editada correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            novedadBancolombiaService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad Eliminada correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al eliminar la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @GetMapping("/filtrate")
    public ResponseEntity<?> filtrate(@RequestParam int mes, @RequestParam int anio) {
        List<NovedadBanistmo> novedades = novedadBancolombiaService.filtrate(mes, anio);
        List<NovedadDTO> novedadDTOList = NovedadMapper.INSTANCE.NOVEDAD_DTO_LIST(novedades);
        return new ResponseEntity<>(novedadDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        try {
            novedadBancolombiaService.deleteAll();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Todas las novedades han sido eliminadas correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar las novedades: " + e.getMessage());
        }
    }
}

