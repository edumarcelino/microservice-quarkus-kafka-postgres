package br.com.common.util;

import br.com.domain.model.Imagem;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProcessadorConteudo {

    public String atualizarPaths(String conteudoOriginal, List<Imagem> imagens) {
        String atualizado = conteudoOriginal;

        for (Imagem img : imagens) {
            if (img.getNomeArquivo() != null && img.getCid() != null) {
                // Substitui o "cid:nomeArquivo" pelo caminho (path) correspondente
                atualizado = atualizado.replace("cid:" + img.getNomeArquivo(), img.getCid());
            }
        }

        return atualizado;
    }
}
