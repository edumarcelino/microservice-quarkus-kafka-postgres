package br.com.domain.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Comentario extends PanacheEntity {

    @NotBlank(message = "O autor é obrigatório.")
    @Size(max = 60, message = "O nome do autor deve ter no máximo 60 caracteres.")
    @Column(nullable = false, length = 60)
    public String autor;

    @Email(message = "E-mail inválido.")
    @Size(max = 100)
    @Column(length = 100)
    public String email;

    @NotBlank(message = "O conteúdo do comentário é obrigatório.")
    @Size(min = 3, max = 500, message = "O comentário deve ter entre 3 e 500 caracteres.")
    @Column(nullable = false, length = 500)
    public String conteudo;

    @Column(nullable = false)
    public LocalDateTime dataComentario = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "postagem_id")
    public Postagem postagem;

    public Comentario() {
    }

    public Comentario(String autor, String conteudo, Postagem postagem) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.postagem = postagem;
    }
}
