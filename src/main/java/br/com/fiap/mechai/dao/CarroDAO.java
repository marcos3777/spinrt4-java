package br.com.fiap.mechai.dao;

import br.com.fiap.mechai.model.Carro;
import br.com.fiap.mechai.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public int createCarro(Carro carro) throws Exception {
        int generatedId = -1;
        String sql = "INSERT INTO tb_mechai_carro (ID_CLIENTE, PLACA, MARCA, MODELO, ANO_FABRICACAO, COR, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_CARRO"})) {

            stmt.setInt(1, carro.getIdCliente());
            stmt.setString(2, carro.getPlaca());
            stmt.setString(3, carro.getMarca());
            stmt.setString(4, carro.getModelo());
            stmt.setString(5, carro.getAnoFabricacao());
            stmt.setString(6, carro.getCor());
            stmt.setInt(7, carro.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Falha ao criar carro, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new Exception("Falha ao criar carro, ID não obtido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return generatedId;
    }

    public Carro readCarro(int idCarro) throws Exception {
        String sql = "SELECT * FROM tb_mechai_carro WHERE ID_CARRO = ?";
        Carro carro = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarro);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    carro = new Carro();
                    carro.setIdCarro(rs.getInt("ID_CARRO"));
                    carro.setIdCliente(rs.getInt("ID_CLIENTE"));
                    carro.setPlaca(rs.getString("PLACA"));
                    carro.setMarca(rs.getString("MARCA"));
                    carro.setModelo(rs.getString("MODELO"));
                    carro.setAnoFabricacao(rs.getString("ANO_FABRICACAO"));
                    carro.setCor(rs.getString("COR"));
                    carro.setStatus(rs.getInt("STATUS"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return carro;
    }

    public List<Carro> readAllCarros() throws Exception {
        String sql = "SELECT * FROM tb_mechai_carro";
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carro carro = new Carro();
                carro.setIdCarro(rs.getInt("ID_CARRO"));
                carro.setIdCliente(rs.getInt("ID_CLIENTE"));
                carro.setPlaca(rs.getString("PLACA"));
                carro.setMarca(rs.getString("MARCA"));
                carro.setModelo(rs.getString("MODELO"));
                carro.setAnoFabricacao(rs.getString("ANO_FABRICACAO"));
                carro.setCor(rs.getString("COR"));
                carro.setStatus(rs.getInt("STATUS"));
                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return carros;
    }

    public void updateCarro(Carro carro) throws Exception {
        String sql = "UPDATE tb_mechai_carro SET ID_CLIENTE = ?, PLACA = ?, MARCA = ?, MODELO = ?, ANO_FABRICACAO = ?, COR = ?, STATUS = ? WHERE ID_CARRO = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carro.getIdCliente());
            stmt.setString(2, carro.getPlaca());
            stmt.setString(3, carro.getMarca());
            stmt.setString(4, carro.getModelo());
            stmt.setString(5, carro.getAnoFabricacao());
            stmt.setString(6, carro.getCor());
            stmt.setInt(7, carro.getStatus());
            stmt.setInt(8, carro.getIdCarro());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Falha ao atualizar carro, nenhuma linha afetada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteCarro(int idCarro) throws Exception {
        String sql = "DELETE FROM tb_mechai_carro WHERE ID_CARRO = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarro);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Falha ao deletar carro, nenhuma linha afetada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para listar carros de um cliente específico
    public List<Carro> readCarrosByCliente(int idCliente) throws Exception {
        String sql = "SELECT * FROM tb_mechai_carro WHERE ID_CLIENTE = ?";
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carro carro = new Carro();
                    carro.setIdCarro(rs.getInt("ID_CARRO"));
                    carro.setIdCliente(rs.getInt("ID_CLIENTE"));
                    carro.setPlaca(rs.getString("PLACA"));
                    carro.setMarca(rs.getString("MARCA"));
                    carro.setModelo(rs.getString("MODELO"));
                    carro.setAnoFabricacao(rs.getString("ANO_FABRICACAO"));
                    carro.setCor(rs.getString("COR"));
                    carro.setStatus(rs.getInt("STATUS"));
                    carros.add(carro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return carros;
    }
}
