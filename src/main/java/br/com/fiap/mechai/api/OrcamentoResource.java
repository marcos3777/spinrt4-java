package br.com.fiap.mechai.api;

import br.com.fiap.mechai.dao.OrcamentoDAO;
import br.com.fiap.mechai.model.Orcamento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("orcamentos")
public class OrcamentoResource {

    private OrcamentoDAO orcamentoDAO = new OrcamentoDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrcamento(Orcamento orcamento) {
        try {
            System.out.println("Dados do orçamento recebido:");
            System.out.println("ID Mecanico: " + orcamento.getIdMecanico());
            System.out.println("ID Carro: " + orcamento.getIdCarro());
            System.out.println("ID Cliente: " + orcamento.getIdCliente());
            System.out.println("Valor: " + orcamento.getValor());
            System.out.println("Data Prevista Finalização: " + orcamento.getDtPrevistaFinalizacao());
            System.out.println("Data Finalização: " + orcamento.getDtFinalizacao());
            System.out.println("Tipo Problema: " + orcamento.getTipoProblema());
            System.out.println("Descrição Problema: " + orcamento.getDescricaoProblema());
            System.out.println("Endereço Atual: " + orcamento.getEnderecoAtual());
            System.out.println("Status: " + orcamento.getStatus());
            int idOrcamento = orcamentoDAO.createOrcamento(orcamento);
            if (idOrcamento > 0) {
                orcamento.setIdOrcamento(idOrcamento);
                return Response.status(Response.Status.CREATED).entity(orcamento).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Falha ao criar orçamento.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar orçamento: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrcamentos() {
        try {
            List<Orcamento> orcamentos = orcamentoDAO.readAllOrcamentos();
            return Response.status(Response.Status.OK).entity(orcamentos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter orçamentos: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrcamentoById(@PathParam("id") int id) {
        try {
            Orcamento orcamento = orcamentoDAO.readOrcamento(id);
            if (orcamento != null) {
                return Response.status(Response.Status.OK).entity(orcamento).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Orçamento não encontrado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter orçamento: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrcamento(@PathParam("id") int id, Orcamento orcamento) {
        try {
            orcamento.setIdOrcamento(id);
            boolean updated = orcamentoDAO.updateOrcamento(orcamento);
            if (updated) {
                return Response.status(Response.Status.OK).entity(orcamento).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Orçamento não encontrado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar orçamento: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrcamento(@PathParam("id") int id) {
        try {
            // Implementar lógica de exclusão
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir orçamento: " + e.getMessage()).build();
        }
    }


@GET
@Path("/cliente/{idCliente}")
@Produces(MediaType.APPLICATION_JSON)
public Response getOrcamentosByCliente(@PathParam("idCliente") int idCliente) {
    try {
        List<Orcamento> orcamentos = orcamentoDAO.readOrcamentosByCliente(idCliente);
        if (orcamentos != null && !orcamentos.isEmpty()) {
            return Response.status(Response.Status.OK).entity(orcamentos).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao obter orçamentos do cliente: " + e.getMessage()).build();
    }
}

}
