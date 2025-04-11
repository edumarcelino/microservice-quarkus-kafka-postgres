/*
 br.com.application.messaging;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.Instant;
import java.util.UUID;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import br.com.domain.dto.UserOperationDTO;

@ApplicationScoped
public class UserProducer {

    @Inject
    @Channel("users-out")
    Emitter<Record<String, String>> emitter;

    public void sendUserToKafka(UserOperationDTO userEventDTO) {
        // Converta o objeto User para JSON ou outra representação serializável
        JsonObject userJson = Json.createObjectBuilder()
                .add("id", userEventDTO.getUser().id)
                .add("operation", userEventDTO.getOperation())
                .add("timestampOperacao", Instant.now().toEpochMilli()) // Adiciona timestamp
                .add("messageId", UUID.randomUUID().toString()) // UUID único
                .add("email", userEventDTO.getUser().getEmail())
                .add("password", userEventDTO.getUser().getPassword())
                .add("username", userEventDTO.getUser().getUsername())
                .build();

        System.out.println("USER JSON: " + "ID: " + String.valueOf(userEventDTO.getUser().id) + userJson);

        // Enviar o registro para o Kafka, com a chave sendo o id do usuário e o valor
        // sendo o JSON como string
        String userJsonString = userJson.toString(); // Convertendo o JSON para String
        emitter.send(Record.of(String.valueOf(userEventDTO.getUser().id), userJsonString));
    }

}

 */