package br.com.fiap.mechai.api;

import br.com.fiap.mechai.dao.CarroDAO;
import br.com.fiap.mechai.model.Carro;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("carros")
public class CarroResource {

    private CarroDAO carroDAO = new CarroDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCarro(Carro carro) {
        try {
            int idCarro = carroDAO.createCarro(carro);
            if (idCarro > 0) {
                carro.setIdCarro(idCarro);
                return Response.status(Response.Status.CREATED).entity(carro).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Falha ao criar carro.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar carro: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCarros() {
        try {
            List<Carro> carros = carroDAO.readAllCarros();
            return Response.status(Response.Status.OK).entity(carros).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter carros: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarroById(@PathParam("id") int id) {
        try {
            Carro carro = carroDAO.readCarro(id);
            if (carro != null) {
                return Response.status(Response.Status.OK).entity(carro).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Carro não encontrado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter carro: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCarro(@PathParam("id") int id, Carro carro) {
        try {
            carro.setIdCarro(id);
            carroDAO.updateCarro(carro);
            return Response.status(Response.Status.OK).entity(carro).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar carro: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCarro(@PathParam("id") int id) {
        try {
            carroDAO.deleteCarro(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar carro: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarrosByCliente(@PathParam("idCliente") int idCliente) {
        try {
            List<Carro> carros = carroDAO.readCarrosByCliente(idCliente);
            return Response.status(Response.Status.OK).entity(carros).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter carros: " + e.getMessage()).build();
        }
    }
}
