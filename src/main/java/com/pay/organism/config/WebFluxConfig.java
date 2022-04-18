package com.pay.organism.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer
{
	Logger logger = LoggerFactory.getLogger(WebFluxConfig.class);

    @Value("${PayItemUrl}")
    private String PayItemUrl;

	@Bean
	public WebClient getWebClient()
	{
		return WebClient.builder()
		        .baseUrl("http://localhost:8080")
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .build();
	}
}
    
}
