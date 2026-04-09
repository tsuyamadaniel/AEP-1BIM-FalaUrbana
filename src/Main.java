import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();
        List<Usuario> usuarios = new ArrayList<>();

        // usuário gestor padrão
        usuarios.add(new Usuario("Admin", TipoUsuario.GESTOR, "000", "admin@email.com", "admin"));
        usuarios.add(new Usuario("User", TipoUsuario.CIDADAO, "000", "user@email.com", "user"));

        boolean sistemaRodando = true;

        while (sistemaRodando) {

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

            if (tipo == 1 || tipo == 3) {

                TipoUsuario tipoUsuario = (tipo == 1) ? TipoUsuario.CIDADAO : TipoUsuario.GESTOR;

                System.out.println("1 - Login");
                System.out.println("2 - Cadastrar");
                System.out.print("Escolha: ");

                int op = sc.nextInt();
                sc.nextLine();

                if (op == 1) {
                    usuario = login(sc, tipoUsuario, usuarios);
                    if (usuario == null) continue;

                } else if (op == 2) {
                    usuario = cadastrar(sc, tipoUsuario, usuarios);

                } else {
                    System.out.println("Opção inválida!");
                    continue;
                }

            } else if (tipo == 2) {
                usuario = new Usuario(TipoUsuario.ANONIMO);

            } else {
                System.out.println("Opção inválida!");
                continue;
            }

            // chama menus
            if (usuario.getTipo() == TipoUsuario.GESTOR) {
                menuGestor(sc, servico, usuario);
            } else {
                menuUsuario(sc, servico, usuario);
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    // =========================
    // CADASTRO
    // =========================
    public static Usuario cadastrar(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (!validarEmail(email)) {
                System.out.println("Email inválido!");
                continue;
            }

            if (emailJaExiste(email, usuarios)) {
                System.out.println("Email já cadastrado!");
                continue;
            }

            break;
        }

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario novo = new Usuario(nome, tipo, cpf, email, senha);
        usuarios.add(novo);

        System.out.println("Cadastro realizado com sucesso!");

        return novo;
    }

    // =========================
    // LOGIN
    // =========================
    public static Usuario login(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) &&
                    u.getSenha().equals(senha) &&
                    u.getTipo() == tipo) {

                System.out.println("Login realizado com sucesso!");
                return u;
            }
        }

        System.out.println("Usuário não encontrado.");
        return null;
    }

    // =========================
    // VALIDADOR DE EMAIL
    // =========================
    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean emailJaExiste(String email, List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // MENU USUÁRIO
    // =========================
    public static void menuUsuario(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {

        int opcao;

        do {
            System.out.println("\n--- MENU USUÁRIO ---");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Buscar por protocolo");
            System.out.println("3 - Confirmar problema");
            System.out.println("0 - Voltar");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();

                    // ===== LOCAL =====
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

                    // ===== CATEGORIA =====
                    System.out.println("Categoria:");
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
                        sc.nextLine(); // (opcional armazenar depois)
                    }

                    // ===== ENDEREÇO =====
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

                    // ===== CRIAR SOLICITAÇÃO =====
                    servico.criarSolicitacao(
                            descricao,
                            categoria,
                            localTipo,
                            localOutro,
                            endereco,
                            usuario
                    );

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
                            for (Solicitacao s : lista) {
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
            }

        } while (opcao != 0);
    }

    // =========================
    // MENU GESTOR
    // =========================
    public static void menuGestor(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {

        int opcao;

        do {
            System.out.println("\n--- MENU GESTOR ---");
            System.out.println("1 - Listar solicitações");
            System.out.println("2 - Atualizar status");
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

                    System.out.println("Novo Status:");
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
            }

        } while (opcao != 0);
    }
}