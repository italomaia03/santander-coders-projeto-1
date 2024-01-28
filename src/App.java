import java.util.Scanner;

public class App {
    private final Menu menu;
    private final AcoesUsuario acoesUsuario;

    public App(String caminhoDoArquivo) {
        Acoes acoes = new Acoes(caminhoDoArquivo);
        this.menu = new Menu(acoes);
        AcoesTelefone acoesTelefone = new AcoesTelefone(acoes);
        this.acoesUsuario = new AcoesUsuario(acoes, acoesTelefone);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean ativo = true;
        while (ativo) {
            menu.montarMenuPrincipal();
            System.out.print("Informe a opção que deseja: ");
            int escolhaDoUsuario = input.nextInt();
            switch (escolhaDoUsuario) {
                case 1 -> {
                    menu.montarMenuCriarContato();
                    acoesUsuario.adicionarNovoContato();
                }
                case 2 -> {
                    Long idContato = menu.montarMenuCapturarId("Remover Contato", "contato","remover");
                    acoesUsuario.removerContato(idContato);
                }
                case 3 -> {
                    Long idContato = menu.montarMenuCapturarId("Editar contato","contato", "editar");
                    acoesUsuario.editarContato(idContato, this.menu);

                }
                case 4 -> {
                    System.out.println("Saindo da aplicação...");
                    ativo = false;
                }
                default -> System.err.println("Opção inválida.");
            }
        }
        input.close();
    }
}
