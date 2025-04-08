package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Postagem extends PanacheEntity {

    @NotBlank(message = "O título da postagem é obrigatório.")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    public String titulo;

    @NotBlank(message = "O conteúdo da postagem é obrigatório.")
    @Lob
    @Column(nullable = false)
    public String conteudo;

    @Column(nullable = false)
    public LocalDateTime dataPublicacao = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    public Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "postagem_tag",
        joinColumns = @JoinColumn(name = "postagem_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    public Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comentario> comentarios;

    public Postagem() {
    }

    public Postagem(String titulo, String conteudo, Categoria categoria) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoria = categoria;
    }
}
