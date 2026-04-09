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

    private boolean anonimo;

    private int confirmacoes;
    private String prioridade;
    private Usuario usuario;

    private List<HistoricoStatus> historico;


    public Solicitacao(String protocolo, String descricao, Categoria categoria,
                       LocalTipo localTipo, String localOutro,
                       Endereco endereco, boolean anonimo,
                       Usuario usuario) {

        this.protocolo = protocolo;
        this.descricao = descricao;
        this.categoria = categoria;

        this.localTipo = localTipo;
        this.localOutro = localOutro;
        this.endereco = endereco;

        this.anonimo = anonimo;
        this.usuario = usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public String toString() {

        String localExibicao = (localTipo == LocalTipo.OUTRO) ? localOutro : localTipo.toString();
        String nomeUsuario = (usuario.isAnonimo()) ? "ANÔNIMO" : usuario.getNome();

        return "\nProtocolo: " + protocolo +
                "\nDescrição: " + descricao +
                "\nCategoria: " + categoria +
                "\nLocal: " + localExibicao +
                "\nEndereço: " + endereco +
                "\nConfirmações: " + confirmacoes +
                "\nPrioridade: " + prioridade +
                "\nStatus: " + status +
                "\nUsuário: " + (anonimo ? "ANÔNIMO" : "IDENTIFICADO") +
                "\nSolicitante: " + nomeUsuario +
                "\n-------------------------";
    }
}