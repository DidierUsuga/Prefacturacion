package com.tcs.Bancolombia.controllers;

import com.tcs.Bancolombia.models.dtos.PrefacturaDTO;
import com.tcs.Bancolombia.models.services.PrefacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prefactura-bancolombia")
public class PrefacturaController {

    @Autowired
    private PrefacturaService prefacturaService;

    @GetMapping("/generate")
    public ResponseEntity<List<PrefacturaDTO>> generate(
            @RequestParam int mes,
            @RequestParam int anio) {
        List<PrefacturaDTO> prefacturas = prefacturaService.generate(mes, anio);
        return ResponseEntity.ok(prefacturas);
    }
}
