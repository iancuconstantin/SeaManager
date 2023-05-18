package com.codecool.seamanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.cors(withDefaults()) //by default use a bean by the name of corsConfiguration
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth-> auth.anyRequest().authenticated())
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(withDefaults())
				.build();
	}

	@Bean public InMemoryUserDetailsManager user() {
		return new InMemoryUserDetailsManager(
				User.withUsername("admin")
						.password("{noop}admin123")
						.authorities("read")
						.build()
		);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
		configuration.setAllowedHeaders(List.of("Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
