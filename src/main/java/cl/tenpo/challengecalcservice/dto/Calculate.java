package cl.tenpo.challengecalcservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Calculate {
    private double result;
}