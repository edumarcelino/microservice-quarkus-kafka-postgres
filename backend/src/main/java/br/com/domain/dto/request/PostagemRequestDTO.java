package br.com.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostagemRequestDTO {

    @NotBlank(message = "O título da postagem é obrigatório.")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.")
    private String titulo;

    @NotBlank(message = "O conteúdo da postagem é obrigatório.")
    private String conteudo;

    private Long categoriaId;

    private Long autorId; // ID do autor da postagem

    private Long[] tagsId; // IDs das tags associadas à postagem

    // Construtores
    public PostagemRequestDTO() {
    }

    public PostagemRequestDTO(String titulo, String conteudo, Long categoriaId, Long[] tagsId, Long autorId) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoriaId = categoriaId;
        this.tagsId = tagsId;
        this.autorId = autorId;
    }

    // Getters e Setters
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

    public Long[] getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long[] tagsId) {
        this.tagsId = tagsId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    
}
