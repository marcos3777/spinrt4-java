package br.com.fiap.mechai.api;

import br.com.fiap.mechai.dao.ClientDAO;
import br.com.fiap.mechai.dao.UserDAO;
import br.com.fiap.mechai.dto.ResponseMessage;

import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("auth")
public class AuthResource {

    private UserDAO userDAO = new UserDAO();
    private ClientDAO clientDAO = new ClientDAO(); // Adicione esta linha

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User loginUser) {
        try {
            System.out.println("Tentativa de login para o usuário: " + loginUser.getLogin());

            // Verifica se o usuário existe no banco de dados
            User user = userDAO.findByLogin(loginUser.getLogin());

            if (user != null && user.getSenhaHash().equals(loginUser.getSenhaHash())) {
                // Obter o objeto Client associado
                Client client = clientDAO.readClient(user.getIdCliente());

                if (client != null) {
                    System.out.println("Login bem-sucedido para o cliente: " + client.getNome());
                    return Response.status(Response.Status.OK).entity(client).build();
                } else {
                    System.out.println("Cliente não encontrado para o usuário: " + user.getLogin());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Cliente não encontrado")
                            .build();
                }
            } else {
                System.out.println("Login ou senha inválidos para o usuário: " + loginUser.getLogin());
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Login ou senha inválidos")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a solicitação: " + e.getMessage())
                    .build();
        }
    }
}

