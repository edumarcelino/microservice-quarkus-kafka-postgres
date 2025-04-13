package br.com.domain.dto.request;

public class ImagemRequesDTO {

    private String cid; // "imagem1" — corresponde ao usado no src='cid:imagem1'
    private String nomeArquivo; // "grafico1.png"
    private String legenda;
    private String base64; // Conteúdo codificado da imagem

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    // Getters e Setters

}