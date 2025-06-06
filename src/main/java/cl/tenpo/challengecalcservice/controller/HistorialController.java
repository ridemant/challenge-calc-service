package cl.tenpo.challengecalcservice.controller;

import cl.tenpo.challengecalcservice.dto.ApiResponseBody;
import cl.tenpo.challengecalcservice.entity.Historial;
import cl.tenpo.challengecalcservice.service.HistorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @Operation(summary = "Obtiene el historial de llamadas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponseBody<Page<Historial>>> getHistorial(Pageable pageable) {
        return ResponseEntity
                .ok(ApiResponseBody.success(historialService.getHistorial(pageable)));
    }
}