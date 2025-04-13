package br.com.domain.mapper;

import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.*;
import br.com.domain.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostagemMapper {

    // Mapeia de Postagem (entidade) para PostagemResponseDTO
    public static PostagemResponseDTO toResponseDTO(Postagem postagem) {
        CategoriaResponseDTO categoriaDTO = CategoriaMapper.toResponseDTO(postagem.getCategoria());
        UsuarioResponseDTO usuarioDTO = UsuarioMapper.toResponseDTO(postagem.getAutor());

        List<TagResponseDTO> tagsDTO = postagem.getTags().stream()
                .map(TagMapper::toResponseDTO)
                .collect(Collectors.toList());

        ImagemResponseDTO imagemPrincipalDTO = postagem.getImagemPrincipal() != null
                ? ImagemMapper.toResponseDTO(postagem.getImagemPrincipal())
                : null;

        ImagemResponseDTO imagemMiniaturaDTO = postagem.getImagemMiniatura() != null
                ? ImagemMapper.toResponseDTO(postagem.getImagemMiniatura())
                : null;

        return new PostagemResponseDTO(
                postagem.id,
                postagem.getTitulo(),
                postagem.getConteudo(),
                postagem.getDataPublicacao(),
                categoriaDTO,
                tagsDTO,
                usuarioDTO,
                imagemPrincipalDTO,
                imagemMiniaturaDTO
        );
    }

    // Mapeia de PostagemRequestDTO para Postagem (entidade)
    public static Postagem toEntity(PostagemRequestDTO dto, Categoria categoria, List<Tag> tags, Usuario autor, Imagem imagemPrincipal, Imagem imagemMiniatura) {
        Postagem postagem = new Postagem();
        postagem.setTitulo(dto.getTitulo());
        postagem.setConteudo(dto.getConteudo());
        postagem.setCategoria(categoria);
        postagem.setTags(tags);
        postagem.setAutor(autor);
        postagem.setImagemPrincipal(imagemPrincipal);
        postagem.setImagemMiniatura(imagemMiniatura);
        return postagem;
    }
}
