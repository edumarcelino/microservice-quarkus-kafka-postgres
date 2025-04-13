package br.com.domain.dto.response;

public class ImagemResponseDTO {
    private Long id;
    private String nomeArquivo;
    private String legenda;
    private String url; // "/imagens/{id}"

    public ImagemResponseDTO() {
    }

    public ImagemResponseDTO(Long id, String nomeArquivo, String legenda, String url) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.legenda = legenda;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}