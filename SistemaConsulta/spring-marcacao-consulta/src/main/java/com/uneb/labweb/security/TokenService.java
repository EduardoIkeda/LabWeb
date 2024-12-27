package com.uneb.labweb.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uneb.labweb.model.User;

/**
 * Serviço responsável pela geração e validação de tokens JWT.
 * 
 * <p>O {@link TokenService} permite gerar tokens de autenticação para usuários e validar tokens existentes.
 * Utiliza a chave secreta configurada para criar tokens seguros e para verificar sua autenticidade.</p>
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     * 
     * @param user o usuário para o qual o token será gerado
     * @return o token JWT gerado
     * @throws RuntimeException se ocorrer um erro na criação do token
     */
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("sus-auth-api")
                    .withSubject(user.getSusCardNumber())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    /**
     * Valida o token JWT fornecido.
     * 
     * @param token o token JWT a ser validado
     * @return o assunto (subject) do token se válido, ou null se inválido
     */
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("sus-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * Gera a data de expiração para o token JWT.
     * 
     * @return a data e hora de expiração do token
     */
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
