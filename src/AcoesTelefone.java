import java.util.*;

public class AcoesTelefone {
    private final Acoes acoes;

    public AcoesTelefone(Acoes acoes) {
        this.acoes = acoes;
    }

    private Telefone criarNovoTelefone() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nInforme o DDD do telefone: ");
        String ddd = input.nextLine();
        System.out.print("Informe o número do telefone: ");
        Long numeroTelefone = input.nextLong();
        Long idTelefone = getIdDisponivelTelefone();

        return new Telefone(idTelefone, ddd, numeroTelefone);
    }

    private Telefone criarNovoTelefone(Long idTelefone) {
        Scanner input = new Scanner(System.in);
        System.out.print("Informe o DDD do telefone: ");
        String ddd = input.nextLine();
        System.out.print("Informe o número do telefone: ");
        Long numeroTelefone = input.nextLong();

        return new Telefone(idTelefone, ddd, numeroTelefone);
    }

    private Long getIdDisponivelTelefone() {
        return acoes.getTelefonesCadastrados().isEmpty() ? 1L :
                acoes.getTelefonesCadastrados()
                        .get(acoes.getTelefonesCadastrados().size() - 1)
                        .getId() + 1;
    }

    private boolean validarTelefones(Telefone telefone) throws Exception {
        String ddd = telefone.getDdd();
        String numeroTelefone = telefone.getNumero().toString();
        boolean telefonesIguais = compararTelefones(acoes.getTelefonesCadastrados(), telefone);

        if (ddd.length() != 2)
            throw new Exception(Feedback.getMensagem("erro", "DDD inválido. O DDD deve conter precisamente dois números.\nEx.: 88."));

        else if (numeroTelefone.length() < 8 || numeroTelefone.length() > 9)
            throw new Exception(Feedback.getMensagem("erro", "Número de telefone inválido. O número de telefone deve conter 8 (residencial fixo) ou 9 (celulares) dígitos.\nEx.: 35812356 (residencial), 999873456 (celular)."));

        else if (telefonesIguais) {
            throw new Exception(Feedback.getMensagem("erro", "Este número já se encontra cadastrado. Por favor, insira um número não cadastrado."));
        }

        return true;
    }

    private boolean compararTelefones(List<Telefone> telefones, Telefone novoTelefone) {
        String ddd = novoTelefone.getDdd();
        Long numeroTelefone = novoTelefone.getNumero();

        for (Telefone telefone : telefones) {
            if (ddd.equals(telefone.getDdd()) &&
                    numeroTelefone.equals(telefone.getNumero())) {
                return true;
            }
        }
        return false;
    }

    private Telefone verificarCriacaoTelefone() {
        boolean telefoneValido = false;
        Telefone novoTelefone = null;
        while (!telefoneValido) {
            try {
                novoTelefone = criarNovoTelefone();
                telefoneValido = validarTelefones(novoTelefone);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return novoTelefone;
    }

    private Telefone verificarCriacaoTelefone(Long idTelefone) {
        boolean telefoneValido = false;
        Telefone novoTelefone = null;
        while (!telefoneValido) {
            try {
                novoTelefone = criarNovoTelefone(idTelefone);
                telefoneValido = validarTelefones(novoTelefone);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return novoTelefone;
    }

    public List<Telefone> adicionarNovoTelefone() {
        List<Telefone> telefonesContato = new ArrayList<>();
        boolean realizarNovoCadastro = false;
        while (!realizarNovoCadastro) {
            Telefone novoTelefone = null;
            try {
                novoTelefone = verificarCriacaoTelefone();
                realizarNovoCadastro = realizarNovoCadastro();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            telefonesContato.add(novoTelefone);
            acoes.getTelefonesCadastrados().add(novoTelefone);
        }
        return telefonesContato;
    }

    public void adicionarNovoTelefone(List<Telefone> telefonesContato) {
        Telefone novoTelefone = null;
        boolean realizarNovoCadastro = false;

        while (!realizarNovoCadastro) {
            try {
                novoTelefone = verificarCriacaoTelefone();
                realizarNovoCadastro = realizarNovoCadastro();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            acoes.getTelefonesCadastrados().add(novoTelefone);
            telefonesContato.add(novoTelefone);
        }
    }

    private boolean realizarNovoCadastro() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Digite 'S' para adicionar mais um número, ou 'N' para finalizar o cadastro de telefones: ");
            char respostaUsuario = input.next().charAt(0);
            switch (respostaUsuario) {
                case 'S':
                    return false;
                case 'N':
                    return true;
                default:
                    System.out.println(Feedback.getMensagem("erro", "Opção inválida."));
            }
        }
    }

    private int encontarTelefonePeloId(Long idTelefone, List<Telefone> telefones) throws Exception {
        int telefoneInteresse = -1;
        for (Telefone telefone : telefones) {
            if (telefone.getId().equals(idTelefone)) {
                telefoneInteresse = telefones.indexOf(telefone);
                break;
            }
        }
        if (telefoneInteresse == -1) {
            throw new Exception(Feedback.getMensagem("erro", String.format("Não foi encontrado qualquer telefone com o ID %d.", idTelefone)));
        }
        return telefoneInteresse;
    }

    public String removerTelefonesCadastrados(Long idTelefone) {
        int indiceTelefoneInteresse = -1;
        try {
            indiceTelefoneInteresse = encontarTelefonePeloId(idTelefone, acoes.getTelefonesCadastrados());
            acoes.getTelefonesCadastrados().remove(indiceTelefoneInteresse);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Feedback.getMensagem("sucesso", String.format("Telefone %s foi removido dos telefones cadastrados com sucesso!", indiceTelefoneInteresse));
    }

    public String removerTelefonesCadastrados(Long idTelefone, List<Telefone> telefonesContato) {
        int indiceTelefoneInteresse = -1;
        try {
            indiceTelefoneInteresse = encontarTelefonePeloId(idTelefone, telefonesContato);
            telefonesContato.remove(indiceTelefoneInteresse);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println(removerTelefonesCadastrados(idTelefone));
        return Feedback.getMensagem("sucesso", String.format("Telefone %s foi removido dos telefones do contato com sucesso!", indiceTelefoneInteresse));
    }

    public void editarTelefoneCadastrado(Long idTelefoneInterese, List<Telefone> telefonesContato) {
        try {
            int indiceTelefoneContato = encontarTelefonePeloId(idTelefoneInterese, telefonesContato);
            int indiceTelefoneCadastrado = encontarTelefonePeloId(idTelefoneInterese, acoes.getTelefonesCadastrados());
            Telefone telefoneEditado = verificarCriacaoTelefone(idTelefoneInterese);
            telefonesContato.set(indiceTelefoneContato, telefoneEditado);
            acoes.getTelefonesCadastrados().set(indiceTelefoneCadastrado, telefoneEditado);
            System.out.println(Feedback.getMensagem("sucesso", String.format("%nTelefone %s foi modificado com sucesso!", telefoneEditado)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

