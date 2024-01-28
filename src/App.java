import java.util.Scanner;

public class App {
    private final Menu menu;
    private final AcoesContato acoesUsuario;

    public App() {
        Acoes acoes = receberCaminhoArquivo();
        this.menu = new Menu(acoes);
        AcoesTelefone acoesTelefone = new AcoesTelefone(acoes);
        this.acoesUsuario = new AcoesContato(acoes, acoesTelefone);
    }

    private Acoes receberCaminhoArquivo() {
        Scanner input = new Scanner(System.in);
        System.out.println("\u001B[33mATENÇÃO:\u001B[0m Caso não seja informado nenhum nenhum arquivo,\n" +
                "a aplicação criará um arquivo padrão no caminho:");
        System.out.println("\u001B[47m\u001B[30mCaminho padrão:\u001B[0m diretorio_da_aplicacao/database/contatos.txt");
        System.out.println("Por favor, informe o caminho do arquivo para persistir os dados da aplicação. ");
        System.out.println("\u001B[47m\u001B[30mEx.:\u001B[0m Linux, Mac, Powershell -> ~/diretorio_do_arquivo/arquivo.txt");
        System.out.println("\u001B[47m\u001B[30mEx.:\u001B[0m CMD -> diretorio_do_arquivo\\arquivo.txt");
        System.out.print("\u001B[47m\u001B[30mCaminho do arquivo:\u001B[0m ");
        String caminhoDoArquivo = input.nextLine();

        if (caminhoDoArquivo.trim().isEmpty()) {
            caminhoDoArquivo = "./database/contatos.txt";
        }

        return new Acoes(caminhoDoArquivo);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean ativo = true;
        String feedbackMetodo = "";
        while (ativo) {
            try {
                menu.montarMenuPrincipal(feedbackMetodo);
                System.out.print("Informe a opção que deseja: ");
                int escolhaDoUsuario = input.nextInt();
                switch (escolhaDoUsuario) {
                    case 1 -> {
                        menu.montarMenuCriarContato();
                        feedbackMetodo = acoesUsuario.adicionarNovoContato();
                    }
                    case 2 -> {
                        Long idContato = menu.montarMenuCapturarId("Remover Contato", "contato", "remover");
                        feedbackMetodo = acoesUsuario.removerContato(idContato);
                    }
                    case 3 -> {
                        Long idContato = menu.montarMenuCapturarId("Editar contato", "contato", "editar");
                        feedbackMetodo = acoesUsuario.editarContato(idContato, this.menu);
                    }
                    case 4 -> {
                        System.out.println("Saindo da aplicação...");
                        ativo = false;
                    }
                    default -> System.err.println(Feedback.getMensagem("erro", "Opção inválida."));
                }
            } catch (Exception e) {
                input.nextLine();
                feedbackMetodo = (Feedback.getMensagem("erro", "Por favor, informe um dos números referentes às opções."));
            }
        }
        input.close();
    }
}
