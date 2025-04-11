package br.com.application.rest.portal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.stream.Collectors;
import br.com.application.service.TagService;
import br.com.domain.dto.response.TagResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/portal/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PortalTagResource {

    @Inject
    TagService tagService;

    // Endpoints Públicos
    @GET
    public Response listarTags() {
        List<TagResponseDTO> tags = tagService.getAllTags()
                .stream()
                .collect(Collectors.toList());
        return Response.ok(tags).build();
    }

    @GET
    @Path("/{tagId}")
    public Response getTagConteudo(@PathParam("tagId") Long tagId) {

        TagResponseDTO tagResponseDTO = tagService.getTagById(tagId);

        if (tagResponseDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag não encontrada").build();
        }

        return Response.ok(tagResponseDTO).build();
    }
}