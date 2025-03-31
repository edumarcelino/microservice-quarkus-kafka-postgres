package br.com.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import br.com.application.messaging.UserProducer;
import br.com.common.exception.ExceptionLogger;
import br.com.common.exception.InvalidEmailException;
import br.com.common.exception.WeakPasswordException;
import br.com.domain.dto.UserOperationDTO;
import br.com.domain.dto.UserRequestDTO;
import br.com.domain.dto.UserResponseDTO;
import br.com.domain.mapper.UserMapper;
import br.com.domain.model.User;
import br.com.domain.repository.UserRepository;

@ApplicationScoped
@ExceptionLogger  // Ativa o interceptor para capturar exceções e registrar logs
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserProducer userProducer;

    @Inject
    Event<UserOperationDTO> userOperationEventDTO;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.listAll();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    
    public UserResponseDTO getUserById(Long id) {

        return userRepository.findByIdOptional(id)
                .map(UserMapper::toResponseDTO)
                .orElseThrow(() -> new InvalidEmailException("User with ID " + id + " not found"));
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        validateEmail(userRequestDTO.getEmail());
        validatePassword(userRequestDTO.getPassword());

        User user = UserMapper.toEntity(userRequestDTO);
        userRepository.persist(user);

        // Dispara evento após o commit
        userOperationEventDTO.fire(new UserOperationDTO("CREATE", user));

        return UserMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findByIdOptional(id)
                .orElseThrow(() -> new InvalidEmailException("User with ID " + id + " not found"));

        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());

        userRepository.persist(existingUser);

        // Dispara evento para ser processado após commit e informando que é um update
        userOperationEventDTO.fire(new UserOperationDTO("UPDATE", existingUser));

        return UserMapper.toResponseDTO(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdOptional(id)
                .orElseThrow(() -> new InvalidEmailException("User with ID " + id + " not found"));

        userRepository.delete(user);

        // Dispara evento informando que é uma deleção
        userOperationEventDTO.fire(new UserOperationDTO("DELETE", user));
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
