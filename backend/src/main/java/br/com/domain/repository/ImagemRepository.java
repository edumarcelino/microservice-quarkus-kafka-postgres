package br.com.domain.repository;


import br.com.domain.model.Imagem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImagemRepository implements PanacheRepository<Imagem> {

    public Imagem findByNomeArquivo(String nomeArquivo) {
        return find("nomeArquivo", nomeArquivo).firstResult();
    }

    public Imagem findByCid(String cid) {
        return find("cid", cid).firstResult();
    }

}
