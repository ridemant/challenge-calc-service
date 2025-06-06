package cl.tenpo.challengecalcservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculationRequest {

    @NotNull(message = "El número 1 es obligatorio.")
    private BigDecimal num1;

    @NotNull(message = "El número 2 es obligatorio.")
    private BigDecimal num2;
}