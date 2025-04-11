package br.com.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import br.com.domain.model.Tag;

@ApplicationScoped
public class TagRepository implements PanacheRepository<Tag> {

    public List<Tag> buscarPorNome(String nome) {
        return find("nome", nome).list();
    }

    public long contarTags() {
        return count();
    }

    public boolean deleteTag(Long id) {
        return deleteById(id);
    }

    public List<Tag> findByIds(List<Long> ids) {
        return find("id in ?1", ids).stream().collect(java.util.stream.Collectors.toList());
    }
}
