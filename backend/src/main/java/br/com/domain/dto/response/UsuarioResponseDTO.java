package br.com.domain.dto.response;

import br.com.domain.model.enums.Role;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String avatarUrl;
    private Boolean preferenciaNotificacao;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
