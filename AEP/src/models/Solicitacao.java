package models;

import enums.Categoria;
import enums.LocalTipo;
import enums.Status;

import java.util.ArrayList;
import java.util.List;

public class Solicitacao {
    private String protocolo;
    private String descricao;
    private LocalTipo localTipo;
    private String localOutro;
    private Endereco endereco;
    private Categoria categoria;
    private Status status;
    private int confirmacoes;
    private String prioridade;
    private Usuario usuario;
    private List<HistoricoStatus> historico;

    public Solicitacao(String protocolo, String descricao, Categoria categoria, LocalTipo localTipo, String localOutro, Endereco endereco, Usuario usuario) {
        this.protocolo = protocolo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.localTipo = localTipo;
        this.localOutro = localOutro;
        this.endereco = endereco;
        this.usuario = usuario;
        this.status = Status.ABERTO;
        this.confirmacoes = 0;
        this.historico = new ArrayList();
        this.prioridade = this.calcularPrioridade();
    }

    private String calcularPrioridade() {
        if (this.categoria != Categoria.SAUDE && this.categoria != Categoria.SEGURANCA) {
            return this.confirmacoes >= 5 ? "ALTA" : "NORMAL";
        } else {
            return "ALTA";
        }
    }

    public void confirmar() {
        ++this.confirmacoes;
        this.prioridade = this.calcularPrioridade();
    }

    public void atualizarStatus(Status novoStatus, String comentario, String responsavel) {
        this.status = novoStatus;
        this.historico.add(new HistoricoStatus(novoStatus, comentario, responsavel));
    }

    public String getProtocolo() {
        return this.protocolo;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public LocalTipo getLocalTipo() {
        return this.localTipo;
    }

    public String getLocalOutro() {
        return this.localOutro;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getPrioridade() {
        return this.prioridade;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public boolean isAnonimo() {
        return this.usuario.isAnonimo();
    }

    public String toString() {
        String localExibicao = this.localTipo == LocalTipo.OUTRO ? this.localOutro : this.localTipo.toString();
        String nomeUsuario = this.usuario.isAnonimo() ? "ANÔNIMO" : this.usuario.getNome();
        String var10000 = this.protocolo;
        return "\nProtocolo: " + var10000 + "\nDescrição: " + this.descricao + "\nCategoria: " + String.valueOf(this.categoria) + "\nLocal: " + localExibicao + "\nEndereço: " + String.valueOf(this.endereco) + "\nConfirmações: " + this.confirmacoes + "\nPrioridade: " + this.prioridade + "\nStatus: " + String.valueOf(this.status) + "\nSolicitante: " + nomeUsuario + "\n-------------------------";
    }
}
