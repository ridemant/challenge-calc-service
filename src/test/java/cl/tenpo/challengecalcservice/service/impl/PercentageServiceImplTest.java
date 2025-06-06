package cl.tenpo.challengecalcservice.service.impl;

import cl.tenpo.challengecalcservice.client.PercentageClient;
import cl.tenpo.challengecalcservice.exception.PercentageFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PercentageServiceImplTest {

    @Mock
    private PercentageClient percentageClient;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @Test
    void percentage_returnPercentageFromExternal() {
        // Arrange
        when(percentageClient.getPercentage()).thenReturn(15.0);

        // Act
        double result = percentageService.getPercentage();

        // Assert
        assertEquals(15.0, result);
        verify(percentageClient).getPercentage();
    }

    @Test
    // cuando el api externo retorna 10.5 pero en cache redis no hay nada
    void percentage_returnPercentageFromApiAndCacheIt() {
        // Arrange
        Double expected = 10.5;
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get(SimpleKey.EMPTY, Double.class)).thenReturn(null); // cuando no hay nada en cache
        when(percentageClient.getPercentage()).thenReturn(expected); // pero el api si retorna

        // Act
        double result = percentageService.getPercentage();

        // Assert
        assertEquals(expected, result);
        verify(cache).put(SimpleKey.EMPTY, expected);
    }

    @Test
    void percentage_throwException_WhenNoCacheAndApiReturnsNull() {
        // Arrange
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get(any(), eq(Double.class))).thenReturn(null); // valor cache = null
        when(percentageClient.getPercentage()).thenReturn(null); // valor percentage = null

        // Assert
        PercentageFailedException exception = assertThrows(
                PercentageFailedException.class,
                () -> percentageService.getPercentage()
        );

        assertEquals("No se pudo obtener el porcentaje ni existe valor en caché.", exception.getMessage());
    }

    @Test
    void percentage_throwException_WhenNoCacheAndApiFails() {
        // Arrange
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get(any(), eq(Double.class))).thenReturn(null);

        when(percentageClient.getPercentage())
                .thenThrow(new RuntimeException("API externo no disponible"));

        // Assert
        PercentageFailedException exception = assertThrows(
                PercentageFailedException.class,
                () -> percentageService.getPercentage()
        );
        assertEquals("No se pudo obtener el porcentaje ni existe valor en caché.", exception.getMessage());
    }
}