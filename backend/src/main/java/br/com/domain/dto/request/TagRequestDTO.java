package br.com.domain.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagRequestDTO {

    @NotBlank(message = "O nome da tag é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da tag deve ter entre 3 e 100 caracteres")
    @Column(unique = true, nullable = false)
    public String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

