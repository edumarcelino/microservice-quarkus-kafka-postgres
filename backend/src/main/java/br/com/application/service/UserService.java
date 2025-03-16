package br.com.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import br.com.application.messaging.UserProducer;
import br.com.common.exception.InvalidEmailException;
import br.com.common.exception.UserNotFoundException;
import br.com.common.exception.WeakPasswordException;
import br.com.domain.dto.UserOperationDTO;
import br.com.domain.dto.UserRequestDTO;
import br.com.domain.dto.UserResponseDTO;
import br.com.domain.mapper.UserMapper;
import br.com.domain.model.User;
import br.com.domain.repository.UserRepository;
import br.com.domain.model.enums.OperationType;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    LogService logService;

    @Inject
    UserProducer userProducer;

    @Inject
    Event<UserOperationDTO> userOperationEventDTO;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public List<UserResponseDTO> getAllUsers() {
        logService.logInfo("Buscando todos os usuários", OperationType.GET_USER);
        List<User> users = userRepository.listAll();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        logService.logInfo("Buscando usuário por ID: " + id, OperationType.GET_USER);
        User user = userRepository.findById(id);
        if (user == null) {
            logService.logWarn("Usuário com ID " + id + " não encontrado", OperationType.GET_USER);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return UserMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        try {
            validateEmail(userRequestDTO.getEmail());
            validatePassword(userRequestDTO.getPassword());

            User user = UserMapper.toEntity(userRequestDTO);
            userRepository.persist(user);

            logService.logInfo("Usuário criado com sucesso: " + user.id, OperationType.CREATE_USER);

            // Dispara evento para ser processado após commit e informando que é uma
            // inserção
            userOperationEventDTO.fire(new UserOperationDTO("CREATE", user));

            return UserMapper.toResponseDTO(user);
        } catch (Exception e) {
            logService.logError("Erro ao criar usuário", e, OperationType.CREATE_USER);
            throw e;
        }
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        try {
            logService.logInfo("Atualizando usuário com ID: " + id, OperationType.UPDATE_USER);
            User existingUser = userRepository.findByIdOptional(id)
                    .orElseThrow(() -> {
                        logService.logWarn("Usuário com ID " + id + " não encontrado", OperationType.UPDATE_USER);
                        return new UserNotFoundException("User with ID " + id + " not found");
                    });

            existingUser.setUsername(userRequestDTO.getUsername());
            existingUser.setEmail(userRequestDTO.getEmail());
            existingUser.setPassword(userRequestDTO.getPassword());

            userRepository.persist(existingUser);

            logService.logInfo("Usuário atualizado com sucesso: " + id, OperationType.UPDATE_USER);

            // Dispara evento para ser processado após commit e informando que é um update
            userOperationEventDTO.fire(new UserOperationDTO("UPDATE", existingUser));

            return UserMapper.toResponseDTO(existingUser);
        } catch (Exception e) {
            logService.logError("Erro ao atualizar usuário com ID: " + id, e, OperationType.UPDATE_USER);
            throw e;
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            logService.logInfo("Deletando usuário com ID: " + id, OperationType.DELETE_USER);
            User user = userRepository.findByIdOptional(id)
                    .orElseThrow(() -> {
                        logService.logWarn("Usuário com ID " + id + " não encontrado", OperationType.DELETE_USER);
                        return new UserNotFoundException("User with ID " + id + " not found");
                    });

            userRepository.delete(user);

            logService.logInfo("Usuário deletado com sucesso: " + id, OperationType.DELETE_USER);

            // Dispara evento informando que é uma deleção
            userOperationEventDTO.fire(new UserOperationDTO("DELETE", user));

        } catch (Exception e) {
            logService.logError("Erro ao deletar usuário com ID: " + id, e, OperationType.DELETE_USER);
            throw e;
        }
    }

    public void sendUserEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) UserOperationDTO userEventDTO) {
        userProducer.sendUserToKafka(userEventDTO);
    }

    private void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new WeakPasswordException(
                    "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }
}