import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private int escolhaDoUsuario;

    private String montarCabecalho() {
        String agenda = "AGENDA";
        int tamanhoMax = 20;
        int padding = (tamanhoMax - agenda.length()) / 2 - 1;

        String cabecalhoSuperior = String.format("%s%n", "#".repeat(tamanhoMax));
        String cabecalhoMeio = String.format("%s %s %s%n", "#".repeat(padding), "AGENDA", "#".repeat(padding));
        return cabecalhoSuperior + cabecalhoMeio + cabecalhoSuperior;
    }

    private String montarContatos() {
        String contatosSalvos = String.format("%s Contatos %s%nId | Nome%n", ">".repeat(5), "<".repeat(5));;
        BufferedReader reader = null;
        try {
            String data;
            reader = new BufferedReader(new FileReader("src/database/contatos.txt"));
            while (true) {
                try {
                    if ((data = reader.readLine()) == null) {
                        break;
                    }
                    contatosSalvos += data + "\n";
                } catch (IOException e) {
                    System.out.println("Todos os contatos foram recuperados");
                    ;
                }
            }

        } catch (FileNotFoundException e) {
            return "\nNão há contatos salvos";
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
        System.out.printf("%s%s%n%n%s%n", cabecalho, contatos, acoes);
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
