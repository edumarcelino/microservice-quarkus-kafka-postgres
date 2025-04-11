package br.com.domain.mapper;

import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.CategoriaResponseDTO;
import br.com.domain.dto.response.PostagemResponseDTO;
import br.com.domain.dto.response.TagResponseDTO;
import br.com.domain.dto.response.UsuarioResponseDTO;
import br.com.domain.model.Categoria;
import br.com.domain.model.Postagem;
import br.com.domain.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class PostagemMapper {

    // Mapeia de Postagem (entidade) para PostagemResponseDTO
    public static PostagemResponseDTO toResponseDTO(Postagem postagem) {
        CategoriaResponseDTO categoriaDTO = CategoriaMapper.toResponseDTO(postagem.categoria);

        UsuarioResponseDTO usuarioDTO = UsuarioMapper.toResponseDTO(postagem.autor);


        List<TagResponseDTO> tagsDTO = postagem.tags.stream()
                .map(TagMapper::toResponseDTO)
                .collect(Collectors.toList());

        return new PostagemResponseDTO(
                postagem.id,
                postagem.getTitulo(),
                postagem.getConteudo(),
                postagem.getDataPublicacao(),
                categoriaDTO,
                tagsDTO,
                usuarioDTO);
    }

    // Mapeia de PostagemRequestDTO para Postagem (entidade)
    public static Postagem toEntity(PostagemRequestDTO dto, Categoria categoria, List<Tag> tags) {
        Postagem postagem = new Postagem();
        postagem.setTitulo(dto.getTitulo());
        postagem.setConteudo(dto.getConteudo());
        postagem.setCategoria(categoria);
        postagem.setTags(tags);
        return postagem;
    }
}