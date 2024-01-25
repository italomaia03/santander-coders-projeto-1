import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private int escolhaDoUsuario;

    private String montarCabecalho() {
        String agenda = "AGENDA";
        int tamanhoMax = 40;
        int padding = (tamanhoMax - agenda.length()) / 2 - 1;

        String cabecalhoSuperior = String.format("%s%n", "#".repeat(tamanhoMax));
        String cabecalhoMeio = String.format("%s %s %s%n", "#".repeat(padding), "AGENDA", "#".repeat(padding));
        return cabecalhoSuperior + cabecalhoMeio + cabecalhoSuperior;
    }

    private String montarContatos() {
        String contatosSalvos = "";
        BufferedReader reader = null;
        try {
            String data;
            reader = new BufferedReader(new FileReader("src/contatos.txt"));
            while (true) {
                try {
                    if ((data = reader.readLine()) == null) {
                        break;
                    }
                    contatosSalvos += "\n" + data;
                } catch (IOException e) {
                    System.out.println("Todos os contatos foram recuperados");
                    ;
                }
            }

        } catch (FileNotFoundException e) {
            return "Não há contatos salvos\n";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Ocorreu um erro inesperado ao tentar ler a base de dados.");
                }
            }
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
        System.out.printf("%s%n%s%n%s", cabecalho, contatos, acoes);
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean ativo = true;
        while (ativo) {
            montarMenu();
            System.out.print("Informe a opção que deseja: ");
            this.escolhaDoUsuario = input.nextInt();
            switch (this.escolhaDoUsuario) {
                case 1 -> System.out.println("Adicionar contato");
                case 2 -> System.out.println("Remover contato");
                case 3 -> System.out.println("Editar contato");
                case 4 -> ativo = false;
                default -> System.out.println("Opção inválida.");
            }
        }
        input.close();
    }
}
