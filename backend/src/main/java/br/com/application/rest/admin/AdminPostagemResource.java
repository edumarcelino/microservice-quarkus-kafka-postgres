package br.com.application.rest.admin;

import br.com.application.service.PostagemService;
import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.PostagemResponseDTO;

//import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/admin/postagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RolesAllowed("admin")
public class AdminPostagemResource {

    @Inject
    PostagemService postagemService;

    @GET
    public List<PostagemResponseDTO> listar() {
        return postagemService.listarTodas();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return postagemService.buscarPorId(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Transactional
    public Response criar(PostagemRequestDTO dto) {
        PostagemResponseDTO nova = postagemService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, PostagemRequestDTO dto) {
        PostagemResponseDTO atualizada = postagemService.atualizar(id, dto);
        if (atualizada != null) {
            return Response.ok(atualizada).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        boolean removido = postagemService.deletar(id);
        return removido
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
