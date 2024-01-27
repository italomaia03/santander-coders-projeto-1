import java.util.*;

public class AcoesTelefone {
    private final List<Telefone> telefonesCadastrados;
    private final Acoes acoes;

    public AcoesTelefone(Acoes acoes) {
        this.acoes = acoes;
        this.telefonesCadastrados = getTodosTelefonesCadastrados();
    }

    private List<Telefone> getTodosTelefonesCadastrados() {
        List<Telefone> telefones = new ArrayList<>();
        for (Contato contato : acoes.contatos) {
            for (Telefone telefoneContato : contato.getTelefones()) {
                telefones.add(telefoneContato);
            }
        }
        return telefones;
    }

    private Telefone criarNovoTelefone() {
        Scanner input = new Scanner(System.in);
        System.out.print("Informe o DDD do telefone: ");
        String ddd = input.nextLine();
        System.out.print("Informe o número do telefone: ");
        Long numeroTelefone = input.nextLong();
        Long idTelefone = getIdDisponivelTelefone();

        return new Telefone(idTelefone, ddd, numeroTelefone);
    }
    private Long getIdDisponivelTelefone() {
        return this.telefonesCadastrados.isEmpty() ? 1L :
                this.telefonesCadastrados
                        .get(telefonesCadastrados.size() - 1)
                        .getId() + 1;
    }
}

