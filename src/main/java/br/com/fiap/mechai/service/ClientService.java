package br.com.fiap.mechai.service;

import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.dao.ClientDAO;

import java.util.List;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();

    public void registerClient(Client client) {
        clientDAO.createClient(client);
    }

    public Client getClientById(int id) {
        return clientDAO.readClient(id);
    }

    public List<Client> getAllClients() {
        return clientDAO.readAllClients();
    }

    public void updateClient(Client client) {
        clientDAO.updateClient(client);
    }

    public void deleteClient(int id) {
        clientDAO.deleteClient(id);
    }
}