package cl.tenpo.challengecalcservice.controller;

import cl.tenpo.challengecalcservice.dto.CalculationRequest;
import cl.tenpo.challengecalcservice.exception.PercentageFailedException;
import cl.tenpo.challengecalcservice.service.CalculationService;
import cl.tenpo.challengecalcservice.service.HistorialService;
import cl.tenpo.challengecalcservice.service.PercentageService;
import cl.tenpo.challengecalcservice.service.impl.CalculationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CalculationController.class)
class CalculationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PercentageService percentageService;

    @MockitoBean
    private CalculationService calculationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void calculate_returns200_WhenValidRequest() throws Exception {
        Mockito.when(calculationService.calculateWithPercentage(any()))
                .thenReturn(110.0);

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"num1\":10,\"num2\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(110.0));
    }

    @Test
    void calculate_whenExternalFailsButCacheExists() throws Exception {

        Mockito.when(calculationService.calculateWithPercentage(any()))
                .thenReturn(105.0); // Resultado esperado

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"num1\":10,\"num2\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(105.0));
    }

    @Test
    void calculate_whenExternalAndCacheFail() throws Exception {
        Mockito.when(calculationService.calculateWithPercentage(any()))
                .thenThrow(new PercentageFailedException("No se pudo obtener el porcentaje ni existe valor en caché."));

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"num1\":50,\"num2\":50}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(503))
                .andExpect(jsonPath("$.data.message").value("No se pudo obtener el porcentaje ni existe valor en caché."));
    }

}