package cl.tenpo.challengecalcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@EnableAsync
public class ChallengeCalcServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeCalcServiceApplication.class, args);
	}

}
