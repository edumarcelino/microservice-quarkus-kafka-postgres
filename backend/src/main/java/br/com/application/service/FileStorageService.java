package br.com.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@ApplicationScoped
public class FileStorageService {

    private static final String BASE_PATH = "upload/imagens/";

    public String salvarImagem(FileUpload file) {
        try {
            String uniqueName = UUID.randomUUID() + "_" + file.fileName();
            Path pasta = Path.of(BASE_PATH);
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }

            Path destino = pasta.resolve(uniqueName);
            Files.copy(file.uploadedFile(), destino);

            return BASE_PATH + uniqueName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + file.fileName(), e);
        }
    }
}
