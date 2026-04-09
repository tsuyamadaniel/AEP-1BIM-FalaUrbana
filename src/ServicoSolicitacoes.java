import java.util.ArrayList;
import java.util.List;

public class ServicoSolicitacoes {
    private List<Solicitacao> lista = new ArrayList<>();

    public void criarSolicitacao(String descricao, Categoria categoria,
                                 LocalTipo localTipo, String localOutro,
                                 Endereco endereco, Usuario usuario) {

        if (usuario.getTipo() == TipoUsuario.ANONIMO && descricao.length() < 10) {
            System.out.println("Descrição muito curta! Para denúncias anônimas, descreva melhor o problema.");
            return;
        }

        String protocolo = GeradorProtocolo.gerar(8);
        boolean anonimo = usuario.isAnonimo();

        Solicitacao s = new Solicitacao(
                protocolo, descricao, categoria,
                localTipo, localOutro,
                endereco, anonimo,
                usuario
        );

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

    public List<Solicitacao> buscarPorCPF(String cpf) {

        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (!s.getUsuario().isAnonimo() &&
                    s.getUsuario().getCPF().equals(cpf)) {

                resultado.add(s);
            }
        }

        return resultado;
    }
}