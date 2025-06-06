package cl.tenpo.challengecalcservice.service;

import cl.tenpo.challengecalcservice.entity.Historial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HistorialService {

    Page<Historial> getHistorial(Pageable pageable);

    void save(Historial historial);
}
