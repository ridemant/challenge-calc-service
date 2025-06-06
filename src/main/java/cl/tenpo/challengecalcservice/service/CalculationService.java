package cl.tenpo.challengecalcservice.service;

import cl.tenpo.challengecalcservice.dto.CalculationRequest;

public interface CalculationService {
    double calculateWithPercentage(CalculationRequest request);
}