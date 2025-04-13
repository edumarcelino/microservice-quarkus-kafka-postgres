package br.com.application.rest.admin;

import br.com.application.service.PostagemService;
import br.com.domain.dto.request.PostagemRequestDTO;
import br.com.domain.dto.response.PostagemResponseDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/admin/postagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
// @RolesAllowed("admin")
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

    // Método para criar a postagem com upload de imagens
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response criar(
            @RestForm String titulo,
            @RestForm String conteudo,
            @RestForm Long categoriaId,
            @RestForm Long autorId,
            @RestForm List<Long> tagsId,
            @RestForm Long imagemPrincipalId,
            @RestForm Long imagemMiniaturaId,
            @RestForm List<FileUpload> imagens) {

        // Monte o DTO manualmente, se quiser
        PostagemRequestDTO dto = new PostagemRequestDTO(
                titulo, conteudo, categoriaId, autorId,
                tagsId != null ? tagsId.toArray(new Long[0]) : null,
                imagemPrincipalId, imagemMiniaturaId);

        // Chama o serviço de postagem, passando o DTO, token e imagens
        PostagemResponseDTO nova = postagemService.criarComUpload(dto, imagens);

        // Retorna a resposta com o status CREATED (201)
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response atualizar(@RestForm("id") Long id, @RestForm String titulo,
            @RestForm String conteudo,
            @RestForm Long categoriaId,
            @RestForm Long autorId,
            @RestForm List<Long> tagsId,
            @RestForm Long imagemPrincipalId,
            @RestForm Long imagemMiniaturaId,
            @RestForm List<FileUpload> imagens) {

        // Monte o DTO manualmente, se quiser
        PostagemRequestDTO dto = new PostagemRequestDTO(
                titulo, conteudo, categoriaId, autorId,
                tagsId != null ? tagsId.toArray(new Long[0]) : null,
                imagemPrincipalId, imagemMiniaturaId);

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
