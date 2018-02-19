package net.akaigo15.dotastat;

import net.akaigo15.dotastat.opendota.cache.DotaCacheConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DotastatApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotastatApplication.class, args);
	}

	@Bean
	RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	DotaCacheConfig createCacheConfig() {return new DotaCacheConfig(10*60);}
}
