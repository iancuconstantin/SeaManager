package com.codecool.seamanager.config;

import com.codecool.seamanager.auth.JpaUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final RsaKeyProperties jwtConfigProperties;
	private final JpaUserDetailsService jpaUserDetailsService;

	public SecurityConfig(RsaKeyProperties jwtConfigProperties, JpaUserDetailsService jpaUserDetailsService) {
		this.jwtConfigProperties = jwtConfigProperties;
		this.jpaUserDetailsService = jpaUserDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors(withDefaults()) //by default use a bean by the name of corsConfiguration
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
							auth.requestMatchers("/api/employee/**").hasAnyRole("USER", "ADMIN");
							auth.requestMatchers("/api/vessel/**").hasRole("ADMIN");
							auth.anyRequest().authenticated();
						}
				)
				.userDetailsService(jpaUserDetailsService)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.headers(headers -> headers.frameOptions().sameOrigin())
				.exceptionHandling(
						(ex) -> ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
								.accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
				.httpBasic(withDefaults())
				.build();
	}

	// This will allow the /token endpoint to use basic auth and everything else uses the SFC above
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	@Bean
//	SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
//		return http
//				.cors(withDefaults())//by default use a bean by the name of corsConfiguration
//				.csrf(AbstractHttpConfigurer::disable)
//				.authorizeHttpRequests(auth -> {
//							auth.requestMatchers("/token").authenticated();
//							auth.anyRequest().authenticated();
//						}
//				)
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.exceptionHandling(ex -> {
//					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
//					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
//				})
//				.httpBasic(withDefaults())
//				.build();
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder
				.withPublicKey(jwtConfigProperties.publicKey())
				.build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey
				.Builder(jwtConfigProperties.publicKey()).privateKey(jwtConfigProperties.privateKey())
				.build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
}
