import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();

        System.out.println("===== SISTEMA DE SOLICITAÇÕES =====");

        Usuario usuario = escolherTipoUsuario(sc);

        if (usuario.getTipo() == TipoUsuario.GESTOR) {
            menuGestor(sc, servico, usuario);
        } else {
            menuUsuario(sc, servico, usuario);
        }

        sc.close();
    }

    // =========================
    // ESCOLHA DO USUÁRIO
    // =========================
    public static Usuario escolherTipoUsuario(Scanner sc) {


        System.out.println("1 - Cidadão");
        System.out.println("2 - Anônimo");
        System.out.println("3 - Gestor");
        System.out.println("Escolha o tipo de usuário:");

        int opcao = sc.nextInt();
        sc.nextLine();

        if (opcao == 1) {
            System.out.print("Nome: ");
            return new Usuario(sc.nextLine(), TipoUsuario.CIDADAO);
        } else if (opcao == 2) {
            return new Usuario(null, TipoUsuario.ANONIMO);
        } else {
            return new Usuario("Gestor", TipoUsuario.GESTOR);
        }
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
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();

                    System.out.print("Local: ");
                    String local = sc.nextLine();

                    System.out.println("Categoria:");
                    System.out.println("1 - ILUMINACAO");
                    System.out.println("2 - BURACO");
                    System.out.println("3 - LIMPEZA");
                    System.out.println("4 - SAUDE");
                    System.out.println("5 - SEGURANCA");

                    int cat = sc.nextInt();
                    sc.nextLine();

                    Categoria categoria = Categoria.values()[cat - 1];

                    servico.criarSolicitacao(descricao, categoria, local, usuario);
                    break;

                case 2:
                    System.out.print("Protocolo: ");
                    String protocoloBusca = sc.nextLine();

                    Solicitacao s = servico.buscarPorProtocolo(protocoloBusca);

                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("Não encontrada.");
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
            System.out.println("0 - Sair");

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

                    Status status = Status.values()[st];

                    System.out.print("Comentário: ");
                    String comentario = sc.nextLine();

                    servico.atualizarStatus(protocolo, status, comentario, usuario.getNome());
                    break;
            }

        } while (opcao != 0);
    }
}