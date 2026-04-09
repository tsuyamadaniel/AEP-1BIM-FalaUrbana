
import enums.Categoria;
import enums.LocalTipo;
import enums.Status;
import enums.TipoUsuario;
import models.Endereco;
import models.Solicitacao;
import models.Usuario;
import services.ServicoSolicitacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();
        List<Usuario> usuarios = new ArrayList();
        usuarios.add(new Usuario("Admin", TipoUsuario.GESTOR, "000", "admin@email.com", "admin"));
        usuarios.add(new Usuario("User", TipoUsuario.CIDADAO, "000", "user@email.com", "user"));
        boolean sistemaRodando = true;

        while(sistemaRodando) {
            System.out.println("\n===== SISTEMA DE SOLICITAÇÕES =====");
            System.out.println("1 - Cidadão");
            System.out.println("2 - Anônimo");
            System.out.println("3 - Gestor");
            System.out.println("0 - Encerrar sistema");
            System.out.print("Escolha: ");
            int tipo = sc.nextInt();
            sc.nextLine();
            if (tipo == 0) {
                sistemaRodando = false;
                break;
            }

            Usuario usuario = null;
            if (tipo != 1 && tipo != 3) {
                if (tipo != 2) {
                    System.out.println("Opção inválida!");
                    continue;
                }

                usuario = new Usuario(TipoUsuario.ANONIMO);
            } else {
                TipoUsuario tipoUsuario = tipo == 1 ? TipoUsuario.CIDADAO : TipoUsuario.GESTOR;
                System.out.println("1 - Login");
                System.out.println("2 - Cadastrar");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                sc.nextLine();
                if (op == 1) {
                    usuario = login(sc, tipoUsuario, usuarios);
                    if (usuario == null) {
                        continue;
                    }
                } else {
                    if (op != 2) {
                        System.out.println("Opção inválida!");
                        continue;
                    }

                    usuario = cadastrar(sc, tipoUsuario, usuarios);
                }
            }

            if (usuario.getTipo() == TipoUsuario.GESTOR) {
                menuGestor(sc, servico, usuario);
            } else {
                menuUsuario(sc, servico, usuario);
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    public static Usuario cadastrar(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        while(true) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            if (!validarEmail(email)) {
                System.out.println("Email inválido!");
            } else {
                if (!emailJaExiste(email, usuarios)) {
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    Usuario novo = new Usuario(nome, tipo, cpf, email, senha);
                    usuarios.add(novo);
                    System.out.println("Cadastro realizado com sucesso!");
                    return novo;
                }

                System.out.println("Email já cadastrado!");
            }
        }
    }

    public static Usuario login(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for(Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) && u.getTipo() == tipo) {
                System.out.println("Login realizado com sucesso!");
                return u;
            }
        }

        System.out.println("Usuário não encontrado.");
        return null;
    }

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean emailJaExiste(String email, List<Usuario> usuarios) {
        for(Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public static void menuUsuario(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {
        int opcao;
        do {
            System.out.println("\n--- MENU USUÁRIO ---");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Buscar por protocolo");
            System.out.println("3 - Confirmar problema");
            System.out.println("4 - Listar todas solicitações");
            System.out.println("5 - Buscar por local");
            System.out.println("6 - Minhas solicitações");
            System.out.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();
                    System.out.println("Local:");
                    System.out.println("1 - HOSPITAL");
                    System.out.println("2 - ESCOLA");
                    System.out.println("3 - PRACA");
                    System.out.println("4 - PREFEITURA");
                    System.out.println("5 - RUA");
                    System.out.println("6 - OUTRO");
                    int loc = sc.nextInt();
                    sc.nextLine();
                    LocalTipo localTipo = LocalTipo.values()[loc - 1];
                    String localOutro = null;
                    if (localTipo == LocalTipo.OUTRO) {
                        System.out.print("Digite o local: ");
                        localOutro = sc.nextLine();
                    }

                    System.out.println("enums.Categoria:");
                    System.out.println("1 - ILUMINACAO");
                    System.out.println("2 - BURACO");
                    System.out.println("3 - LIMPEZA");
                    System.out.println("4 - SAUDE");
                    System.out.println("5 - SEGURANCA");
                    System.out.println("6 - OUTRO");
                    int cat = sc.nextInt();
                    sc.nextLine();
                    Categoria categoria = Categoria.values()[cat - 1];
                    if (categoria == Categoria.OUTRO) {
                        System.out.print("Digite a categoria: ");
                        sc.nextLine();
                    }

                    System.out.print("Rua: ");
                    String rua = sc.nextLine();
                    System.out.print("Número: ");
                    String numero = sc.nextLine();
                    System.out.print("CEP: ");
                    String cep = sc.nextLine();
                    System.out.print("Bairro: ");
                    String bairro = sc.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = sc.nextLine();
                    System.out.print("Estado: ");
                    String estado = sc.nextLine();
                    Endereco endereco = new Endereco(rua, numero, cep, bairro, cidade, estado);
                    servico.criarSolicitacao(descricao, categoria, localTipo, localOutro, endereco, usuario);
                    break;
                case 2:
                    System.out.println("Buscar por:");
                    System.out.println("1 - Protocolo");
                    System.out.println("2 - CPF");
                    int busca = sc.nextInt();
                    sc.nextLine();
                    if (busca == 1) {
                        System.out.print("Protocolo: ");
                        String protocolo = sc.nextLine();
                        Solicitacao s = servico.buscarPorProtocolo(protocolo);
                        if (s != null) {
                            System.out.println(s);
                        } else {
                            System.out.println("Não encontrada.");
                        }
                    } else if (busca == 2) {
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        List<Solicitacao> lista = servico.buscarPorCPF(cpf);
                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma solicitação encontrada.");
                        } else {
                            for(Solicitacao s : lista) {
                                System.out.println(s);
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.print("Protocolo: ");
                    String protocoloConf = sc.nextLine();
                    servico.confirmarSolicitacao(protocoloConf);
                    break;
                case 4:
                    servico.listar();
                    break;
                case 5:
                    System.out.println("Buscar por:");
                    System.out.println("1 - Tipo de Local");
                    System.out.println("2 - Bairro");
                    int opBusca = sc.nextInt();
                    sc.nextLine();
                    if (opBusca == 1) {
                        System.out.println("Local:");
                        System.out.println("1 - HOSPITAL");
                        System.out.println("2 - ESCOLA");
                        System.out.println("3 - PRACA");
                        System.out.println("4 - PREFEITURA");
                        System.out.println("5 - RUA");
                        System.out.println("6 - OUTRO");
                        int loca = sc.nextInt();
                        sc.nextLine();
                        LocalTipo tipo = LocalTipo.values()[loca - 1];

                        for(Solicitacao s : servico.buscarPorLocalTipo(tipo)) {
                            System.out.println(s);
                        }
                    } else if (opBusca == 2) {
                        System.out.print("Digite o bairro: ");
                        String bairroUser = sc.nextLine();

                        for(Solicitacao s : servico.buscarPorBairro(bairroUser)) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 6:

                    if (usuario.isAnonimo()) {
                        System.out.println("Usuário anônimo não possui histórico.");
                        break;
                    }

                    List<Solicitacao> minhas = servico.buscarPorUsuario(usuario);

                    if (minhas.isEmpty()) {
                        System.out.println("Você não possui solicitações.");
                    } else {
                        for (Solicitacao s : minhas) {
                            System.out.println(s);
                        }
                    }

                    break;

            }
        } while(opcao != 0);

    }

    public static void menuGestor(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {
        int opcao;
        do {
            System.out.println("\n--- MENU GESTOR ---");
            System.out.println("1 - Listar solicitações");
            System.out.println("2 - Atualizar status");
            System.out.println("3 - Buscar");
            System.out.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    servico.listar();
                    break;
                case 2:
                    System.out.print("Protocolo: ");
                    String protocolo = sc.nextLine();
                    System.out.println("Novo enums.Status:");
                    System.out.println("1 - TRIAGEM");
                    System.out.println("2 - EM_EXECUCAO");
                    System.out.println("3 - RESOLVIDO");
                    System.out.println("4 - ENCERRADO");
                    int st = sc.nextInt();
                    sc.nextLine();
                    Status status = Status.values()[st - 1];
                    System.out.print("Comentário: ");
                    String comentario = sc.nextLine();
                    servico.atualizarStatus(protocolo, status, comentario, usuario.getNome());
                    break;
                case 3:
                    menuBuscaGestor(sc, servico);
            }
        } while(opcao != 0);

    }

    public static void menuBuscaGestor(Scanner sc, ServicoSolicitacoes servico) {
        int opcao;
        do {
            System.out.println("\n--- BUSCAR SOLICITAÇÕES ---");
            System.out.println("1 - Por status");
            System.out.println("2 - Por prioridade");
            System.out.println("3 - Por local");
            System.out.println("4 - Por bairro");
            System.out.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("enums.Status:");
                    System.out.println("1 - ABERTO");
                    System.out.println("2 - TRIAGEM");
                    System.out.println("3 - EM_EXECUCAO");
                    System.out.println("4 - RESOLVIDO");
                    System.out.println("5 - ENCERRADO");
                    int st = sc.nextInt();
                    sc.nextLine();
                    Status status = Status.values()[st - 1];
                    List<Solicitacao> listaStatus = servico.buscarPorStatus(status);
                    if (listaStatus.isEmpty()) {
                        System.out.println("Nenhuma encontrada.");
                    } else {
                        for(Solicitacao s : listaStatus) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Digite prioridade (ALTA/NORMAL): ");
                    String prioridade = sc.nextLine();
                    List<Solicitacao> listaPrioridade = servico.buscarPorPrioridade(prioridade);
                    if (listaPrioridade.isEmpty()) {
                        System.out.println("Nenhuma encontrada.");
                    } else {
                        for(Solicitacao s : listaPrioridade) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Local:");
                    System.out.println("1 - HOSPITAL");
                    System.out.println("2 - ESCOLA");
                    System.out.println("3 - PRACA");
                    System.out.println("4 - PREFEITURA");
                    System.out.println("5 - RUA");
                    System.out.println("6 - OUTRO");
                    int loc = sc.nextInt();
                    sc.nextLine();
                    LocalTipo tipo = LocalTipo.values()[loc - 1];
                    List<Solicitacao> listaLocal = servico.buscarPorLocalTipo(tipo);
                    if (listaLocal.isEmpty()) {
                        System.out.println("Nenhuma encontrada.");
                    } else {
                        for(Solicitacao s : listaLocal) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 4:
                    System.out.print("Digite o bairro: ");
                    String bairro = sc.nextLine();
                    List<Solicitacao> listaBairro = servico.buscarPorBairro(bairro);
                    if (listaBairro.isEmpty()) {
                        System.out.println("Nenhuma encontrada.");
                    } else {
                        for(Solicitacao s : listaBairro) {
                            System.out.println(s);
                        }
                    }
            }
        } while(opcao != 0);

    }
}
