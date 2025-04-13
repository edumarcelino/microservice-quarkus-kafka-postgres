package br.com.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class PostagemResponseDTO {

    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataPublicacao;

    private CategoriaResponseDTO categoria;
    private List<TagResponseDTO> tags;
    private UsuarioResponseDTO autor;

    private ImagemResponseDTO imagemPrincipal;
    private ImagemResponseDTO imagemMiniatura;

    public PostagemResponseDTO() {
    }

    public PostagemResponseDTO(Long id, String titulo, String conteudo, LocalDateTime dataPublicacao,
            CategoriaResponseDTO categoria, List<TagResponseDTO> tags, UsuarioResponseDTO autor,
            ImagemResponseDTO imagemPrincipal, ImagemResponseDTO imagemMiniatura) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.tags = tags;
        this.autor = autor;
        this.imagemPrincipal = imagemPrincipal;
        this.imagemMiniatura = imagemMiniatura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }

    public List<TagResponseDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseDTO> tags) {
        this.tags = tags;
    }

    public UsuarioResponseDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioResponseDTO autor) {
        this.autor = autor;
    }

    public ImagemResponseDTO getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(ImagemResponseDTO imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public ImagemResponseDTO getImagemMiniatura() {
        return imagemMiniatura;
    }

    public void setImagemMiniatura(ImagemResponseDTO imagemMiniatura) {
        this.imagemMiniatura = imagemMiniatura;
    }

}