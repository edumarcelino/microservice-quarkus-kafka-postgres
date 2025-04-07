package br.com.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import br.com.domain.model.Categoria;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    public List<Categoria> buscarPorNome(String nome) {
        return find("nome", nome).list();
    }

    public long contarCategorias() {
        return count();
    }

    public boolean deleteCategoria(Long id) {
        // Retorna true se a categoria foi removida com sucesso
        return deleteById(id);
    }

}