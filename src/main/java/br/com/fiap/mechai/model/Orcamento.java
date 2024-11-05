package br.com.fiap.mechai.model;

public class Orcamento {
    private int idOrcamento;
    private Integer idMecanico; // Pode ser null
    private int idCarro;
    private String valor;
    private String dtPrevistaFinalizacao;
    private String dtFinalizacao;
    private String tipoProblema;
    private String descricaoProblema;
    private String enderecoAtual;
    private int status;
    private int idCliente;


    // Getters e Setters
    public int getIdOrcamento() {
        return idOrcamento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdOrcamento(int idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDtPrevistaFinalizacao() {
        return dtPrevistaFinalizacao;
    }

    public void setDtPrevistaFinalizacao(String dtPrevistaFinalizacao) {
        this.dtPrevistaFinalizacao = dtPrevistaFinalizacao;
    }

    public String getDtFinalizacao() {
        return dtFinalizacao;
    }

    public void setDtFinalizacao(String dtFinalizacao) {
        this.dtFinalizacao = dtFinalizacao;
    }

    public String getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getEnderecoAtual() {
        return enderecoAtual;
    }

    public void setEnderecoAtual(String enderecoAtual) {
        this.enderecoAtual = enderecoAtual;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
