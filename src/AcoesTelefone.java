import java.util.*;

public class AcoesTelefone {
    private final Acoes acoes;

    public AcoesTelefone(Acoes acoes) {
        this.acoes = acoes;
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
        return acoes.telefonesCadastrados.isEmpty() ? 1L :
                acoes.telefonesCadastrados
                        .get(acoes.telefonesCadastrados.size() - 1)
                        .getId() + 1;
    }
    private boolean validarTelefones(Telefone telefone) throws Exception {
        String ddd = telefone.getDdd();
        String numeroTelefone = telefone.getNumero().toString();
        boolean telefonesIguais = compararTelefones(acoes.telefonesCadastrados, telefone);

        if (ddd.length() != 2)
            throw new Exception("DDD inválido. O DDD deve conter precisamente dois números.\nEx.: 88.");

        else if (numeroTelefone.length() < 8 || numeroTelefone.length() > 9)
            throw new Exception("Número de telefone inválido. O número de telefone deve conter 8 (residencial fixo) ou 9 (celulares) dígitos.\nEx.: 3581-2356 (residencial), 999873456 (celular).");

        else if (telefonesIguais) {
            throw new Exception("Este número já se encontra cadastrado. Por favor, insira um número não cadastrado.");
        }

        return true;
    }
    private boolean compararTelefones(List<Telefone> telefones, Telefone novoTelefone) {
        String ddd = novoTelefone.getDdd();
        Long numeroTelefone = novoTelefone.getNumero();

        for (Telefone telefone : telefones){
            if(ddd.equals(telefone.getDdd()) &&
                    numeroTelefone.equals(telefone.getNumero())){
                return true;
            }
        }
        return false;
    }
    public List<Telefone> adicionarNovoTelefone() {
        List<Telefone> telefonesContato = new ArrayList<>();
        boolean telefoneValido = false;
        boolean finalizarCadastroTelefones = false;
        Telefone novoTelefone;
        while (!telefoneValido || !finalizarCadastroTelefones) {
            try {
                novoTelefone = criarNovoTelefone();
                telefoneValido = validarTelefones(novoTelefone);
                finalizarCadastroTelefones = realizarNovoCadastro();
                telefonesContato.add(novoTelefone);
                acoes.telefonesCadastrados.add(novoTelefone);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return telefonesContato;
    }
    private boolean realizarNovoCadastro() {
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print("Digite 'S' para adicionar mais um número, ou 'N' para finalizar o cadastro de telefones: ");
            char respostaUsuario = input.next().charAt(0);
            switch (respostaUsuario) {
                case 'S':
                    return false;
                case 'N':
                    return true;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    public String removerTelefone(Long idTelefone) {
        Telefone telefoneInteresse = null;
        for(Telefone telefone : acoes.telefonesCadastrados) {
            if (telefone.getId().equals(idTelefone)){
                telefoneInteresse = telefone;
                acoes.telefonesCadastrados.remove(telefone);
                break;
            }
        }
        return String.format("Telefone %s foi removido com sucesso!", telefoneInteresse);
    }
}

