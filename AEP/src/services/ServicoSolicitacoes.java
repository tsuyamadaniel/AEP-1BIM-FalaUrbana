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

    private List<Solicitacao> lista = new ArrayList<>();

    public void criarSolicitacao(String descricao, Categoria categoria, LocalTipo localTipo, String localOutro, Endereco endereco, Usuario usuario) {

        if (usuario.getTipo() == TipoUsuario.ANONIMO && descricao.length() < 10) {
            System.out.println("Descrição muito curta! Para denúncias anônimas, descreva melhor o problema.");
            return;
        }

        String protocolo = GeradorProtocolo.gerar(8);
        Solicitacao s = new Solicitacao(protocolo, descricao, categoria, localTipo, localOutro, endereco, usuario);

        lista.add(s);

        System.out.println("Solicitação criada com sucesso!");
        System.out.println("Protocolo: " + protocolo);
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

        System.out.println("Solicitação não encontrada para o protocolo informado.");
        return null;
    }

    public void confirmarSolicitacao(String protocolo) {
        Solicitacao s = buscarPorProtocolo(protocolo);

        if (s != null) {
            s.confirmar();
            System.out.println("Confirmação registrada!");
        }
    }

    public void atualizarStatus(String protocolo, Status status, String comentario, String responsavel) {
        Solicitacao s = buscarPorProtocolo(protocolo);

        if (s != null) {
            s.atualizarStatus(status, comentario, responsavel);
            System.out.println("Status atualizado com sucesso!");
        }
    }

    public List<Solicitacao> buscarPorCPF(String cpf) {
        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (!s.getUsuario().isAnonimo() && s.getUsuario().getCPF().equals(cpf)) {
                resultado.add(s);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada para este CPF.");
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorLocalTipo(LocalTipo tipo) {
        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (s.getLocalTipo() == tipo) {
                resultado.add(s);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada para este tipo de local.");
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorStatus(Status status) {
        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (s.getStatus() == status) {
                resultado.add(s);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada para este status.");
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorPrioridade(String prioridade) {
        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (s.getPrioridade().equalsIgnoreCase(prioridade)) {
                resultado.add(s);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada para esta prioridade.");
        }

        return resultado;
    }

    public List<Solicitacao> buscarPorBairro(String bairro) {
        List<Solicitacao> resultado = new ArrayList<>();

        for (Solicitacao s : lista) {
            if (s.getEndereco().getBairro().equalsIgnoreCase(bairro)) {
                resultado.add(s);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada para este bairro.");
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

        if (resultado.isEmpty()) {
            System.out.println("Você não possui solicitações.");
        }

        return resultado;
    }
}