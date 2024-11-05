package br.com.fiap.mechai.dao;

import br.com.fiap.mechai.model.Client;
import br.com.fiap.mechai.model.User;
import br.com.fiap.mechai.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public int createClient(Client client) {
        String sql = "INSERT INTO TB_MECHAI_CLIENTE (cpf, nome, endereco, telefone, email, login_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        int clientId = -1; // Declaração da variável clientId

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] {"id_cliente"})) {

            stmt.setString(1, client.getCpf());
            stmt.setString(2, client.getNome());
            stmt.setString(3, client.getEndereco());
            stmt.setString(4, client.getTelefone());
            stmt.setString(5, client.getEmail());
            stmt.setString(6, client.getLoginCliente());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Tentativa de recuperar o ID gerado usando uma consulta SELECT adicional
                try (PreparedStatement selectStmt = conn.prepareStatement("SELECT id_cliente FROM TB_MECHAI_CLIENTE WHERE ROWNUM = 1 ORDER BY id_cliente DESC");
                     ResultSet rs = selectStmt.executeQuery()) {

                    if (rs.next()) {
                        clientId = rs.getInt("id_cliente");
                        System.out.println("Client ID gerado: " + clientId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientId; // Retorna o clientId
    }





    public Client readClient(int idCliente) {
        String sql = "SELECT * FROM TB_MECHAI_CLIENTE WHERE id_cliente = ?";
        Client client = new Client();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client.setIdCliente(rs.getInt("id_cliente"));
                    client.setCpf(rs.getString("cpf"));
                    client.setNome(rs.getString("nome"));
                    client.setEndereco(rs.getString("endereco"));
                    client.setTelefone(rs.getString("telefone"));
                    client.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    public List<Client> readAllClients() {
        String sql = "SELECT * FROM TB_MECHAI_CLIENTE";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setIdCliente(rs.getInt("id_cliente"));
                client.setCpf(rs.getString("cpf"));
                client.setNome(rs.getString("nome"));
                client.setEndereco(rs.getString("endereco"));
                client.setTelefone(rs.getString("telefone"));
                client.setEmail(rs.getString("email"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

    public void updateClient(Client client) {
        String sql = "UPDATE TB_MECHAI_CLIENTE SET nome = ?, endereco = ?, telefone = ?, email = ?, cpf = ? WHERE id_cliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNome());
            stmt.setString(2, client.getEndereco());
            stmt.setString(3, client.getTelefone());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getCpf());
            stmt.setInt(6, client.getIdCliente());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client updated successfully!");
            } else {
                System.out.println("No client found with the specified ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int idCliente) {
        String sql = "DELETE FROM TB_MECHAI_CLIENTE WHERE id_cliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            stmt.executeUpdate();
            System.out.println("Client deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

