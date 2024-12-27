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

/**
 * Configuração de segurança do Spring Security.
 * Define a política de sessões (stateless), desativa o CSRF e configura as permissões de acesso.
 * 
 * <p>Permite acesso sem autenticação para rotas específicas (login, registro, H2 console) e exige autenticação
 * para as demais rotas. Inclui um filtro de segurança personalizado.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    /**
     * Construtor para configuração de segurança.
     * 
     * @param securityFilter filtro de segurança personalizado
     */
    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    /**
     * Configura o filtro de segurança.
     * 
     * @param http configurações do HttpSecurity
     * @return filtro de segurança configurado
     * @throws Exception em caso de erro na configuração
     */
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
                        
                        // User entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        
                        // Appointment entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/appointments").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/group").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/years-with-appointments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/by-user/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.POST, "/api/appointments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/appointments/schedule/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.PATCH, "/api/appointments/cancel/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/{id}").hasRole("ADMIN")

                        // Doctor entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/doctors").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/doctors/by-health-center/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/doctors/doctor-id/{userId}").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/doctors/{id}").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/doctors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/{id}").hasRole("ADMIN")

                        // HealthCenter entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/health-centers").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/health-centers/by-specialty/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/health-centers/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.POST, "/api/health-centers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/health-centers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/health-centers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/health-centers/{id}").hasRole("ADMIN")

                        // Penalty entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/penalties").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/penalties/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/penalties").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/penalties/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/penalties/{id}").hasRole("ADMIN")

                        // Specialty entity permissions
                        .requestMatchers(HttpMethod.GET, "/api/specialties").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/specialties/{id}").hasRole("CITIZEN")
                        .requestMatchers(HttpMethod.GET, "/api/specialties/count/{year}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/specialties").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/specialties/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/specialties/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
                
        return http.build();
    }

    /**
     * Define o encoder de senhas usando BCrypt.
     * 
     * @return encoder de senha
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o gerenciador de autenticação.
     * 
     * @param authenticationConfiguration configuração de autenticação
     * @return gerenciador de autenticação
     * @throws Exception em caso de erro
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
