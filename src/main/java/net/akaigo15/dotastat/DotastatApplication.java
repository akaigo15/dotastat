package net.akaigo15.dotastat;

import net.akaigo15.dotastat.opendota.cache.DotaCacheConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DotastatApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DotastatApplication.class)
				.run();
	}

	@Bean
	RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	DotaCacheConfig createCacheConfig() {return new DotaCacheConfig(10*60);}
}
