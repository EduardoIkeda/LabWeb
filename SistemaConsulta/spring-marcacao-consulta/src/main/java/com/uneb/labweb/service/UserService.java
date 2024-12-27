package com.uneb.labweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.dto.mapper.UserMapper;
import com.uneb.labweb.dto.request.LoginDTO;
import com.uneb.labweb.dto.request.RegisterDTO;
import com.uneb.labweb.dto.request.UserDTO;
import com.uneb.labweb.dto.request.UserPartialDTO;
import com.uneb.labweb.dto.response.AuthResponseDTO;
import com.uneb.labweb.enums.Role;
import com.uneb.labweb.exception.RecordAlreadyExistsException;
import com.uneb.labweb.exception.RecordNotFoundException;
import com.uneb.labweb.exception.WrongPasswordException;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.UserRepository;
import com.uneb.labweb.security.TokenService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder, TokenService tokenService, UserRepository userRepository,
            UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Realiza o login do usuário.
     * @param loginDTO Dados de login do usuário
     * @return DTO de resposta de autenticação
     * @throws WrongPasswordException Se a senha estiver incorreta
     * @throws RecordNotFoundException Se o usuário não for encontrado
     */
    public AuthResponseDTO login(@Valid @NotNull LoginDTO loginDTO) {
        return userRepository.findBySusCardNumber(loginDTO.susCardNumber())
                .map(user -> {
                    // Verifica se a senha é válida
                    if (passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
                        String token = this.tokenService.generateToken(user);
                        return new AuthResponseDTO(
                            user.getId(), 
                            user.getName(), 
                            user.getAvatarUrl(), 
                            user.getRole().getValue(),
                            user.getUserStatus().getValue(), 
                            token
                        );
                    }

                    throw new WrongPasswordException();  // Senha incorreta
                })
                .orElseThrow(RecordNotFoundException::new);  // Usuário não encontrado
    }

    /**
     * Registra um novo usuário.
     * @param registerDTO Dados do usuário a ser registrado
     * @return DTO de resposta de autenticação
     * @throws RecordAlreadyExistsException Se o usuário já existir
     */
    public AuthResponseDTO register(@Valid @NotNull RegisterDTO registerDTO) {
        Optional<User> user = userRepository.findBySusCardNumber(registerDTO.susCardNumber());

        if (user.isEmpty()) {
            // Cria um novo usuário
            User newUser = new User();
            newUser.setSusCardNumber(registerDTO.susCardNumber());
            newUser.setName(registerDTO.name());
            newUser.setCpf(registerDTO.cpf());
            newUser.setPhone(registerDTO.phone());
            newUser.setEmail(registerDTO.email());
            newUser.setPassword(passwordEncoder.encode(registerDTO.password()));  // Codifica a senha
            newUser.setAvatarUrl("https://i.imgur.com/7WymxTr.png");  // Avatar padrão
            newUser.setRole(Role.CITIZEN);  // Define o papel como CIDADÃO
            User registeredUser = userRepository.save(newUser);

            // Gera o token para o novo usuário
            String token = this.tokenService.generateToken(newUser);
            return new AuthResponseDTO(
                registeredUser.getId(), 
                registeredUser.getName(), 
                registeredUser.getAvatarUrl(), 
                registeredUser.getRole().getValue(),
                registeredUser.getUserStatus().getValue(), 
                token
            );
        }

        throw new RecordAlreadyExistsException(user.get().getId());  // Usuário já existe
    }

    /**
     * Retorna todos os usuários cadastrados.
     * @return Lista de DTOs de usuários
     */
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    /**
     * Retorna um usuário pelo ID.
     * @param id ID do usuário
     * @return DTO do usuário
     * @throws RecordNotFoundException Se o usuário não for encontrado
     */
    public UserDTO findUserById(@NotNull @Positive Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));  // Usuário não encontrado
    }

    /**
     * Cria um novo usuário.
     * @param userDTO Dados do usuário a ser criado
     * @return DTO do usuário criado
     * @throws RecordAlreadyExistsException Se o usuário já existir
     */
    public UserDTO createUser(@Valid @NotNull UserDTO userDTO) {
        Optional<User> user = userRepository.findBySusCardNumber(userDTO.susCardNumber());

        if (user.isEmpty()) {
            return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));  // Salva e mapeia o novo usuário
        }

        throw new RecordAlreadyExistsException(user.get().getId());  // Usuário já existe
    }

    /**
     * Atualiza os dados de um usuário existente.
     * @param id ID do usuário
     * @param userDTO Dados atualizados do usuário
     * @return DTO do usuário atualizado
     * @throws RecordNotFoundException Se o usuário não for encontrado
     */
    public UserDTO updateUser(@NotNull @Positive Long id, @Valid @NotNull UserDTO userDTO) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    // Atualiza os campos do usuário
                    recordFound.setSusCardNumber(userDTO.susCardNumber());
                    recordFound.setName(userDTO.name());
                    recordFound.setCpf(userDTO.cpf());
                    recordFound.setPhone(userDTO.phone());
                    recordFound.setEmail(userDTO.email());
                    recordFound.setPassword(passwordEncoder.encode(userDTO.password()));  // Codifica a senha
                    recordFound.setRole(userMapper.convertRoleValue(userDTO.role()));

                    return userMapper.toDTO(userRepository.save(recordFound));  // Salva e retorna o usuário atualizado
                })
                .orElseThrow(() -> new RecordNotFoundException(id));  // Usuário não encontrado
    }

    /**
     * Atualiza parcialmente os dados de um usuário.
     * @param id ID do usuário
     * @param userDTO Dados parciais do usuário
     * @return DTO do usuário atualizado
     * @throws RecordNotFoundException Se o usuário não for encontrado
     */
    public UserDTO patchUser(@NotNull @Positive Long id, @Valid @NotNull UserPartialDTO userDTO) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    // Atualiza os campos parciais do usuário
                    if (userDTO.avatarUrl() != null && !userDTO.avatarUrl().isEmpty()) {
                        recordFound.setAvatarUrl(userDTO.avatarUrl());
                    }

                    if (userDTO.phone() != null && !userDTO.phone().isEmpty()) {
                        recordFound.setPhone(userDTO.phone());
                    }

                    if (userDTO.email() != null && !userDTO.email().isEmpty()) {
                        recordFound.setEmail(userDTO.email());
                    }

                    if (userDTO.password() != null && !userDTO.password().isEmpty()) {
                        recordFound.setPassword(passwordEncoder.encode(userDTO.password()));  // Codifica a senha
                    }

                    return userMapper.toDTO(userRepository.save(recordFound));  // Salva e retorna o usuário atualizado
                })
                .orElseThrow(() -> new RecordNotFoundException(id));  // Usuário não encontrado
    }

    /**
     * Exclui um usuário pelo ID.
     * @param id ID do usuário
     * @throws RecordNotFoundException Se o usuário não for encontrado
     */
    public void deleteUser(@NotNull @Positive Long id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));  // Exclui o usuário ou lança exceção
    }
}
