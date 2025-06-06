package cl.tenpo.challengecalcservice.repository;

import cl.tenpo.challengecalcservice.entity.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialRepository extends JpaRepository<Historial, Long> {
}