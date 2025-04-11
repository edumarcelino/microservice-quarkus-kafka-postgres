package br.com.application.rest.portal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.stream.Collectors;

import br.com.application.service.CategoriaService;
import br.com.domain.dto.response.CategoriaResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/portal/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PortalCategoriaResource {

    @Inject
    CategoriaService categoriaService;

    // Endpoints Públicos
    @GET
    public Response listarCategorias() {
        List<CategoriaResponseDTO> categorias = categoriaService.getAllCategorias()
                .stream()
                .collect(Collectors.toList());
        return Response.ok(categorias).build();
    }

    @GET
    @Path("/{categoriaId}")
    public Response getCategoriaConteudo(@PathParam("categoriaId") Long categoriaId) {
        CategoriaResponseDTO categoria = categoriaService.getCategoriaById(categoriaId);

        if (categoria == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada").build();
        }

        return Response.ok(categoria).build();
    }
}