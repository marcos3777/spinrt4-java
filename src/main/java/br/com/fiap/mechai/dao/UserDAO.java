package br.com.fiap.mechai.dao;

import br.com.fiap.mechai.model.User;
import br.com.fiap.mechai.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void createUser(User user) {
        String sql = "INSERT INTO TB_MECHAI_USUARIO (id_cliente, login, senha_hash, tipo_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getIdCliente());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getSenhaHash());
            stmt.setString(4, user.getTipoUsuario());

            stmt.executeUpdate();
            System.out.println("User inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User readUser(int id) {
        String sql = "SELECT * FROM TB_MECHAI_USUARIO WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setIdCliente(rs.getInt("id_cliente"));
                user.setLogin(rs.getString("login"));
                user.setSenhaHash(rs.getString("senha_hash"));
                user.setTipoUsuario(rs.getString("tipo_usuario"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM TB_MECHAI_USUARIO";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setIdCliente(rs.getInt("id_cliente"));
                user.setLogin(rs.getString("login"));
                user.setSenhaHash(rs.getString("senha_hash"));
                user.setTipoUsuario(rs.getString("tipo_usuario"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String sql = "UPDATE TB_MECHAI_USUARIO SET id_cliente = ?, login = ?, senha_hash = ?, tipo_usuario = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getIdCliente());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getSenhaHash());
            stmt.setString(4, user.getTipoUsuario());
            stmt.setInt(5, user.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM TB_MECHAI_USUARIO WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findByLogin(String login) {
        String sql = "SELECT * FROM TB_MECHAI_USUARIO WHERE login = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setIdUsuario(rs.getInt("id_usuario"));
                    user.setIdCliente(rs.getInt("id_cliente"));
                    user.setLogin(rs.getString("login"));
                    user.setSenhaHash(rs.getString("senha_hash"));
                    user.setTipoUsuario(rs.getString("tipo_usuario"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
