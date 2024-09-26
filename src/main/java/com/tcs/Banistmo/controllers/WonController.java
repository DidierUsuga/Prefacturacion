package com.tcs.Banistmo.controllers;

import com.tcs.Banistmo.models.dtos.WonDTO;
import com.tcs.Banistmo.models.entities.Won;
import com.tcs.Banistmo.models.mappers.WonMapper;
import com.tcs.Banistmo.models.services.WonBanistmoService;
import com.tcs.advices.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("WonBanistmoController")
@RequestMapping("won-banistmo")
public class WonController {
    @Autowired
    private WonBanistmoService wonBanistmoService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        List<WonDTO> wonDTOList = WonMapper.INSTANCE.WON_DTO_LIST(wonBanistmoService.list());
        return new ResponseEntity<>(wonDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody WonDTO wonDTO){
        try {
            Won won = WonMapper.INSTANCE.WON(wonDTO);
            wonBanistmoService.create(won);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Won creado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al crear el won: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody WonDTO wonDTO, @PathVariable Long id) {
        try {
            Won won = WonMapper.INSTANCE.WON(wonDTO);
            wonBanistmoService.update(won, id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Won editado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el won: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        } catch (NotFoundException e) {
            Map<String, Object> errorResponseBody = new HashMap<>();
            errorResponseBody.put("message", "Error al editar el won: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseBody);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            wonBanistmoService.delete(id);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message","Won eliminado correctamente");
            return ResponseEntity.ok(responseBody);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
