package br.com.application.rest.admin;

import java.util.List;
import java.util.stream.Collectors;

import br.com.application.service.TagService;
import br.com.domain.dto.request.TagRequestDTO;
import br.com.domain.dto.response.TagResponseDTO;
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

@Path("/api/admin/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminTagResource {

    @Inject
    TagService tagService;

    @GET
    public Response listarTags() {
        List<TagResponseDTO> tags = tagService.getAllTags()
                .stream()
                .collect(Collectors.toList());
        return Response.ok(tags).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid TagRequestDTO tagRequestDTO) {
        TagResponseDTO responseDTO = tagService.create(tagRequestDTO);
        return Response.status(Status.CREATED).entity(responseDTO).build();
    }

    @PUT
    @Path("/{tagId}")
    public Response editarTag(@PathParam("tagId") Long tagId,
            @Valid TagRequestDTO tagRequestDTO) {
        TagResponseDTO tagAtualizada = tagService.updateTag(tagId, tagRequestDTO);
        return Response.ok(tagAtualizada).build();
    }

    @DELETE
    @Path("/{tagId}")
    public Response deletarTag(@PathParam("tagId") Long tagId) {
        boolean deleted = tagService.deleteTag(tagId);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag não encontrada").build();
        }

        return Response.noContent().build(); // Retorna 204 No Content sem corpo
    }
}
