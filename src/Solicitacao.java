public class Solicitacao {
    private String protocolo;
    private String descricao;
    private String local;
    private String risco;
    private String prioridade;
    private int confirmacoes;
    private String status;

    public Solicitacao(String protocolo, String descricao, String local, String risco) {
        this.protocolo = protocolo;
        this.descricao = descricao;
        this.local = local;
        this.risco = risco;
        this.confirmacoes = 0;
        this.status = "ABERTA";
        this.prioridade = calcularPrioridade();
    }

    public String calcularPrioridade() {
        if (local.equalsIgnoreCase("hospital") || local.equalsIgnoreCase("escola")) {
            return "ALTA";
        }

        if (risco.equalsIgnoreCase("alto")) {
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

    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public String toString() {
        return "\nProtocolo: " + protocolo +
                "\nDescrição: " + descricao +
                "\nLocal: " + local +
                "\nRisco: " + risco +
                "\nConfirmações: " + confirmacoes +
                "\nPrioridade: " + prioridade +
                "\nStatus: " + status +
                "\n-------------------------";
    }
}