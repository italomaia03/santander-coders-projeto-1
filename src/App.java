import java.util.List;
import java.util.Scanner;

public class App {
    private final Acoes acoes;
    private final Menu menu;
    private final AcoesUsuario acoesUsuario;

    public App(String caminhoDoArquivo) {
        this.acoes = new Acoes(caminhoDoArquivo);
        this.menu = new Menu(this.acoes);
        AcoesTelefone acoesTelefone = new AcoesTelefone(this.acoes);
        this.acoesUsuario = new AcoesUsuario(this.acoes, acoesTelefone);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean ativo = true;
        while (ativo) {
            menu.montarMenu();
            System.out.print("Informe a opção que deseja: ");
            int escolhaDoUsuario = input.nextInt();
            switch (escolhaDoUsuario) {
                case 1 -> {
                    acoesUsuario.adicionarNovoContato();
                }
                case 2 -> {
                    Long idContato = menu.removerContatoMenu();
                    acoesUsuario.removerContato(idContato);
                }
                case 3 -> System.out.println("Editar contato");
                case 4 -> {
                    System.out.println("Saindo da aplicação...");
                    ativo = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        input.close();
    }
}
