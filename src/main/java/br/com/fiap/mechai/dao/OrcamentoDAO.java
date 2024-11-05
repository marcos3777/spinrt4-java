package br.com.fiap.mechai.dao;

import br.com.fiap.mechai.model.Orcamento;
import br.com.fiap.mechai.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {
    public int createOrcamento(Orcamento orcamento) throws Exception {
        int generatedId = -1;
        String sql = "INSERT INTO tb_mechai_orcamento (ID_CLIENTE, ID_MECANICO, ID_CARRO, VALOR, DT_PREVISTA_FINALIZACAO, DT_FINALIZACAO, TIPO_PROBLEMA, DESCRICAO_PROBLEMA, ENDERECO_ATUAL, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ORCAMENTO"})) {

            stmt.setInt(1, orcamento.getIdCliente());
            stmt.setObject(2, orcamento.getIdMecanico()); // Use setObject para permitir valores nulos
            stmt.setInt(3, orcamento.getIdCarro());
            stmt.setString(4, orcamento.getValor());
            stmt.setString(5, orcamento.getDtPrevistaFinalizacao());
            stmt.setString(6, orcamento.getDtFinalizacao());
            stmt.setString(7, orcamento.getTipoProblema());
            stmt.setString(8, orcamento.getDescricaoProblema());
            stmt.setString(9, orcamento.getEnderecoAtual());
            stmt.setInt(10, orcamento.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Falha ao criar orçamento, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new Exception("Falha ao criar orçamento, ID não obtido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Re-lança a exceção para ser tratada no método chamador
        }
        return generatedId;
    }


    public Orcamento readOrcamento(int idOrcamento) {
        // Implementar leitura do orçamento pelo ID
        String sql = "SELECT * FROM tb_mechai_orcamento WHERE ID_ORCAMENTO = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrcamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Orcamento orcamento = new Orcamento();
                orcamento.setIdOrcamento(rs.getInt("ID_ORCAMENTO"));
                orcamento.setIdMecanico(rs.getInt("ID_MECANICO"));
                orcamento.setIdCarro(rs.getInt("ID_CARRO"));
                orcamento.setValor(rs.getString("VALOR"));
                orcamento.setDtPrevistaFinalizacao(rs.getString("DT_PREVISTA_FINALIZACAO"));
                orcamento.setDtFinalizacao(rs.getString("DT_FINALIZACAO"));
                orcamento.setTipoProblema(rs.getString("TIPO_PROBLEMA"));
                orcamento.setDescricaoProblema(rs.getString("DESCRICAO_PROBLEMA"));
                orcamento.setEnderecoAtual(rs.getString("ENDERECO_ATUAL"));
                orcamento.setStatus(rs.getInt("STATUS"));
                return orcamento;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    // OrcamentoDAO.java
    public List<Orcamento> readAllOrcamentos() throws Exception {
        String sql = "SELECT * FROM TB_MECHAI_ORCAMENTO";
        List<Orcamento> orcamentos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Orcamento orcamento = new Orcamento();
                orcamento.setIdOrcamento(rs.getInt("ID_ORCAMENTO"));
                orcamento.setIdCliente(rs.getInt("ID_CLIENTE"));
                orcamento.setIdMecanico(rs.getInt("ID_MECANICO"));
                orcamento.setIdCarro(rs.getInt("ID_CARRO"));
                orcamento.setValor(rs.getString("VALOR"));
                orcamento.setDtPrevistaFinalizacao(rs.getString("DT_PREVISTA_FINALIZACAO"));
                orcamento.setDtFinalizacao(rs.getString("DT_FINALIZACAO"));
                orcamento.setTipoProblema(rs.getString("TIPO_PROBLEMA"));
                orcamento.setDescricaoProblema(rs.getString("DESCRICAO_PROBLEMA"));
                orcamento.setEnderecoAtual(rs.getString("ENDERECO_ATUAL"));
                orcamento.setStatus(rs.getInt("STATUS"));
                orcamentos.add(orcamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return orcamentos;
    }

    public List<Orcamento> readOrcamentosByCliente(int idCliente) throws Exception {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM tb_mechai_orcamento WHERE ID_CLIENTE = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Orcamento orcamento = new Orcamento();
                // Preencha os campos do orçamento a partir do ResultSet
                orcamento.setIdOrcamento(rs.getInt("ID_ORCAMENTO"));
                orcamento.setIdMecanico(rs.getInt("ID_MECANICO"));
                orcamento.setIdCarro(rs.getInt("ID_CARRO"));
                orcamento.setValor(rs.getString("VALOR"));
                orcamento.setDtPrevistaFinalizacao(rs.getString("DT_PREVISTA_FINALIZACAO"));
                orcamento.setDtFinalizacao(rs.getString("DT_FINALIZACAO"));
                orcamento.setTipoProblema(rs.getString("TIPO_PROBLEMA"));
                orcamento.setDescricaoProblema(rs.getString("DESCRICAO_PROBLEMA"));
                orcamento.setEnderecoAtual(rs.getString("ENDERECO_ATUAL"));
                orcamento.setStatus(rs.getInt("STATUS"));

                orcamentos.add(orcamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return orcamentos;
    }

    public boolean updateOrcamento(Orcamento orcamento) throws Exception {
        String sql = "UPDATE tb_mechai_orcamento SET ID_MECANICO = ?, ID_CARRO = ?, VALOR = ?, DT_PREVISTA_FINALIZACAO = ?, DT_FINALIZACAO = ?, TIPO_PROBLEMA = ?, DESCRICAO_PROBLEMA = ?, ENDERECO_ATUAL = ?, STATUS = ? WHERE ID_ORCAMENTO = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, orcamento.getIdMecanico()); // Use setObject to allow null values
            stmt.setInt(2, orcamento.getIdCarro());
            stmt.setString(3, orcamento.getValor());
            stmt.setString(4, orcamento.getDtPrevistaFinalizacao());
            stmt.setString(5, orcamento.getDtFinalizacao());
            stmt.setString(6, orcamento.getTipoProblema());
            stmt.setString(7, orcamento.getDescricaoProblema());
            stmt.setString(8, orcamento.getEnderecoAtual());
            stmt.setInt(9, orcamento.getStatus());
            stmt.setInt(10, orcamento.getIdOrcamento());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Falha ao atualizar orçamento, nenhuma linha afetada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }


}
