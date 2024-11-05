package br.com.fiap.mechai.dao;

import br.com.fiap.mechai.model.Mechanic;
import br.com.fiap.mechai.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicDAO {

    public void createMechanic(Mechanic mechanic) {
        String sql = "INSERT INTO TB_MECHAI_MECANICO (cpf, nome, nm_empresa, endereco, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mechanic.getCpf());
            stmt.setString(2, mechanic.getNome());
            stmt.setString(3, mechanic.getNmEmpresa());
            stmt.setString(4, mechanic.getEndereco());
            stmt.setString(5, mechanic.getTelefone());
            stmt.setString(6, mechanic.getEmail());

            stmt.executeUpdate();
            System.out.println("Mechanic inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mechanic readMechanic(int idMecanico) {
        String sql = "SELECT * FROM TB_MECHAI_MECANICO WHERE id_mecanico = ?";
        Mechanic mechanic = new Mechanic();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMecanico);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mechanic.setIdMecanico(rs.getInt("id_mecanico"));
                    mechanic.setCpf(rs.getString("cpf"));
                    mechanic.setNome(rs.getString("nome"));
                    mechanic.setNmEmpresa(rs.getString("nm_empresa"));
                    mechanic.setEndereco(rs.getString("endereco"));
                    mechanic.setTelefone(rs.getString("telefone"));
                    mechanic.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mechanic;
    }

    public List<Mechanic> readAllMechanics() {
        String sql = "SELECT * FROM TB_MECHAI_MECANICO";
        List<Mechanic> mechanics = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mechanic mechanic = new Mechanic();
                mechanic.setIdMecanico(rs.getInt("id_mecanico"));
                mechanic.setCpf(rs.getString("cpf"));
                mechanic.setNome(rs.getString("nome"));
                mechanic.setNmEmpresa(rs.getString("nm_empresa"));
                mechanic.setEndereco(rs.getString("endereco"));
                mechanic.setTelefone(rs.getString("telefone"));
                mechanic.setEmail(rs.getString("email"));
                mechanics.add(mechanic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mechanics;
    }

    public void updateMechanic(Mechanic mechanic) {
        String sql = "UPDATE TB_MECHAI_MECANICO SET cpf = ?, nome = ?, nm_empresa = ?, endereco = ?, telefone = ?, email = ? WHERE id_mecanico = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mechanic.getCpf());
            stmt.setString(2, mechanic.getNome());
            stmt.setString(3, mechanic.getNmEmpresa());
            stmt.setString(4, mechanic.getEndereco());
            stmt.setString(5, mechanic.getTelefone());
            stmt.setString(6, mechanic.getEmail());
            stmt.setInt(7, mechanic.getIdMecanico());

            stmt.executeUpdate();
            System.out.println("Mechanic updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMechanic(int idMecanico) {
        String sql = "DELETE FROM TB_MECHAI_MECANICO WHERE id_mecanico = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMecanico);

            stmt.executeUpdate();
            System.out.println("Mechanic deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
