package com.health.healther.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("spring.redis")
@Component
public class RedisProperties {
	private String host;

	private int port;

	private String username;

	private String password;
}
