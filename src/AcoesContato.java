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

    public String adicionarNovoContato() {
        Contato novoContato = criarNovoContato();
        acoes.getContatosCadastrados().add(novoContato);
        this.salvarContato(novoContato);

        return Feedback.getMensagem("sucesso","Contato salvo com sucesso!");
    }

    private boolean validarContato(Contato contato) throws Exception {
        if (contato.getNome().trim().isEmpty())
            throw new Exception(Feedback.getMensagem("erro","O contato deve receber um nome."));
        return true;
    }

    private Contato criarNovoContato() {
        Contato novoContato = new Contato();
        boolean contatoValido = false;
        while (!contatoValido) {
            try{
                novoContato.setId(getIdDisponivelContato());
                novoContato.setNome(setDadosContato("nome"));
                novoContato.setSobrenome(setDadosContato("sobrenome"));
                contatoValido = validarContato(novoContato);
                List<Telefone> telefonesContato = acoesTelefone.adicionarNovoTelefone();
                novoContato.setTelefones(telefonesContato);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return novoContato;
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
            System.err.printf(Feedback.getMensagem("erro", String.format("Não foi possível salvar o novo contato no arquivo %s%n.", acoes.getArquivo().getName())));
            System.err.println(Feedback.getMensagem("erro", "Por favor, reveja as permissões do arquivo e reinicie a aplicação."));
        }
    }

    private void salvarAlteracoesContatos(List<String> dados) {
        try {
            acoes.persistirDados(dados);
        } catch (IOException e) {
            System.err.printf(Feedback.getMensagem("erro", String.format("Não foi possível salvar o novo contato no arquivo %s%n.", acoes.getArquivo().getName())));
            System.err.println(Feedback.getMensagem("erro", "Por favor, reveja as permissões do arquivo e reinicie a aplicação."));
        }
    }

    private int encontarContatoPeloId(Long idContato) throws Exception {
        int contatoInteresse = -1;
        for (Contato contato : acoes.getContatosCadastrados()) {
            if (contato.getId().equals(idContato)) {
                contatoInteresse = acoes.getContatosCadastrados().indexOf(contato);
                break;
            }
        }
        if (contatoInteresse == -1) {
            throw new Exception(Feedback.getMensagem("erro",String.format("Não foi encontrado qualquer contato com o ID %d.", idContato)));
        }
        return contatoInteresse;
    }

    public String removerContato(Long idContato) {
        try {
            int indiceContatoInteresse = this.encontarContatoPeloId(idContato);
            Contato contatoInteresse = acoes.getContatosCadastrados().get(indiceContatoInteresse);
            for (Telefone telefoneContato : contatoInteresse.getTelefones()) {
                System.out.println(acoesTelefone.removerTelefonesCadastrados(telefoneContato.getId()));
            }
            acoes
                    .getStringContatosCadastrados()
                    .remove(indiceContatoInteresse);
            acoes
                    .getContatosCadastrados()
                    .remove(indiceContatoInteresse);
            salvarAlteracoesContatos(acoes.getStringContatosCadastrados());
            return Feedback.getMensagem("sucesso", String.format("Contato %s %s foi removido com sucesso!", contatoInteresse.getNome(), contatoInteresse.getSobrenome()));
        } catch (Exception e) {
            return Feedback.getMensagem("erro", String.format("Não foi encontrado qualquer contato com o ID %d.", idContato));
        }
    }

    private void escolherOperacaoEdicaoContato(Contato contato, int opcaoEscolhida, Menu menu) {
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
                Long idTelefoneInteresse = menu.montarMenuCapturarId(contato, "telefone", "editar");
                acoesTelefone.editarTelefoneCadastrado(idTelefoneInteresse, contato.getTelefones());
            }
            case 4 -> acoesTelefone.adicionarNovoTelefone(contato.getTelefones());

            case 5 -> {
                Long idTelefoneInteresse = menu.montarMenuCapturarId(contato, "telefone", "editar");
                System.out.println(acoesTelefone.removerTelefonesCadastrados(idTelefoneInteresse, contato.getTelefones()));
            }
            default -> System.err.println(Feedback.getMensagem("erro","Opção inválida."));
        }
    }

    public String editarContato(Long idContato, Menu menu) {
        try {
            int indiceContatoInteresse = encontarContatoPeloId(idContato);
            Contato contato = acoes.getContatosCadastrados().get(indiceContatoInteresse);
            int opcaoEscolhida = menu.montarMenuEdicaoContato(contato);
            escolherOperacaoEdicaoContato(contato, opcaoEscolhida, menu);
            validarContato(contato);
            String contatoFormatado = formatarContatoParaSalvar(contato);
            acoes.getStringContatosCadastrados().set(indiceContatoInteresse, contatoFormatado);
            salvarAlteracoesContatos(acoes.getStringContatosCadastrados());
        } catch (Exception e) {
            return e.getMessage();
        }
        return Feedback.getMensagem("sucesso","Alterações realizadas com sucesso!");
    }

    private String definirNomeSobrenomeContato(String atributo) {
        Scanner input = new Scanner(System.in);
        System.out.printf("Informe o %s do contato: ", atributo);
        return input.nextLine();
    }
}
