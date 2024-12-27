package com.uneb.labweb.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança que valida o token JWT presente no cabeçalho da requisição.
 * Caso o token seja válido, autentica o usuário no contexto de segurança.
 * 
 * <p>Este filtro recupera o token do cabeçalho "Authorization", valida o token com o {@link TokenService}
 * e autentica o usuário no contexto do Spring Security.</p>
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    /**
     * Construtor para o filtro de segurança.
     * 
     * @param tokenService serviço de validação do token
     * @param userRepository repositório de usuários
     */
    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    /**
     * Valida o token e autentica o usuário no contexto de segurança.
     * 
     * @param request requisição HTTP
     * @param response resposta HTTP
     * @param filterChain cadeia de filtros
     * @throws ServletException em caso de erro de servlet
     * @throws IOException em caso de erro de entrada/saída
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            User user = userRepository.findBySusCardNumber(login)
                    .orElseThrow(() -> new RuntimeException("User Not Found"));

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho da requisição.
     * 
     * @param request requisição HTTP
     * @return token JWT ou null se não encontrado
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return null;
        }
        
        return authHeader.replace("Bearer ", "");
    }
}
