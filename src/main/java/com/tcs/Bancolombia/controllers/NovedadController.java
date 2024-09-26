package com.tcs.Bancolombia.controllers;

import com.tcs.Bancolombia.models.dtos.NovedadDTO;
import com.tcs.Bancolombia.models.entities.Novedad;
import com.tcs.Bancolombia.models.services.NovedadService;
import com.tcs.advices.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news-bancolombia")
public class NovedadController {
    @Autowired
    private NovedadService novedadService;

    @GetMapping("/list")
    public ResponseEntity<List<Novedad>> listNews() {
        List<Novedad> novedades = novedadService.list();
        return ResponseEntity.ok(novedades);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNews(@RequestBody NovedadDTO novedadDTO) {
        try {
            Novedad novedad = novedadService.create(novedadDTO);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad creada correctamente");
            return ResponseEntity.ok(responseBody);
        }catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @RequestBody NovedadDTO novedadDTO) {
        try {
            Novedad novedadActualizada = novedadService.update(id, novedadDTO);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad editada correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar la novedad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/filtrate")
    public List<Novedad> filtrateNews(int mes, int anio) {
        List<Novedad> novedades = novedadService.filtrate(mes, anio);
        if (novedades.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron novedades para el mes y año especificado.");
        }
        return novedades;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        try {
            novedadService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Novedad Eliminada correctamente");
            return ResponseEntity.ok(responseBody);
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllNews() {
        try {
            novedadService.deleteAll();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Todas las novedades han sido eliminadas correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar todas las novedades: " + e.getMessage());
        }
    }
}
