package cl.tenpo.challengecalcservice.service.impl;


import cl.tenpo.challengecalcservice.dto.CalculationRequest;
import cl.tenpo.challengecalcservice.entity.Historial;
import cl.tenpo.challengecalcservice.exception.CalculationFailedException;
import cl.tenpo.challengecalcservice.repository.HistorialRepository;
import cl.tenpo.challengecalcservice.service.CalculationService;
import cl.tenpo.challengecalcservice.service.HistorialService;
import cl.tenpo.challengecalcservice.service.PercentageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PercentageService percentageService;
    private final HistorialService  historialService;

    @Override
    public double calculateWithPercentage(CalculationRequest request) {
        try {
            BigDecimal num1 = request.getNum1();
            BigDecimal num2 = request.getNum2();

            BigDecimal base = num1.add(num2); // suma
            BigDecimal percentage = BigDecimal.valueOf(percentageService.getPercentage()).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
            BigDecimal result = base.multiply(BigDecimal.ONE.add(percentage)); //base * (1 + porcentaje)
            result = result.setScale(2, RoundingMode.HALF_UP);

            saveHistoryAsync("/calculate", request, result, false);

            return result.doubleValue();

        } catch (Exception e) {
            saveHistoryAsync("/calculate", request, e.getMessage(), true);
            throw e;
        }
    }

    @Async
    public void saveHistoryAsync(String endpoint, Object request, Object response, boolean isError) {
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            String responseJson = objectMapper.writeValueAsString(response);

            Historial historial = new Historial();
            historial.setEndpoint(endpoint);
            historial.setParametros(requestJson);
            historial.setRespuesta(responseJson);
            historial.setEsError(isError);
            historial.setFecha(LocalDateTime.now());

            historialService.save(historial);
        } catch (Exception e) {
            throw new CalculationFailedException(e.getMessage());
        }
    }
}