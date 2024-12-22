package com.uneb.labweb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                        
                        // Para teste
                        //.requestMatchers(HttpMethod.POST, "/api/appointments").permitAll()

                        // .requestMatchers("/api/appointments").hasRole("DOCTOR")
                        // .requestMatchers("/api/doctors").hasRole("ADMIN")
                        // .requestMatchers("/api/health-centers").hasRole("ADMIN")
                        // .requestMatchers("/api/penalties").hasRole("ADMIN")
                        // .requestMatchers("/api/specialties").hasRole("ADMIN")

                        // // User entity permissions
                        // .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
                
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
