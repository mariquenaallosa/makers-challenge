package com.challenge.loansystem.config;

import com.challenge.loansystem.domain.model.JwtProperties;
import com.challenge.loansystem.infraestructure.security.jwt.JwtEntryPoint;
import com.challenge.loansystem.infraestructure.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
    private JwtEntryPoint jwtEntryPoint;
    private JwtProperties jwtProperties;


    List<String> publicUrlPatterns = Arrays.asList(
        "/api-docs.yml",
        "api/auth/**"
    );
    // URLs para usuarios por rol
    List<String> userUrls = Arrays.asList(
            "/api/loan/**"
    );
    List<String> adminUrls = Arrays.asList(
            "/api/admin/**"
    );
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(publicUrlPatterns.toArray(new String[0])).permitAll()
                        .requestMatchers(adminUrls.toArray(new String[0])).hasRole("ADMIN")
                        .requestMatchers(userUrls.toArray(new String[0])).hasRole("USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(Customizer.withDefaults())
                .exceptionHandling(customize -> {
                    customize.authenticationEntryPoint(jwtEntryPoint);
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
