package com.tcs.Bancolombia.models.services;

import com.tcs.Bancolombia.models.dtos.EmpleadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${endpoint.employees}")
    private String endpoint;

    public List<EmpleadoDTO> fetchData() {
        EmpleadoDTO[] responseDTOS = restTemplate.getForObject(endpoint, EmpleadoDTO[].class);
        return Arrays.asList(responseDTOS);
    }
}
