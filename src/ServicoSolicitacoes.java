import java.util.ArrayList;
import java.util.List;

public class ServicoSolicitacoes {
    private List<Solicitacao> lista = new ArrayList<>();

    public void criarSolicitacao(String descricao, String local, String risco) {
        String protocolo = "SOL-" + (lista.size() + 1);
        Solicitacao s = new Solicitacao(protocolo, descricao, local, risco);
        lista.add(s);
        System.out.println("Solicitação criada com protocolo: " + protocolo);
    }

    public void listar() {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada.");
            return;
        }

        for (Solicitacao s : lista) {
            System.out.println(s);
        }
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        for (Solicitacao s : lista) {
            if (s.getProtocolo().equalsIgnoreCase(protocolo)) {
                return s;
            }
        }
        return null;
    }

    public void confirmarSolicitacao(String protocolo) {
        Solicitacao s = buscarPorProtocolo(protocolo);

        if (s != null) {
            s.confirmar();
            System.out.println("Confirmação registrada!");
        } else {
            System.out.println("Solicitação não encontrada.");
        }
    }

    public void atualizarStatus(String protocolo, String novoStatus) {
        Solicitacao s = buscarPorProtocolo(protocolo);

        if (s != null) {
            s.atualizarStatus(novoStatus);
            System.out.println("Status atualizado!");
        } else {
            System.out.println("Solicitação não encontrada.");
        }
    }
}