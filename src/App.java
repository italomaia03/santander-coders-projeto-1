import java.util.List;
import java.util.Scanner;

public class App {
    private final Acoes acoes;
    private final AcoesUsuario acoesUsuario;

    public App(String caminhoDoArquivo) {
        this.acoes = new Acoes(caminhoDoArquivo);
        AcoesTelefone acoesTelefone = new AcoesTelefone(this.acoes);
        this.acoesUsuario = new AcoesUsuario(this.acoes, acoesTelefone);
    }

    private String montarCabecalho() {
        String agenda = "AGENDA";
        int tamanhoMax = 20;
        int padding = (tamanhoMax - agenda.length()) / 2 - 1;

        String cabecalhoSuperior = String.format("%s%n", "#".repeat(tamanhoMax));
        String cabecalhoMeio = String.format("%s %s %s%n", "#".repeat(padding), "AGENDA", "#".repeat(padding));
        return cabecalhoSuperior + cabecalhoMeio + cabecalhoSuperior;
    }

    private String montarContatos() {
        String contatosSalvos = String.format("%s Contatos %s%nId | Nome%n", ">".repeat(5), "<".repeat(5));
        List<Contato> contatos = acoes.getContatosCadastrados();
        for (Contato contato : contatos) {
            contatosSalvos += String.format("%d | %s %s%n", contato.getId(), contato.getNome(), contato.getSobrenome());
        }

        return contatosSalvos;
    }

    private String montarAcoes() {
        String cabecalho = String.format("%s %s %s%n", ">".repeat(5), "Menu", "<".repeat(5));
        String acoes = """
                1 - Adicionar Contato
                2 - Remover Contato
                3 - Editar Contato
                4 - Sair
                """;

        return cabecalho + acoes;
    }

    private void montarMenu() {
        String cabecalho = montarCabecalho();
        String contatos = montarContatos();
        String acoes = montarAcoes();
        System.out.printf("%s%s%n%n%s%n", cabecalho, contatos, acoes);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean ativo = true;
        while (ativo) {
            montarMenu();
            System.out.print("Informe a opção que deseja: ");
            int escolhaDoUsuario = input.nextInt();
            switch (escolhaDoUsuario) {
                case 1 -> {
                    acoesUsuario.adicionarNovoContato();
                }
                case 2 -> System.out.println("Remover contato");
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
