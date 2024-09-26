package com.tcs.Banistmo.controllers;

import com.tcs.Banistmo.models.mappers.PerfilMapper;
import com.tcs.Banistmo.models.dtos.PerfilDTO;
import com.tcs.Banistmo.models.entities.Perfil;
import com.tcs.Banistmo.models.services.PerfilService;
import com.tcs.advices.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("PerfilBanistmoController")
@RequestMapping("/perfil-banistmo")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<PerfilDTO> perfilDTOList = PerfilMapper.INSTANCE.PERFIL_DTO_LIST(perfilService.list());
        return new ResponseEntity<>(perfilDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PerfilDTO perfilDTO) {
        try {
            Perfil perfil = PerfilMapper.INSTANCE.PERFIL(perfilDTO);
            perfilService.create(perfil);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Perfil creado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear el perfil: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody PerfilDTO perfilDTO, @PathVariable Long id) {
        try {
            Perfil perfilActualizado = PerfilMapper.INSTANCE.PERFIL(perfilDTO);
            perfilService.update(id, perfilActualizado);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Perfil editado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el perfil: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el perfil: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            perfilService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Perfil eliminado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al eliminar el perfil: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }
}
