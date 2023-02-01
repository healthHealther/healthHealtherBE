package com.health.healther.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final String originPattern;

	public WebMvcConfig(
		@Value("${cors.origin}") String originPattern
	) {
		this.originPattern = originPattern;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowedOriginPatterns(originPattern)
			.exposedHeaders("*")
			.allowCredentials(true);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
