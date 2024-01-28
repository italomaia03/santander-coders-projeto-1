import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Acoes acoes;
    public Menu(Acoes acoes) {
        this.acoes = acoes;
    }

    private String montarCabecalho(String titulo) {
        int tamanhoMax = 30;
        int padding = (tamanhoMax - titulo.length()) / 2 - 1;

        String cabecalhoSuperior = String.format("%s%n", "#".repeat(tamanhoMax));
        String cabecalhoMeio = String.format("%s %s %s%n", "#".repeat(padding), titulo, "#".repeat(padding));
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

    public void montarMenuPrincipal(String feedbackMetodos) {
        String cabecalho = montarCabecalho("Agenda");
        String contatos = montarContatos();
        String acoes = montarAcoes();
        System.out.printf("%s%s%n%s%n%n%s%n%s%n","\n".repeat(20), cabecalho, contatos, acoes, feedbackMetodos);
    }

    public void montarMenuCriarContato() {
        System.out.print("\n".repeat(20));
        String cabecalho = montarCabecalho("Adicionar Contato");
        System.out.println(cabecalho);
    }

    public Long montarMenuCapturarId(String titulo, String objetoInteresse, String operacao){
        Scanner input = new Scanner(System.in);
        String cabecalho = montarCabecalho(titulo);
        String contatos = montarContatos();
        System.out.printf("%s%s%n%s", "\n".repeat(20),cabecalho, contatos);
        System.out.printf("Informe o ID do %s que deseja %s: ", objetoInteresse, operacao);
        return input.nextLong();
    }
    public Long montarMenuCapturarId(Contato contato, String objetoInteresse, String operacao){
        Scanner input = new Scanner(System.in);
        mostrarInformacoesContato(contato);
        System.out.printf("Informe o ID do %s que deseja %s: ", objetoInteresse, operacao);
        return input.nextLong();
    }

    public int montarMenuEdicaoContato(Contato contato) {
        Scanner input = new Scanner(System.in);
        mostrarInformacoesContato(contato);
        System.out.println("1 -> Editar nome");
        System.out.println("2 -> Editar sobrenome");
        System.out.println("3 -> Editar um dos telefones cadastrados");
        System.out.println("4 -> Adicionar novo telefone ao contato");
        System.out.println("5 -> Remover telefone do contato");
        System.out.print("Escolha o atributo do contato que deseja editar: ");

        return input.nextInt();
    }
    private void mostrarInformacoesContato(Contato contato) {
        System.out.printf("%s%s Contato a ser editado %s%n","\n".repeat(20), "=".repeat(4), "=".repeat(4));
        System.out.printf("%s | %s %s | %s%n", "ID", "Nome", "Sobrenome", "Telefones");
        System.out.printf("%d | %s %s | %s%n%" +
                "n", contato.getId(), contato.getNome(), contato.getSobrenome(), contato.getTelefones());
    }
}
