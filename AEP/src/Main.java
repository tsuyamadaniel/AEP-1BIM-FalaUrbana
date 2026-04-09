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

    public static void pausar(Scanner sc) {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    public static void header(String titulo, Usuario usuario) {
        System.out.println("========================================");
        System.out.println(" FALAURBANA - SISTEMA DE SOLICITAÇÕES");
        System.out.println("========================================");

        if (usuario != null) {
            System.out.println("\nUsuário: " + usuario.getNome() + " (" + usuario.getTipo() + ")");
        }

        System.out.println("\n--- " + titulo + " ---\n");
    }

    public static void menu(String[] opcoes) {
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println("[" + (i + 1) + "] " + opcoes[i]);
        }
        System.out.println("----------------------------------------");
        System.out.print("Escolha: ");
    }

    public static String formatarEnum(Enum<?> e) {
        String nome = e.name().toLowerCase().replace("_", " ");
        return nome.substring(0, 1).toUpperCase() + nome.substring(1);
    }

    public static int lerOpcaoValida(Scanner sc, int max) {
        int opcao;
        while (true) {
            try {
                opcao = sc.nextInt();
                sc.nextLine();
                if (opcao >= 1 && opcao <= max) return opcao;
            } catch (Exception e) {
                sc.nextLine();
            }
            System.out.print("Opção inválida. Tente novamente: ");
        }
    }

    public static void mostrarLista(List<Solicitacao> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada.");
        } else {
            for (Solicitacao s : lista) {
                System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Admin", TipoUsuario.GESTOR, "000", "admin@email.com", "Admin123"));
        usuarios.add(new Usuario("User", TipoUsuario.CIDADAO, "000", "user@email.com", "User123"));

        int tipo;

        do {
            header("MENU PRINCIPAL", null);

            String[] opcoes = {
                    "Cidadão",
                    "Anônimo",
                    "Gestor",
                    "Encerrar sistema"
            };

            menu(opcoes);
            tipo = lerOpcaoValida(sc, opcoes.length);

            if (tipo == 4) break;

            Usuario usuario = null;

            if (tipo == 2) {
                usuario = new Usuario(TipoUsuario.ANONIMO);
            } else {
                TipoUsuario tipoUsuario = (tipo == 1) ? TipoUsuario.CIDADAO : TipoUsuario.GESTOR;

                System.out.println("\n[1] Login");
                System.out.println("[2] Cadastrar");
                System.out.print("Escolha: ");

                int op = lerOpcaoValida(sc, 2);

                if (op == 1) {
                    usuario = login(sc, tipoUsuario, usuarios);
                    if (usuario == null) continue;
                } else {
                    usuario = cadastrar(sc, tipoUsuario, usuarios);
                }
            }

            if (usuario.getTipo() == TipoUsuario.GESTOR) {
                menuGestor(sc, servico, usuario);
            } else {
                menuUsuario(sc, servico, usuario);
            }

        } while (true);

        System.out.println("Sistema encerrado.");
        sc.close();
    }

    // ================= CADASTRO =================
    public static Usuario cadastrar(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {

        header("CADASTRO", null);

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine();

            if (!validarEmail(email)) {
                System.out.println("Email inválido!");
            } else if (emailJaExiste(email, usuarios)) {
                System.out.println("Email já cadastrado!");
            } else {

                String senha;

                while (true) {
                    System.out.print("Senha: ");
                    senha = sc.nextLine();

                    if (!senhaSegura(senha)) {
                        System.out.println("Senha fraca! Use maiúscula, minúscula, número e caractere especial.");
                    } else break;
                }

                Usuario novo = new Usuario(nome, tipo, cpf, email, senha);
                usuarios.add(novo);

                System.out.println("\nCadastro realizado com sucesso!");
                pausar(sc);
                return novo;
            }
        }
    }

    public static Usuario login(Scanner sc, TipoUsuario tipo, List<Usuario> usuarios) {

        header("LOGIN", null);

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)
                    && u.getSenha().equals(senha)
                    && u.getTipo() == tipo) {

                System.out.println("\nLogin realizado!");
                pausar(sc);
                return u;
            }
        }

        System.out.println("\nUsuário não encontrado.");
        pausar(sc);
        return null;
    }

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean emailJaExiste(String email, List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) return true;
        }
        return false;
    }

    public static boolean senhaSegura(String senha) {
        return senha.length() >= 6 &&
                senha.matches(".*[A-Z].*") &&
                senha.matches(".*[a-z].*") &&
                senha.matches(".*\\d.*");
    }

    // ================= MENU USUÁRIO =================
    public static void menuUsuario(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {

        int opcao;

        String[] opcoes = {
                "Voltar",
                "Nova solicitação",
                "Buscar por protocolo",
                "Buscar por local",
                "Confirmar ocorrência",
                "Minhas solicitações"
        };

        do {
            header("MENU USUÁRIO", usuario);
            menu(opcoes);

            opcao = lerOpcaoValida(sc, opcoes.length);

            switch (opcao) {

                case 1:
                    break;

                case 2:
                    header("NOVA SOLICITAÇÃO", usuario);

                    if (usuario.isAnonimo()) {
                        System.out.println("Você está enviando uma solicitação anônima.");
                        System.out.println("Não será possível acompanhar depois.\n");
                    }

                    String descricao;

                    do {
                        System.out.println("Faça uma breve descrição do problema:");
                        System.out.println("Ex: Buraco grande na rua causando risco de acidente");
                        System.out.print("> ");
                        descricao = sc.nextLine();

                        if (descricao.length() < 10) {
                            System.out.println("Descrição muito curta.\n");
                        }

                    } while (descricao.length() < 10);

                    System.out.println("\nTipo de local:");
                    LocalTipo[] locais = LocalTipo.values();
                    for (int i = 0; i < locais.length; i++) {
                        System.out.println((i + 1) + " - " + formatarEnum(locais[i]));
                    }
                    LocalTipo localTipo = locais[lerOpcaoValida(sc, locais.length) - 1];

                    System.out.println("\nCategoria:");
                    Categoria[] categorias = Categoria.values();
                    for (int i = 0; i < categorias.length; i++) {
                        System.out.println((i + 1) + " - " + formatarEnum(categorias[i]));
                    }
                    Categoria categoria = categorias[lerOpcaoValida(sc, categorias.length) - 1];

                    System.out.println("\nEndereço:");

                    System.out.print("Rua: ");
                    String rua = sc.nextLine();

                    System.out.print("Número (opcional): ");
                    String numero = sc.nextLine();
                    if (numero.isEmpty()) numero = "S/N";

                    System.out.print("Bairro: ");
                    String bairro = sc.nextLine();

                    System.out.print("CEP (opcional): ");
                    String cep = sc.nextLine();

                    System.out.print("Ponto de referência (opcional): ");
                    String referencia = sc.nextLine();

                    Endereco endereco = new Endereco(rua, numero, cep, bairro, referencia);

                    System.out.println("\n--- CONFIRMAR SOLICITAÇÃO ---");
                    System.out.println("Descrição: " + descricao);
                    System.out.println("Local: " + formatarEnum(localTipo));
                    System.out.println("Categoria: " + formatarEnum(categoria));
                    System.out.println("Endereço: " + endereco);

                    System.out.print("\nConfirmar envio? (S/N): ");
                    String confirm = sc.nextLine();

                    if (!confirm.equalsIgnoreCase("S")) {
                        System.out.println("Solicitação cancelada.");
                        pausar(sc);
                        break;
                    }

                    servico.criarSolicitacao(descricao, categoria, localTipo, null, endereco, usuario);

                    System.out.println("\nSolicitação registrada com sucesso!");
                    System.out.println("Guarde o protocolo para acompanhamento.");
                    pausar(sc);
                    break;

                case 3:
                    System.out.print("Protocolo: ");
                    Solicitacao s = servico.buscarPorProtocolo(sc.nextLine());

                    if (s == null) {
                        System.out.println("Solicitação não encontrada.");
                    } else {
                        System.out.println(s);
                    }

                    pausar(sc);
                    break;

                case 4:
                    header("BUSCAR POR LOCAL", usuario);

                    System.out.println("Buscar por:");
                    System.out.println("[1] - Tipo de Local");
                    System.out.println("[2] - Bairro");

                    int opBusca = lerOpcaoValida(sc, 2);

                    if (opBusca == 1) {

                        System.out.println("\nTipo de local:");
                        LocalTipo[] locaisBusca = LocalTipo.values();

                        for (int i = 0; i < locaisBusca.length; i++) {
                            System.out.println((i + 1) + " - " + formatarEnum(locaisBusca[i]));
                        }

                        LocalTipo tipo = locaisBusca[lerOpcaoValida(sc, locaisBusca.length) - 1];

                        List<Solicitacao> lista = servico.buscarPorLocalTipo(tipo);

                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma solicitação encontrada.");
                        } else {
                            lista.forEach(System.out::println);
                        }

                    } else {

                        System.out.print("Digite o bairro: ");
                        String bairroBusca = sc.nextLine();

                        List<Solicitacao> lista = servico.buscarPorBairro(bairroBusca);

                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma solicitação encontrada.");
                        } else {
                            lista.forEach(System.out::println);
                        }
                    }

                    pausar(sc);
                    break;

                case 5:
                    System.out.println("Use essa opção caso o problema ainda NÃO foi resolvido.");
                    System.out.print("Protocolo: ");
                    servico.confirmarSolicitacao(sc.nextLine());
                    pausar(sc);
                    break;

                case 6:
                    if (usuario.isAnonimo()) {
                        System.out.println("Usuário anônimo não possui histórico.");
                    } else {
                        List<Solicitacao> minhas = servico.buscarPorUsuario(usuario);

                        if (minhas.isEmpty()) {
                            System.out.println("Você não possui solicitações.");
                        } else {
                            minhas.forEach(System.out::println);
                        }
                    }
                    pausar(sc);
                    break;
            }

        } while (opcao != 1);
    }

    // ================= MENU GESTOR =================
    public static void menuGestor(Scanner sc, ServicoSolicitacoes servico, Usuario usuario) {

        int opcao;

        String[] opcoes = {
                "Voltar",
                "Listar solicitações",
                "Atualizar status",
                "Buscar solicitações"
        };

        do {
            header("MENU GESTOR", usuario);
            menu(opcoes);

            opcao = lerOpcaoValida(sc, opcoes.length);

            switch (opcao) {

                case 1:
                    break;

                case 2:
                    servico.listar();
                    pausar(sc);
                    break;

                case 3:
                    System.out.print("Protocolo: ");
                    String protocolo = sc.nextLine();

                    System.out.println("\nStatus:");
                    Status[] statusList = Status.values();

                    for (int i = 0; i < statusList.length; i++) {
                        System.out.println((i + 1) + " - " + formatarEnum(statusList[i]));
                    }

                    Status status = statusList[lerOpcaoValida(sc, statusList.length) - 1];

                    System.out.print("Comentário: ");
                    String comentario = sc.nextLine();

                    servico.atualizarStatus(protocolo, status, comentario, usuario.getNome());

                    System.out.println("Status atualizado!");
                    pausar(sc);
                    break;

                case 4:
                    menuBusca(sc, servico);
                    break;
            }

        } while (opcao != 1);
    }

    public static void menuBusca(Scanner sc, ServicoSolicitacoes servico) {

        int opcao;

        String[] opcoes = {
                "Voltar",
                "Por tipo de local",
                "Por status",
                "Por prioridade",
                "Por bairro"
        };

        do {
            header("BUSCAR SOLICITAÇÕES", null);
            menu(opcoes);

            opcao = lerOpcaoValida(sc, opcoes.length);

            switch (opcao) {

                case 1:
                    break;

                case 2: // LOCAL
                    System.out.println("\nTipo de local:");
                    LocalTipo[] locais = LocalTipo.values();

                    for (int i = 0; i < locais.length; i++) {
                        System.out.println((i + 1) + " - " + formatarEnum(locais[i]));
                    }

                    LocalTipo tipoLocal = locais[lerOpcaoValida(sc, locais.length) - 1];

                    mostrarLista(servico.buscarPorLocalTipo(tipoLocal));
                    pausar(sc);
                    break;

                case 3: // STATUS
                    System.out.println("\nStatus:");
                    Status[] statusList = Status.values();

                    for (int i = 0; i < statusList.length; i++) {
                        System.out.println((i + 1) + " - " + formatarEnum(statusList[i]));
                    }

                    Status status = statusList[lerOpcaoValida(sc, statusList.length) - 1];

                    mostrarLista(servico.buscarPorStatus(status));
                    pausar(sc);
                    break;

                case 4: // PRIORIDADE
                    System.out.print("Digite a prioridade (ALTA/NORMAL): ");
                    String prioridade = sc.nextLine();

                    mostrarLista(servico.buscarPorPrioridade(prioridade));
                    pausar(sc);
                    break;

                case 5: // BAIRRO
                    System.out.print("Digite o bairro: ");
                    String bairro = sc.nextLine();

                    mostrarLista(servico.buscarPorBairro(bairro));
                    pausar(sc);
                    break;
            }

        } while (opcao != 1);
    }
}