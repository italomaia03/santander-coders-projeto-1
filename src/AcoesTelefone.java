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
}

