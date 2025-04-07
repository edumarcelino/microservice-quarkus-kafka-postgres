package br.com.domain.model;

import java.util.Objects;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Tag extends PanacheEntity {

    @NotBlank(message = "O nome da tag é obrigatório.")
    @Size(min = 2, max = 30, message = "O nome da tag deve ter entre 2 e 30 caracteres.")
    @Column(unique = true, nullable = false, length = 30)
    public String nome;

    // Construtor padrão
    public Tag() {
    }

    // Construtor com parâmetros
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*
     * @ManyToMany(mappedBy = "tags")
     * public Set<Postagem> postagens = new HashSet<>();
     */
}