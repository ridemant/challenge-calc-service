package cl.tenpo.challengecalcservice.service.impl;

import cl.tenpo.challengecalcservice.entity.Historial;
import cl.tenpo.challengecalcservice.repository.HistorialRepository;
import cl.tenpo.challengecalcservice.service.HistorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialServiceImpl implements HistorialService {

    private final HistorialRepository historialRepository;

    @Override
    public Page<Historial> getHistorial(Pageable pageable) {
        return historialRepository.findAll(pageable);
    }

    @Override
    public void save(Historial historial) {
        historialRepository.save(historial);
    }

}
