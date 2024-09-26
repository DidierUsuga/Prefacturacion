package com.tcs.Bancolombia.models.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarioDTO {
    private Long id;
    private Date diaFestivo;
}
