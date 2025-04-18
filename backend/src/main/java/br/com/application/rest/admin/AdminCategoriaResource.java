package br.com.application.rest.admin;

import java.util.List;
import java.util.stream.Collectors;

import br.com.application.service.CategoriaService;
import br.com.domain.dto.request.CategoriaRequestDTO;
import br.com.domain.dto.response.CategoriaResponseDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/api/admin/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminCategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN") 
    public Response listarCategorias() {
        List<CategoriaResponseDTO> categorias = categoriaService.getAllCategorias()
                .stream()
                .collect(Collectors.toList());
        return Response.ok(categorias).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN") 
    public Response create(@Valid CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO responseDTO = categoriaService.create(categoriaRequestDTO);
        return Response.status(Status.CREATED).entity(responseDTO).build();
    }

    @PUT
    @Path("/{categoriaId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN") 
    public Response editarCategoria(@PathParam("categoriaId") Long categoriaId,
            @Valid CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.updateCategoria(categoriaId, categoriaRequestDTO);
        return Response.ok(categoriaAtualizada).build();
    }

    @DELETE
    @Path("/{categoriaId}")
    @RolesAllowed("ADMIN") 
    public Response deletarCategoria(@PathParam("categoriaId") Long categoriaId) {
        boolean deleted = categoriaService.deleteCategoria(categoriaId);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria não encontrada").build();
        }

        return Response.noContent().build(); // Retorna 204 No Content sem corpo
    }
}
