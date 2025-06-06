package cl.tenpo.challengecalcservice.service.impl;

import cl.tenpo.challengecalcservice.client.PercentageClient;
import cl.tenpo.challengecalcservice.exception.PercentageFailedException;
import cl.tenpo.challengecalcservice.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PercentageServiceImpl implements PercentageService {

    private final PercentageClient percentageClient;
    private final CacheManager cacheManager;

    @Override
    public double getPercentage() {
        Cache cache = cacheManager.getCache("percentage");
        Double cachedPercentage = cache != null ? cache.get(SimpleKey.EMPTY, Double.class) : null;
        if (cachedPercentage != null) {
            log.info("Usando porcentaje desde caché de Redis: {}", cachedPercentage);
            return cachedPercentage;
        }

        try {
            Double percentage = percentageClient.getPercentage();
            if (percentage == null) {
                throw new PercentageFailedException("API externa con valor de porcentaje no disponible.");
            }

            log.info("Porcentaje obtenido desde API: {}", percentage);
            if (cache != null) {
                cache.put(SimpleKey.EMPTY, percentage); // guardar
            }

            return percentage;
        } catch (Exception ex) {
            log.error("Error al obtener porcentaje desde API externa y no hay caché: {}", ex.getMessage());
            throw new PercentageFailedException("No se pudo obtener el porcentaje ni existe valor en caché.");
        }
    }

}