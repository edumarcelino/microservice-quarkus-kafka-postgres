package br.com.application.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import br.com.application.service.UserService;
import br.com.common.exception.InvalidEmailException;
import br.com.common.exception.UserNotFoundException;
import br.com.common.exception.WeakPasswordException;
import br.com.domain.dto.UserRequestDTO;
import br.com.domain.dto.UserResponseDTO;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    // Método para obter todos os usuários
    @GET
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers(); // Retorna uma lista de UserResponseDTO
    }

    // Método para obter um usuário específico por ID
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            UserResponseDTO userResponseDTO = userService.getUserById(id);
            return Response.ok(userResponseDTO).build(); // Retorna o DTO de resposta
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Retorna 404 se não encontrado
        }
    }

    // Método para criar um novo usuário
    @POST
    public Response createUser(UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return Response.status(Response.Status.CREATED)
                .entity(createdUser) // Retorna o UserResponseDTO com o ID gerado
                .build();
    }

    // Método para atualizar um usuário existente
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, UserRequestDTO userRequestDTO) {
        try {
            // Chama o método de serviço que agora retorna um UserResponseDTO
            UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);

            // Retorna a resposta com o UserResponseDTO atualizado
            return Response.ok(updatedUser).build(); // Retorna 200 OK com o DTO atualizado
        } catch (UserNotFoundException e) {
            // Retorna 404 caso o usuário não seja encontrado
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (InvalidEmailException | WeakPasswordException e) {
            // Retorna 400 caso haja erros de validação
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Método para deletar um usuário
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build(); // Retorna 204 quando deletado com sucesso
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Retorna 404 se não encontrado
        }
    }
}
