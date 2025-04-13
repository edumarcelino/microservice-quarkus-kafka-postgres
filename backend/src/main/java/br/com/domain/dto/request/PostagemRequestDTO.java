package br.com.domain.dto.request;

import org.jboss.resteasy.reactive.RestForm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostagemRequestDTO {

    @NotBlank(message = "O título da postagem é obrigatório.")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.")
    @RestForm
    private String titulo;

    @NotBlank(message = "O conteúdo da postagem é obrigatório.")
    @RestForm
    private String conteudo;

    @RestForm
    private Long categoriaId;

    @RestForm
    private Long autorId;

    @RestForm
    private Long[] tagsId;

    @RestForm
    private Long imagemPrincipalId; // ID da imagem de topo

    @RestForm
    private Long imagemMiniaturaId; // ID da miniatura

    public PostagemRequestDTO() {
    }

    public PostagemRequestDTO(
            @NotBlank(message = "O título da postagem é obrigatório.") @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.") String titulo,
            @NotBlank(message = "O conteúdo da postagem é obrigatório.") String conteudo, Long categoriaId,
            Long autorId, Long[] tagsId, Long imagemPrincipalId, Long imagemMiniaturaId) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoriaId = categoriaId;
        this.autorId = autorId;
        this.tagsId = tagsId;
        this.imagemPrincipalId = imagemPrincipalId;
        this.imagemMiniaturaId = imagemMiniaturaId;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public Long[] getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long[] tagsId) {
        this.tagsId = tagsId;
    }

    public Long getImagemPrincipalId() {
        return imagemPrincipalId;
    }

    public void setImagemPrincipalId(Long imagemPrincipalId) {
        this.imagemPrincipalId = imagemPrincipalId;
    }

    public Long getImagemMiniaturaId() {
        return imagemMiniaturaId;
    }

    public void setImagemMiniaturaId(Long imagemMiniaturaId) {
        this.imagemMiniaturaId = imagemMiniaturaId;
    }

    // Getters, Setters, Construtores...

}