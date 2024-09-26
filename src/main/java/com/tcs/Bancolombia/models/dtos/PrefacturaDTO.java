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
public class PrefacturaDTO {
    private Long numEmpleado;
    private String cedulaEmpleado;
    private String wonProyecto;
    private String nomEmpleado;
    private String projectManager;
    private String perfil;
    private String tipoNovedad;
    private String comentarioNovedad;
    private Double tarifaMes;
    private Double tarifaHora;
    private Double valorAFacturar;
    private Double afectacionPorNovedades;
    private Double horasFacturables;
    private Double horasNoFacturables;
    private Double diasHabiles;
    private Double diasNoFacturables;
    private Double diasDeCosto;

    @JsonProperty("tarifaMesFormateado")
    public String getTarifaMesFormateado() {
        if (tarifaMes != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
            return decimalFormat.format(tarifaMes);
        } else {
            return null;
        }
    }

    @JsonProperty("afectacionPorNovedadesFormateado")
    public String getafectacionPorNovedadesFormateado() {
        if (afectacionPorNovedades != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
            return decimalFormat.format(afectacionPorNovedades);
        } else {
            return null;
        }
    }

    @JsonProperty("tarifaHoraFormateado")
    public String getTarifaHoraFormateado() {
        if (tarifaHora != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
            return decimalFormat.format(tarifaHora);
        } else {
            return null;
        }
    }

    @JsonProperty("valorAFacturarFormateado")
    public String getValorAFacturarFormateado() {
        if (valorAFacturar != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CO"));
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
            return decimalFormat.format(valorAFacturar);
        } else {
            return null;
        }
    }
}
