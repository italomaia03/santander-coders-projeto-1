import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AcoesUsuario {
    private final AcoesTelefone acoesTelefone;
    private final Acoes acoes;


    public AcoesUsuario(Acoes acoes, AcoesTelefone acoesTelefone) {
        this.acoes = acoes;
        this.acoesTelefone = acoesTelefone;
    }
    private Contato criarNovoContato() {
        Long idDisponivel = getIdDisponivelContato();
        String nomeContato = setDadosContato("nome");
        String sobrenomeContato = setDadosContato("sobrenome");
        List<Telefone> telefonesContato = acoesTelefone.adicionarNovoTelefone();
        return new Contato(idDisponivel, nomeContato, sobrenomeContato, telefonesContato);
    }
    private Long getIdDisponivelContato() {
        return (acoes.contatos.isEmpty()) ? 1L:
                acoes.contatos
                        .get(acoes.contatos.size() - 1)
                        .getId() + 1;
    }
    private String setDadosContato(String dado) {
        Scanner input = new Scanner(System.in);
        System.out.printf("%nInforme o %s do contato: ", dado);

        return input.nextLine();
    }
}
