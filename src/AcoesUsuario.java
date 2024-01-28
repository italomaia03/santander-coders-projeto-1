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
        if (contato.getNome().trim().isEmpty())
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
        return (acoes.getContatosCadastrados().isEmpty()) ? 1L :
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

        return String.format("%d | %s %s %s", contato.getId(), contato.getNome(), contato.getSobrenome(), telefonesContato);
    }

    private void salvarContato(Contato contato) {
        String contatoFormatado = formatarContatoParaSalvar(contato);
        try {
            acoes.getStringContatosCadastrados().add(contatoFormatado);
            acoes.persistirDados(contatoFormatado, true);
        } catch (IOException e) {
            System.err.printf("Não foi possível salvar o novo contato no arquivo %s%n.", acoes.getArquivo().getName());
            System.err.println("Por favor, reveja as permissões do arquivo e reinicie a aplicação.");
        }
    }

    private void salvarAlteracoesContatos(List<String> dados) {
        try {
            acoes.persistirDados(dados);
        } catch (IOException e) {
            System.err.printf("Não foi possível salvar o novo contato no arquivo %s%n.", acoes.getArquivo().getName());
            System.err.println("Por favor, reveja as permissões do arquivo e reinicie a aplicação.");
        }
    }

    public int encontarContatoPeloId(Long idContato) throws Exception {
        int contatoInteresse = -1;
        for (Contato contato : acoes.getContatosCadastrados()) {
            if (contato.getId().equals(idContato)) {
                contatoInteresse = acoes.getContatosCadastrados().indexOf(contato);
                break;
            }
        }
        if (contatoInteresse == -1) {
            throw new Exception(String.format("Não foi encontrado qualquer contato com o ID %d.", idContato));
        }
        return contatoInteresse;
    }

    public void removerContato(Long idContato) {
        try {
            int indiceContatoInteresse = this.encontarContatoPeloId(idContato);
            Contato contatoInteresse = acoes.getContatosCadastrados().get(indiceContatoInteresse);
            for (Telefone telefoneContato : contatoInteresse.getTelefones()){
                System.out.println(acoesTelefone.removerTelefonesCadastrados(telefoneContato.getId()));
            }
            acoes
                    .getStringContatosCadastrados()
                    .remove(indiceContatoInteresse);
            acoes
                    .getContatosCadastrados()
                    .remove(indiceContatoInteresse);
            salvarAlteracoesContatos(acoes.getStringContatosCadastrados());
            System.out.printf("Contato %s %s foi removido com sucesso!%n", contatoInteresse.getNome(), contatoInteresse.getSobrenome());
        } catch (Exception e) {
            System.err.printf("Não foi encontrado qualquer contato com o ID %d.", idContato);
        }
    }

    private void escolherOperacaoEdicaoContato(Contato contato, int opcaoEscolhida, Menu menu) throws Exception {
        switch (opcaoEscolhida) {
            case 1 -> {
                String novoNome = definirNomeSobrenomeContato("nome");
                contato.setNome(novoNome);
            }
            case 2 -> {
                String novoSobrenome = definirNomeSobrenomeContato("sobrenome");
                contato.setSobrenome(novoSobrenome);
            }
            case 3 -> {
                Long idTelefoneInteresse = menu.montarMenuCapturarId("Editar Contato", "telefone", "editar");
                acoesTelefone.editarTelefoneCadastrado(idTelefoneInteresse, contato.getTelefones());
            }
            case 4 -> {
                acoesTelefone.adicionarNovoTelefone(contato.getTelefones());
            }
            case 5 -> {
                Long idTelefoneInteresse = menu.montarMenuCapturarId("Editar Contato", "telefone", "editar");
                System.out.println(acoesTelefone.removerTelefonesCadastrados(idTelefoneInteresse, contato.getTelefones()));
            }
            default -> System.err.println("Opção inválida.");
        }
    }

    public void editarContato(Long idContato, Menu menu) {
        try {
            int indiceContatoInteresse = encontarContatoPeloId(idContato);
            Contato contato = acoes.getContatosCadastrados().get(indiceContatoInteresse);
            int opcaoEscolhida = menu.montarMenuEdicaoContato(contato);
            escolherOperacaoEdicaoContato(contato, opcaoEscolhida, menu);
            validarContato(contato);
            String contatoFormatado = formatarContatoParaSalvar(contato);
            acoes.getStringContatosCadastrados().set(indiceContatoInteresse, contatoFormatado);
            salvarAlteracoesContatos(acoes.getStringContatosCadastrados());
            System.out.println("Alterações realizadas com sucesso!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String definirNomeSobrenomeContato(String atributo) {
        Scanner input = new Scanner(System.in);
        System.out.printf("Informe o %s do contato: ", atributo);
        return input.nextLine();
    }
}
