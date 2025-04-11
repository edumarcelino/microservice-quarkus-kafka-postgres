package br.com.application.service;

import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.PostagemResponseDTO;
import br.com.domain.mapper.PostagemMapper;
import br.com.domain.model.Categoria;
import br.com.domain.model.Postagem;
import br.com.domain.model.Tag;
import br.com.domain.repository.CategoriaRepository;
import br.com.domain.repository.PostagemRepository;
import br.com.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ApplicationScoped
public class PostagemService {

    @Inject
    PostagemRepository postagemRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    TagRepository tagRepository;

    public List<PostagemResponseDTO> listarTodas() {
        return postagemRepository.listAll()
                .stream()
                .map(PostagemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostagemResponseDTO> buscarPorId(Long id) {
        return postagemRepository.findByIdOptional(id)
                .map(PostagemMapper::toResponseDTO);
    }

    public List<PostagemResponseDTO> buscarPorCategoria(Long categoriaId) {
        return postagemRepository.findByCategoria(categoriaId)
                .stream()
                .map(PostagemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PostagemResponseDTO> buscarPorUsuario(String usuarioId) {
        return postagemRepository.findByUsuario(usuarioId)
                .stream()
                .map(PostagemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PostagemResponseDTO> buscarPorTitulo(String titulo) {
        return postagemRepository.findByTituloContaining(titulo)
                .stream()
                .map(PostagemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PostagemResponseDTO> listarRecentes(int limite) {
        return postagemRepository.findRecentes(limite)
                .stream()
                .map(PostagemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostagemResponseDTO criar(PostagemRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());

        List<Tag> tags = tagRepository.findByIds(List.of(dto.getTagsId()));

        Postagem postagem = PostagemMapper.toEntity(dto, categoria, tags);
        postagemRepository.persist(postagem);

        return PostagemMapper.toResponseDTO(postagem);
    }

    @Transactional
    public boolean deletar(Long id) {
        return postagemRepository.deleteById(id);
    }

    @Transactional
    public PostagemResponseDTO atualizar(Long id, PostagemRequestDTO dto) {
        Postagem existente = postagemRepository.findById(id);
        if (existente != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());
            
            List<Tag> tags = tagRepository.findByIds(List.of(dto.getTagsId()));

            existente.setTitulo(dto.getTitulo());
            existente.setConteudo(dto.getConteudo());
            existente.setCategoria(categoria);
            existente.setTags(tags);

            return PostagemMapper.toResponseDTO(existente);
        }
        return null;
    }
}
