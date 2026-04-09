package services;

import enums.Categoria;
import enums.LocalTipo;
import enums.Status;
import enums.TipoUsuario;
import models.Endereco;
import models.Solicitacao;
import models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServicoSolicitacoes {
    private List<Solicitacao> lista = new ArrayList();

    public ServicoSolicitacoes() {
    }

    public void criarSolicitacao(String descricao, Categoria categoria, LocalTipo localTipo, String localOutro, Endereco endereco, Usuario usuario) {
        if (usuario.getTipo() == TipoUsuario.ANONIMO && descricao.length() < 10) {
            System.out.println("Descrição muito curta! Para denúncias anônimas, descreva melhor o problema.");
        } else {
            String protocolo = GeradorProtocolo.gerar(8);
            boolean anonimo = usuario.isAnonimo();
            Solicitacao s = new Solicitacao(protocolo, descricao, categoria, localTipo, localOutro, endereco, usuario);
            this.lista.add(s);
            System.out.println("Solicitação criada: " + protocolo);
        }
    }

    public void listar() {
        if (this.lista.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada.");
        } else {
            for(Solicitacao s : this.lista) {
                System.out.println(s);
            }

        }
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        for(Solicitacao s : this.lista) {
            if (s.getProtocolo().equalsIgnoreCase(protocolo)) {
                return s;
            }
        }

        return null;
    }

    public void confirmarSolicitacao(String protocolo) {
        Solicitacao s = this.buscarPorProtocolo(protocolo);
        if (s != null) {
            s.confirmar();
            System.out.println("Confirmação registrada!");
        } else {
            System.out.println("Solicitação não encontrada.");
        }

    }

    public void atualizarStatus(String protocolo, Status status, String comentario, String responsavel) {
        Solicitacao s = this.buscarPorProtocolo(protocolo);
        if (s == null) {
            System.out.println("Não encontrada.");
        } else {
            s.atualizarStatus(status, comentario, responsavel);
        }
    }

    public List<Solicitacao> buscarPorCPF(String cpf) {
        List<Solicitacao> resultado = new ArrayList();

        for(Solicitacao s : this.lista) {
            if (!s.getUsuario().isAnonimo() && s.getUsuario().getCPF().equals(cpf)) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorLocalTipo(LocalTipo tipo) {
        List<Solicitacao> resultado = new ArrayList();

        for(Solicitacao s : this.lista) {
            if (s.getLocalTipo() == tipo) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorStatus(Status status) {
        List<Solicitacao> resultado = new ArrayList();

        for(Solicitacao s : this.lista) {
            if (s.getStatus() == status) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorPrioridade(String prioridade) {
        List<Solicitacao> resultado = new ArrayList();

        for(Solicitacao s : this.lista) {
            if (s.getPrioridade().equalsIgnoreCase(prioridade)) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorBairro(String bairro) {
        List<Solicitacao> resultado = new ArrayList();

        for(Solicitacao s : this.lista) {
            if (s.getEndereco().getBairro().equalsIgnoreCase(bairro)) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorUsuario(Usuario usuario) {

        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (!s.isAnonimo() && s.getUsuario().equals(usuario)) {
                resultado.add(s);
            }
        }

        return resultado;
    }
}
