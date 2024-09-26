package com.tcs.Bancolombia.controllers;

import com.tcs.Bancolombia.models.dtos.EmpleadoDTO;
import com.tcs.Bancolombia.models.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("EmpleadoBancolombiaController")
@RequestMapping("employees-bancolombia")
public class EmpleadoController {
    @Autowired
    private EmpleadoService EmpleadoService;

    @GetMapping("/list")
    public ResponseEntity<List<EmpleadoDTO>> fetchData() {
        List<EmpleadoDTO> data = EmpleadoService.fetchData();
        return ResponseEntity.ok(data);
    }
}
