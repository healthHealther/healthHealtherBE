package com.health.healther.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
	@Bean
	public JwtTokenProvider jwtAuthenticationProvider() {
		return new JwtTokenProvider();
	}
}
