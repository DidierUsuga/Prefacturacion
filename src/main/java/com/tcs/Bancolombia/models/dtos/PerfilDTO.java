package com.tcs.Bancolombia.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    private int id;
    private String nombrePerfil;
    private Double salario;

    @JsonProperty("salarioFormateado")
    public String getSalarioFormateado() {
        if (salario != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
            return decimalFormat.format(salario);
        } else {
            return null;
        }
    }
}
