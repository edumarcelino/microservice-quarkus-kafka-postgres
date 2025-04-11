package br.com.application.rest.login;

import java.time.Duration;

import br.com.application.service.UsuarioService;
import br.com.common.util.TokenUtils;
import br.com.domain.dto.request.LoginRequestDTO;
import br.com.domain.dto.response.LoginResponseDTO;
import br.com.domain.dto.response.UsuarioResponseDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UsuarioService usuarioService;

    private final Duration TOKEN_DURATION = Duration.ofHours(2);
    private final String ISSUER = "http://www.gamesporgamers.com.br"; // altere conforme necessário

    @POST
    public Response login(LoginRequestDTO request) {
        System.out.println("🔐 Tentativa de login com email: " + request.email);

        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.buscarPorEmailESenha(
                request.email,
                request.password
            );

            String token = TokenUtils.generateToken(
                usuarioResponseDTO.getEmail(),
                usuarioResponseDTO.getRole(), // apenas um role
                TOKEN_DURATION,
                ISSUER
            );

            return Response.ok(new LoginResponseDTO(token)).build();

        } catch (Exception e) {
            System.err.println("❌ Erro no login: " + e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("Email ou senha inválidos")
                           .build();
        }
    }
}
