package com.example.application_tracker.config;

import com.example.application_tracker.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final List<String> allowedOrigins;


    public SecurityConfig(@Value("${app.cors.allowed-origins}")
                          String allowedOrigins, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.allowedOrigins = Arrays.asList(allowedOrigins.split(","));
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        // Spring auto-configure the AuthenticationManager with UserDetailsService and PasswordEncoder
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Cookies or Authorization header
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .cors(Customizer.withDefaults())
                // REST API with Bearer Token→ no CSRF necessary
                .csrf(AbstractHttpConfigurer::disable)

                // Stateless API via Bearer Token  → no server  side HTTP sessions necessary
                // all state data is encoded into the token itself
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules, via request matchers: pattern -> behavior pars in order
                // A request has the behavior of the first request matchers it matches with
                .authorizeHttpRequests(auth -> auth
                        // allow Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**", "/api/health", "/api/users"
                        ).permitAll()

                        // everything else requires authentication
                        .anyRequest().authenticated()
                )
                // Enable http basic authentication
                .httpBasic(Customizer.withDefaults())
                // Disable default login form &
                .formLogin(AbstractHttpConfigurer::disable)
                // Add Bearer Token Filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
