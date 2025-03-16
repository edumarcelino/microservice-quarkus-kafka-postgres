package br.com.application.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.reactive.messaging.kafka.Record;
import br.com.common.exception.UserNotFoundException;
import br.com.domain.mapper.UserMapper;
import br.com.domain.model.User;
import br.com.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@ApplicationScoped
public class UserConsumer {

    @Inject
    UserRepository userRepository;

    @Incoming("users-in")
    public void consumeUserEvent(Record<String, String> record) {

        String userJsonString = record.value();
        JsonObject jsonObject = Json.createReader(new java.io.StringReader(userJsonString)).readObject();

        String operation = jsonObject.getString("operation");
        User user = UserMapper.toEntity(jsonObject);

        switch (operation) {
            case "CREATE":
                // userRepository.persist(user);
                System.out.println("CREATED " + user);
                break;
            case "UPDATE":
                User existingUser = userRepository.findByIdOptional(user.id)
                        .orElseThrow(() -> new UserNotFoundException("User with ID " + user.id +
                                " not found"));
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                userRepository.persist(existingUser);
                break;
            case "DELETE":
                userRepository.deleteById(user.id);
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }

    }
}