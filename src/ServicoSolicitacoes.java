import java.util.ArrayList;
import java.util.List;

public class ServicoSolicitacoes {
    private List<Solicitacao> lista = new ArrayList<>();

    public void criarSolicitacao(String descricao, Categoria categoria, String local, Usuario usuario) {

        if (usuario.getTipo() == TipoUsuario.ANONIMO && descricao.length() < 10) {
            System.out.println("Descrição insuficiente para denúncia anônima.");
            return;
        }

        String protocolo = "SOL-" + (lista.size() + 1);
        boolean anonimo = usuario.getTipo() == TipoUsuario.ANONIMO;

        Solicitacao s = new Solicitacao(protocolo, descricao, categoria, local, anonimo);
        lista.add(s);

        System.out.println("Solicitação criada: " + protocolo);
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

    public void atualizarStatus(String protocolo, Status status, String comentario, String responsavel) {

        Solicitacao s = buscarPorProtocolo(protocolo);

        if (s == null) {
            System.out.println("Não encontrada.");
            return;
        }

        s.atualizarStatus(status, comentario, responsavel);
    }
}