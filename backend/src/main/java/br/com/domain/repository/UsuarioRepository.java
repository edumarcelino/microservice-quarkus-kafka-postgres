package br.com.domain.repository;


import br.com.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByEmail(String email) {
        return find("email", email).firstResult();
    }
}