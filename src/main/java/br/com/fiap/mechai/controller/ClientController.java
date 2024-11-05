package br.com.fiap.mechai.controller;

import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.service.ClientService;

public class ClientController {
    private ClientService clientService = new ClientService();


    public void registerClient(Client client) {
        clientService.registerClient(client);
    }

    public Client getClient(int idCliente) {
        return clientService.getClientById(idCliente);
    }
}
