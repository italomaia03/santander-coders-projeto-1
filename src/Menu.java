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

    public void montarMenuPrincipal() {
        String cabecalho = montarCabecalho("Agenda");
        String contatos = montarContatos();
        String acoes = montarAcoes();
        System.out.printf("%s%s%n%n%s%n", cabecalho, contatos, acoes);
    }

    public void montarMenuCriarContato() {
        String cabecalho = montarCabecalho("Adicionar Contato");
        System.out.println(cabecalho);
    }

    public Long montarMenuCapturarId(String titulo){
        Scanner input = new Scanner(System.in);
        String cabecalho = montarCabecalho(titulo);
        String contatos = montarContatos();
        System.out.printf("%s%n%s",cabecalho, contatos);
        System.out.print("Informe o ID do contato que deseja remover: ");
        return input.nextLong();
    }
}
