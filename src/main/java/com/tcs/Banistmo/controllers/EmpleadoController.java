package com.tcs.Banistmo.controllers;

import com.tcs.Banistmo.models.mappers.EmpleadoMapper;
import com.tcs.advices.NotFoundException;
import com.tcs.Banistmo.models.dtos.EmpleadoDTO;
import com.tcs.Banistmo.models.entities.Empleado;
import com.tcs.Banistmo.models.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("EmpleadoBanistmoController")
@RequestMapping("/employees")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/list")
    public ResponseEntity<?> listAll() {
        List<EmpleadoDTO> empleadoDTOList = EmpleadoMapper.INSTANCE.EMPLEADO_DTO_LIST(empleadoService.list());
        return new ResponseEntity<>(empleadoDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado empleado = EmpleadoMapper.INSTANCE.EMPLEADO(empleadoDTO);
            empleadoService.create(empleado);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message","Empleado creado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear el empleado: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody EmpleadoDTO empleadoDTO, @PathVariable Long id) {
        try {
            Empleado empleadoActualizado = EmpleadoMapper.INSTANCE.EMPLEADO(empleadoDTO);
            empleadoService.update(id, empleadoActualizado);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Empleado editado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el empleado: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el empleado: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            empleadoService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message","Empleado eliminado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
