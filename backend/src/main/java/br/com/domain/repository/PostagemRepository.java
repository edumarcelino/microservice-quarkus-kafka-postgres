package br.com.domain.repository;

import br.com.domain.model.Postagem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostagemRepository implements PanacheRepository<Postagem> {

    public List<Postagem> findByCategoria(Long categoriaId) {
        return find("categoria.id", categoriaId).list();
    }

    public List<Postagem> findByUsuario(String usuarioId) {
        return find("usuario.id", usuarioId).list();
    }

    public List<Postagem> findByTituloContaining(String titulo) {
        return list("lower(titulo) like ?1", "%" + titulo.toLowerCase() + "%");
    }

    public List<Postagem> findRecentes(int limite) {
        return find("ORDER BY dataPublicacao DESC").page(0, limite).list();
    }
}
