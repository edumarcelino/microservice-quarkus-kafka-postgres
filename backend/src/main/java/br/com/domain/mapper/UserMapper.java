package br.com.domain.mapper;

import br.com.domain.dto.UserOperationDTO;
import br.com.domain.dto.request.UserRequestDTO;
import br.com.domain.dto.response.UserResponseDTO;
import br.com.domain.model.User;
import jakarta.json.JsonObject;

public class UserMapper {

    // Mapeia de UserRequestDTO para User (entidade)
    public static User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }

    // Mapeia de User (entidade) para UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.id);
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

    // Mapeia de User para UserOperationDTO
    public static UserOperationDTO toOperationDTO(User user, String operation) {
        return new UserOperationDTO(operation, user);
    }

    // Mapeia de UserOperationDTO para User
    public static User toEntity(UserOperationDTO userOperationDTO) {
        return userOperationDTO.getUser();
    }

    public static User toEntity(JsonObject jsonObject) {
        User user = new User();

        if (jsonObject.containsKey("id")) {
            user.id = jsonObject.getJsonNumber("id").longValue(); 
        }
        
        user.setUsername(jsonObject.getString("username"));
        
        user.setEmail(jsonObject.getString("email"));
        
        user.setPassword(jsonObject.getString("password"));
        
        return user;
    }
}
