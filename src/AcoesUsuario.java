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
    public void adicionarNovoContato() {
        boolean contatoValido = false;
        Contato novoContato;
        while (!contatoValido) {
            try {
                novoContato = criarNovoContato();
                contatoValido = validarContato(novoContato);
                acoes.getContatosCadastrados().add(novoContato);
                this.salvarContato(novoContato);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Contato salvo com sucesso!");
    }
    private boolean validarContato(Contato contato) throws Exception {
        if (contato.getNome() == null)
            throw new Exception("O contato deve receber um nome.");
        return true;
    }
    private Contato criarNovoContato() {
        Long idDisponivel = getIdDisponivelContato();
        String nomeContato = setDadosContato("nome");
        String sobrenomeContato = setDadosContato("sobrenome");
        List<Telefone> telefonesContato = acoesTelefone.adicionarNovoTelefone();
        return new Contato(idDisponivel, nomeContato, sobrenomeContato, telefonesContato);
    }
    private Long getIdDisponivelContato() {
        return (acoes.getContatosCadastrados().isEmpty()) ? 1L:
                acoes.getContatosCadastrados()
                        .get(acoes.getContatosCadastrados().size() - 1)
                        .getId() + 1;
    }
    private String setDadosContato(String dado) {
        Scanner input = new Scanner(System.in);
        System.out.printf("%nInforme o %s do contato: ", dado);

        return input.nextLine();
    }
    private String formatarContatoParaSalvar(Contato contato) {
        String telefonesContato = "";
        for (Telefone telefone : contato.getTelefones()) {
            telefonesContato += String.format("| %d | %s%d ", telefone.getId(), telefone.getDdd(), telefone.getNumero());
        }

        return String.format("%d | %s %s %s", contato.getId(), contato.getNome(), contato.getSobrenome(),  telefonesContato);
    }

    private void salvarContato(Contato contato) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(acoes.getArquivo(), true));
            writer.write(formatarContatoParaSalvar(contato));
            writer.newLine();
        } catch (IOException e) {
            System.err.printf("Não foi possível salvar o novo contato no arquivo %s%n.", acoes.getArquivo().getName());
            System.err.println("Por favor, reveja as permissões do arquivo e reinicie a aplicação.");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException("Ocorreu um erro inesperado.");
                }
            }
        }
    }
    public void removerContato(Long idContato){
        Contato contatoInteresse = null;
        for (Contato contato : acoes.getContatosCadastrados()) {
            if (contato.getId().equals(idContato)){
                contatoInteresse = contato;
                for (Telefone telefone : contato.getTelefones()) {
                    System.out.println(acoesTelefone.removerTelefone(telefone.getId()));
                }
                break;
            }
        }
        try {
            System.out.printf("Contato %s %s foi removido com sucesso!%n", contatoInteresse.getNome(), contatoInteresse.getSobrenome());
        } catch (NullPointerException e) {
            System.err.printf("Não foi encontrado qualquer contato com o ID %d.", idContato);
        }
    }
}
