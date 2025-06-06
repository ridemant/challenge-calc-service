package cl.tenpo.challengecalcservice.controller;

import cl.tenpo.challengecalcservice.dto.ApiResponseBody;
import cl.tenpo.challengecalcservice.dto.Calculate;
import cl.tenpo.challengecalcservice.dto.CalculationRequest;
import cl.tenpo.challengecalcservice.service.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @Operation(summary = "Calcula el resultado sumando dos números y aplicando un porcentaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/calculate")

    public ResponseEntity<ApiResponseBody<Calculate>> calculate(@RequestBody @Valid CalculationRequest request) {
        return ResponseEntity.ok(
                ApiResponseBody.success(Calculate.of(calculationService.
                        calculateWithPercentage(request)))
        );
    }
}