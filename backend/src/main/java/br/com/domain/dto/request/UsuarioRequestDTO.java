package br.com.domain.dto.request;

public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String avatarUrl;
    private Boolean preferenciaNotificacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getPreferenciaNotificacao() {
        return preferenciaNotificacao;
    }

    public void setPreferenciaNotificacao(Boolean preferenciaNotificacao) {
        this.preferenciaNotificacao = preferenciaNotificacao;
    }
}
