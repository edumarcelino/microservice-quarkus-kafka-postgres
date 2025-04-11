package br.com.application.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

@Path("/secure")
public class SecuredResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN") // Apenas usuários com a role "admin"
    public String adminOnly() {
        return "Bem-vindo, " + jwt.getName() + "! Você é um administrador." + jwt.getExpirationTime() + " - "
                + jwt.getIssuedAtTime();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ "ADMIN", "USER" }) // Admins e Users podem acessar
    public String userAccess() {
        return "Olá, " + jwt.getName() + "! Você tem acesso como usuário.";
    }

    @GET
    @Path("/public")
    @Produces(MediaType.TEXT_PLAIN)
    public String publicAccess() {
        return "Este é um endpoint público!";
    }
}
