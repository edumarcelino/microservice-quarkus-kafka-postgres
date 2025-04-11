package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comentarios")
public class Comentario extends PanacheEntity {

    @NotBlank(message = "O conteúdo do comentário é obrigatório.")
    @Size(min = 3, max = 500, message = "O comentário deve ter entre 3 e 500 caracteres.")
    @Column(nullable = false, length = 500)
    public String conteudo;

    @Column(nullable = false)
    public LocalDateTime dataComentario = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "postagem_id")
    public Postagem postagem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

    public Comentario() {
    }

    public Comentario(String conteudo, Postagem postagem, Usuario usuario) {
        this.conteudo = conteudo;
        this.postagem = postagem;
        this.usuario = usuario;
        this.dataComentario = LocalDateTime.now();
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Comentario))
            return false;
        Comentario comentario = (Comentario) o;
        return Objects.equals(id, comentario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
