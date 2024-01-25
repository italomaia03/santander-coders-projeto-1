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

}
