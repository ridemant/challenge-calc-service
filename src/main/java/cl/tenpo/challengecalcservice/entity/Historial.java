package cl.tenpo.challengecalcservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String parametros;

    @Column(columnDefinition = "TEXT")
    private String respuesta;

    @Column(nullable = false)
    private boolean esError;

    @Column(nullable = false)
    private LocalDateTime fecha;
}