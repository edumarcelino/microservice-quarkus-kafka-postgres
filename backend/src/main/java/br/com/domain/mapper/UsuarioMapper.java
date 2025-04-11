package br.com.domain.mapper;

import br.com.domain.dto.request.UsuarioRequestDTO;
import br.com.domain.dto.response.UsuarioResponseDTO;
import br.com.domain.model.Usuario;

public class UsuarioMapper {

    // Mapeia de UsuarioRequestDTO para Usuario (entidade)
    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(dto.getSenha()); // Obs: criptografar antes de persistir
        usuario.setAvatarUrl(dto.getAvatarUrl());
        usuario.setPreferenciaNotificacao(dto.getPreferenciaNotificacao());
        return usuario;
    }

    // Mapeia de Usuario (entidade) para UsuarioResponseDTO
    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.id);
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setAvatarUrl(usuario.getAvatarUrl());
        dto.setPreferenciaNotificacao(usuario.getPreferenciaNotificacao());
        dto.setRole(usuario.getRole());
        return dto;
    }
}
