package br.com.fiap.mechai.dto;

import br.com.fiap.mechai.model.Client;

public class ClientRequestDTO {
    private Client client;
    private String senhaHash;

    // Getters e Setters
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
}
