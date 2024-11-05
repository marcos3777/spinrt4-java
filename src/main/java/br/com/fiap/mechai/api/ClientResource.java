package br.com.fiap.mechai.api;

import br.com.fiap.mechai.dao.ClientDAO;
import br.com.fiap.mechai.dao.UserDAO;
import br.com.fiap.mechai.dto.ClientRequestDTO;
import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("clients")
public class ClientResource {

    private ClientDAO clientDAO = new ClientDAO();
    private UserDAO userDAO = new UserDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        try {
            List<Client> clients = clientDAO.readAllClients();
            return Response.status(Response.Status.OK).entity(clients).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@PathParam("id") int id) {
        Client client = clientDAO.readClient(id);

        if (client == null || client.getIdCliente() == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Client not found")
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(client)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") int id, Client client) {
        // Verificar se os campos obrigatórios estão presentes
        if (client.getNome() == null || client.getEndereco() == null || client.getTelefone() == null || client.getEmail() == null || client.getCpf() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Todos os campos obrigatórios devem ser preenchidos.")
                    .build();
        }

        Client existingClient = clientDAO.readClient(id);
        if (existingClient == null || existingClient.getIdCliente() == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Client not found")
                    .build();
        }

        client.setIdCliente(id);
        clientDAO.updateClient(client);
        return Response.status(Response.Status.OK).entity(client).build();
    }

    @POST
    @Path("/cad-client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerClientWithUser(ClientRequestDTO request) {
        try {
            Client client = request.getClient();
            String senhaHash = request.getSenhaHash();

            if (senhaHash == null || senhaHash.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Senha é obrigatória para cadastrar usuário.")
                        .build();
            }

            int clientId = clientDAO.createClient(client);
            System.out.println("teste antes do if");

            if (clientId > 0) {
                User user = new User();
                user.setIdCliente(clientId);
                user.setLogin(client.getLoginCliente());
                user.setSenhaHash(senhaHash);
                user.setTipoUsuario("CLIENTE");
                System.out.println("if clientid");

                try {
                    System.out.println("Dados do Usuário:");
                    System.out.println("ID Cliente: " + user.getIdCliente());
                    System.out.println("Login: " + user.getLogin());
                    System.out.println("Senha Hash: " + user.getSenhaHash());
                    System.out.println("Tipo de Usuário: " + user.getTipoUsuario());
                    userDAO.createUser(user);
                } catch (Exception e) {
                    clientDAO.deleteClient(clientId);
                    e.printStackTrace();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Erro ao criar usuário: " + e.getMessage())
                            .build();
                }
                return Response.status(Response.Status.CREATED).entity(client).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Falha ao registrar cliente").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
