package br.com.application.service;

import br.com.common.util.TokenUtils;
import br.com.domain.dto.request.UsuarioRequestDTO;
import br.com.domain.dto.response.UsuarioResponseDTO;
import br.com.domain.mapper.UsuarioMapper;
import br.com.domain.model.Usuario;
import br.com.domain.repository.UsuarioRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TokenUtils tokenUtils;

    public List<UsuarioResponseDTO> listarTodos() {

        return Usuario.listAll(Sort.by("nome"))
                .stream()
                .map(u -> UsuarioMapper.toResponseDTO((Usuario) u))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        String senhaHash = BcryptUtil.bcryptHash(dto.getSenha());
        usuario.setSenhaHash(senhaHash);
        usuario.persist();
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(BcryptUtil.bcryptHash(dto.getSenha()));
        usuario.setAvatarUrl(dto.getAvatarUrl());
        usuario.setPreferenciaNotificacao(dto.getPreferenciaNotificacao());

        return UsuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        usuario.delete();
    }

    public UsuarioResponseDTO buscarPorEmailESenha(String email, String senha) {
        Usuario usuario = Usuario.find("email", email).firstResult();

        if (usuario == null || !BcryptUtil.matches(senha, usuario.getSenhaHash())) {
            throw new RuntimeException("Email ou senha inválidos.");
        }
        return UsuarioMapper.toResponseDTO(usuario);
    }

    // Método para buscar usuário baseado no token
    public Usuario buscarUsuarioPeloToken(String token) throws Exception {
        // Extrai o nome do usuário (ou sub) do token JWT
        String username = tokenUtils.getUsernameFromToken(token);

        // Busca o usuário no banco de dados
        return usuarioRepository.find("username", username).firstResult();
    }

    // Método para validar se o usuário possui uma role específica
    public boolean usuarioTemRole(String token, String role) throws Exception {
        // Obtém as roles do token
        Set<String> roles = tokenUtils.getRolesFromToken(token);
        // Verifica se o usuário tem a role específica
        return roles.contains(role);
    }
}
