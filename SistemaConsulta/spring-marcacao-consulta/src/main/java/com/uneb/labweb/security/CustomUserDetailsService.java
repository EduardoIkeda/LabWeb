package com.uneb.labweb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.uneb.labweb.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repositório para acesso aos dados de usuários no banco de dados.
     */
    private final UserRepository userRepository;

    /**
     * Construtor para inicializar o repositório de usuários.
     * 
     * @param userRepository o repositório utilizado para buscar os dados do usuário
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega os detalhes do usuário com base no número do cartão SUS fornecido.
     * Este método é chamado pelo Spring Security durante o processo de autenticação.
     * 
     * @param login o número do cartão SUS que será utilizado para localizar o usuário
     * @return um objeto {@link UserDetails} contendo as informações do usuário
     * @throws UsernameNotFoundException se o usuário não for encontrado com o número do cartão SUS fornecido
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.userRepository.findBySusCardNumber(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
