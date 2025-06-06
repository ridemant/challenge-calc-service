package cl.tenpo.challengecalcservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "percentageClient", url = "${external.percentage.url}")
public interface PercentageClient {

    @GetMapping
    Double getPercentage();
}