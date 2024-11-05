package br.com.fiap.mechai.service;

import br.com.fiap.mechai.dao.ClientDAO;
import br.com.fiap.mechai.dao.UserDAO;
import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.model.User;

public class RegistrationService {

    private final ClientDAO clientDAO = new ClientDAO();
    private final UserDAO userDAO = new UserDAO();

    public void registerClientWithUser(Client client, String senhaHash, String tipoUsuario) {
        // 1. Cadastro do cliente e obtenção do ID gerado automaticamente
        int clientId = clientDAO.createClient(client);

        if (clientId > 0) {
            // 2. Cadastro do usuário associado ao cliente
            User user = new User();
            user.setIdCliente(clientId);  // Associando o id_cliente gerado automaticamente
            user.setLogin(client.getLoginCliente());  // Usa o mesmo login_cliente
            user.setSenhaHash(senhaHash);
            user.setTipoUsuario(tipoUsuario);

            userDAO.createUser(user);
        } else {
            System.out.println("Failed to register client. User creation aborted.");
        }
    }
}
