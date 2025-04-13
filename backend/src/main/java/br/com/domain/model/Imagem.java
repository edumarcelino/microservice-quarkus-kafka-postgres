package br.com.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Imagem extends PanacheEntity {

    @NotBlank
    @Column(nullable = false)
    private String nomeArquivo; // Ex: "grafico1.png"

    private String legenda;

    private int largura;

    private int altura;

    private String cid; // usado no conteúdo HTML como src='cid:imagem1'

    @Lob
    @Column(columnDefinition = "TEXT")
    private String base64; // Conteúdo da imagem codificado

    private String path; // Adicionado para armazenar o caminho ou URL da imagem

    public Imagem() {
    }

    public Imagem(String nomeArquivo, String legenda, int largura, int altura, String path) {
        this.nomeArquivo = nomeArquivo;
        this.legenda = legenda;
        this.largura = largura;
        this.altura = altura;
        this.path = path;
    }

    // Getters e Setters

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

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getPath() {
        return path; // Novo método getter para o campo path
    }

    public void setPath(String path) {
        this.path = path; // Novo método setter para o campo path
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Imagem))
            return false;
        Imagem imagem = (Imagem) o;
        return Objects.equals(id, imagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
