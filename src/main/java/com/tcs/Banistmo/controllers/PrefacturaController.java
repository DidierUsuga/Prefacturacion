package com.tcs.Banistmo.controllers;

import com.tcs.Banistmo.models.dtos.PrefacturaDTO;
import com.tcs.Banistmo.models.services.impl.PrefacturaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("PrefacturaBanistmoController")
@RequestMapping("/prefacturacion")
public class PrefacturaController {

    @Autowired
    private PrefacturaServiceImpl prefacturacionService;

    @GetMapping("/lista")
    public ResponseEntity<List<PrefacturaDTO>> obtenerPrefacturacion(
            @RequestParam int mes,
            @RequestParam int anio) {
        List<PrefacturaDTO> prefacturacion = prefacturacionService.generarPrefacturacion(mes, anio);
        return new ResponseEntity<>(prefacturacion, HttpStatus.OK);
    }
}
