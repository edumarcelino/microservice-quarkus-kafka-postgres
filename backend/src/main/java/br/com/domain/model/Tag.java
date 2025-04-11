package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends PanacheEntity {

    @NotBlank(message = "O nome da tag é obrigatório.")
    @Size(min = 2, max = 30, message = "O nome da tag deve ter entre 2 e 30 caracteres.")
    @Column(unique = true, nullable = false, length = 30)
    public String nome;

    @ManyToMany(mappedBy = "tags")
    public Set<Postagem> postagens = new HashSet<>();

    public Tag() {
    }

    public Tag(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
