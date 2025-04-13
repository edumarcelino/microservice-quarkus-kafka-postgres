package br.com.application.service;

import br.com.common.util.ProcessadorConteudo;
import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.PostagemResponseDTO;
import br.com.domain.mapper.PostagemMapper;
import br.com.domain.model.Categoria;
import br.com.domain.model.Imagem;
import br.com.domain.model.Postagem;
import br.com.domain.model.Tag;
import br.com.domain.model.Usuario;
import br.com.domain.repository.CategoriaRepository;
import br.com.domain.repository.ImagemRepository;
import br.com.domain.repository.PostagemRepository;
import br.com.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@ApplicationScoped
public class PostagemService {

    @Inject
    PostagemRepository postagemRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    ImagemRepository imagemRepository;

    @Inject
    FileStorageService fileStorageService;

    @Inject
    ProcessadorConteudo processadorConteudo;

    @Inject
    JsonWebToken jwt;

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
    public PostagemResponseDTO criarComUpload(PostagemRequestDTO dto, List<FileUpload> imagens) {
        try {

            String token = jwt.getRawToken();

            // 1. Valida e obtém o autor
            Usuario autor = usuarioService.buscarUsuarioPeloToken(token);
            if (autor == null) {
                throw new RuntimeException("Usuário não encontrado");
            }

            // 2. Valida e obtém a categoria
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada");
            }

            // 3. Valida e obtém as tags
            List<Tag> tags = tagRepository.findByIds(List.of(dto.getTagsId()));
            if (tags == null || tags.isEmpty()) {
                throw new RuntimeException("Nenhuma tag válida fornecida");
            }

            // 4. Processa upload das imagens
            List<Imagem> imagensSalvas = salvarImagens(imagens);

            // 5. Processa conteúdo HTML
            String conteudoAtualizado = processadorConteudo.atualizarPaths(dto.getConteudo(), imagensSalvas);

            // 6. Valida imagens principal e miniatura
            Imagem imagemPrincipal = validarEObterImagem(dto.getImagemPrincipalId());
            Imagem imagemMiniatura = validarEObterImagem(dto.getImagemMiniaturaId());

            // 7. Cria a postagem
            Postagem postagem = criarPostagem(dto, conteudoAtualizado, categoria, tags, autor, imagemPrincipal,
                    imagemMiniatura, imagensSalvas);

            return PostagemMapper.toResponseDTO(postagem);
        } catch (Exception e) {
            // Logar o erro adequadamente
            throw new RuntimeException("Falha ao criar postagem: " + e.getMessage(), e);
        }
    }

    public List<Imagem> salvarImagens(List<FileUpload> imagens) {
        return imagens.stream()
                .map(fileUpload -> {
                    try {
                        // Salva o arquivo utilizando o serviço de armazenamento
                        String path = fileStorageService.salvarImagem(fileUpload);

                        // Cria e persiste a entidade Imagem
                        Imagem imagem = new Imagem();
                        imagem.setNomeArquivo(fileUpload.fileName());
                        imagem.setPath(path);
                        imagemRepository.persist(imagem);

                        return imagem;
                    } catch (Exception e) {
                        throw new RuntimeException("Falha ao salvar imagem: " + fileUpload.fileName(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    private Imagem validarEObterImagem(Long imagemId) {
        if (imagemId == null) {
            return null;
        }
        Imagem imagem = imagemRepository.findById(imagemId);
        if (imagem == null) {
            throw new RuntimeException("Imagem não encontrada: " + imagemId);
        }
        return imagem;
    }

    private Postagem criarPostagem(PostagemRequestDTO dto, String conteudo, Categoria categoria,
            List<Tag> tags, Usuario autor, Imagem imagemPrincipal,
            Imagem imagemMiniatura, List<Imagem> imagens) {
        Postagem postagem = PostagemMapper.toEntity(dto, categoria, tags, autor, imagemPrincipal, imagemMiniatura);
        postagem.setConteudo(conteudo);

        postagemRepository.persist(postagem);
        return postagem;
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
