package com.codeng.todo.config;

import com.codeng.todo.filter.TokenFilter;
import com.codeng.todo.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenService tokenService;

    private final String[] PUBLIC = {
            "/api/v1/auth/**"
    };

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure CORS
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("http://*"));
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("PATCH");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        http
                .cors(cors -> cors.configurationSource(source))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(PUBLIC).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
