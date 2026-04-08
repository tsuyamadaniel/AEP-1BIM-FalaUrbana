import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServicoSolicitacoes servico = new ServicoSolicitacoes();

        int opcao;

        do {
            System.out.println("\n===== SISTEMA DE SOLICITAÇÕES =====");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Buscar por protocolo");
            System.out.println("4 - Confirmar problema");
            System.out.println("5 - Atualizar status");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();

                    System.out.print("Local (hospital/escola/outro): ");
                    String local = sc.nextLine();

                    System.out.print("Risco (baixo/medio/alto): ");
                    String risco = sc.nextLine();

                    servico.criarSolicitacao(descricao, local, risco);
                    break;

                case 2:
                    servico.listar();
                    break;

                case 3:
                    System.out.print("Protocolo: ");
                    String protocoloBusca = sc.nextLine();

                    var s = servico.buscarPorProtocolo(protocoloBusca);

                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("Não encontrada.");
                    }
                    break;

                case 4:
                    System.out.print("Protocolo: ");
                    String protocoloConf = sc.nextLine();
                    servico.confirmarSolicitacao(protocoloConf);
                    break;

                case 5:
                    System.out.print("Protocolo: ");
                    String protocoloStatus = sc.nextLine();

                    System.out.print("Novo status: ");
                    String status = sc.nextLine();

                    servico.atualizarStatus(protocoloStatus, status);
                    break;
            }

        } while (opcao != 0);

        sc.close();
    }
}