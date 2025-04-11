package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @JoinColumn(name = "categoria_id", nullable = false)
    public Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    public Usuario autor;

    @ManyToMany
    @JoinTable(name = "postagem_tag", joinColumns = @JoinColumn(name = "postagem_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    public List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comentario> comentarios;

    public Postagem() {
    }

    public Postagem(String titulo, String conteudo, Categoria categoria, Usuario autor) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoria = categoria;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Postagem))
            return false;
        Postagem postagem = (Postagem) o;
        return Objects.equals(id, postagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
