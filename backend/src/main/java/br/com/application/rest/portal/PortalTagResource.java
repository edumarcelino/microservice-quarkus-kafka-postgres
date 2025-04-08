package br.com.application.rest.portal;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.domain.dto.response.TagResponseDTO;
import br.com.domain.mapper.TagMapper;
import br.com.domain.model.Tag;
import br.com.domain.repository.TagRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/portal/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PortalTagResource {

    @Inject
    TagRepository tagRepository;

    // Endpoints Públicos
    @GET
    public Response listarTags() {
        List<TagResponseDTO> tags = tagRepository.listAll()
                .stream()
                .map(TagMapper::toResponseDTO)
                .collect(Collectors.toList());
        return Response.ok(tags).build();
    }

    @GET
    @Path("/{tagId}")
    public Response getTagConteudo(@PathParam("tagId") Long tagId) {
        Optional<Tag> tagOpt = tagRepository.findByIdOptional(tagId);
        if (tagOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag não encontrada").build();
        }

        return Response.ok(TagMapper.toResponseDTO(tagOpt.get())).build();
    }
}