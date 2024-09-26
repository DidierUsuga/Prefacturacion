package com.tcs.Banistmo.models.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WonDTO {
    private Long id;
    private Long numeroWon;
    private String nombreWon;
    private Boolean estadoWon;
}
