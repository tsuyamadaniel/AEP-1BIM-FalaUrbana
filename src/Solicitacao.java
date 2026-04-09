import java.util.ArrayList;
import java.util.List;

public class Solicitacao {

    private String protocolo;
    private String descricao;
    private String local;

    private Categoria categoria;
    private Status status;

    private boolean anonimo;

    private int confirmacoes;
    private String prioridade;

    private List<HistoricoStatus> historico;


    public Solicitacao(String protocolo, String descricao, Categoria categoria, String local, boolean anonimo) {
        this.protocolo = protocolo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.local = local;
        this.anonimo = anonimo;

        this.status = Status.ABERTO;
        this.confirmacoes = 0;

        this.historico = new ArrayList<>();
        this.prioridade = calcularPrioridade();
    }


    private String calcularPrioridade() {

        if (categoria == Categoria.SAUDE || categoria == Categoria.SEGURANCA) {
            return "ALTA";
        }

        if (confirmacoes > 10) {
            return "ALTA";
        }

        return "NORMAL";
    }

    public void confirmar() {
        this.confirmacoes++;
        this.prioridade = calcularPrioridade();
    }


    public void atualizarStatus(Status novoStatus, String comentario, String responsavel) {
        this.status = novoStatus;
        historico.add(new HistoricoStatus(novoStatus, comentario, responsavel));
    }


    public String getProtocolo() {
        return protocolo;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public String toString() {
        return "\nProtocolo: " + protocolo +
                "\nDescrição: " + descricao +
                "\nCategoria: " + categoria +
                "\nLocal: " + local +
                "\nConfirmações: " + confirmacoes +
                "\nPrioridade: " + prioridade +
                "\nStatus: " + status +
                "\nUsuário: " + (anonimo ? "ANÔNIMO" : "IDENTIFICADO") +
                "\n-------------------------";
    }
}