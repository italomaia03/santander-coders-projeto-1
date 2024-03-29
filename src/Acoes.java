import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Acoes {
    private final List<Contato> contatosCadastrados;
    private final List<Telefone> telefonesCadastrados;
    private final List<String> stringContatosCadastrados;
    private final File arquivo;

    public Acoes(String caminhoDoArquivo) {
        this.arquivo = verificarArquivo(caminhoDoArquivo);
        this.stringContatosCadastrados = new ArrayList<>();
        this.contatosCadastrados = carregarContatos();
        this.telefonesCadastrados = getTodosTelefonesCadastrados();
    }

    public File verificarArquivo(String caminhoDoArquivo) {
        File arquivo = new File(caminhoDoArquivo);

        if(arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        if (!arquivo.exists()) {
            System.out.println("\u001B[43mSISTEMA:\u001B[0m Não foi encontrado nenhum arquivo com este nome.");
            System.out.printf("\u001B[43mSISTEMA:\u001B[0m Criando um novo arquivo com o nome %s, no caminho %s...%n", arquivo.getName(), arquivo.getPath());
            try {
                if (arquivo.createNewFile()) {
                    System.out.println(Feedback.getMensagem("sucesso", String.format("O arquivo %s foi criado com sucesso.", arquivo.getName())));
                }
            } catch (IOException e) {
                System.err.println(Feedback.getMensagem("erro","Ocorreu um erro inesperado ao tentar criar o arquivo."));
                System.err.println(Feedback.getMensagem("erro","Por favor, verifique as permissões do usuário no diretório ou se o diretório informado existe."));
                return null;
            }
        }
        return arquivo;
    }

    private List<Contato> carregarContatos() {
        System.out.println("Carregando contatos...");
        List<Contato> contatosCadastrados = new ArrayList<>();
        BufferedReader reader = null;
        String dados;
        try {
            reader = new BufferedReader(new FileReader(this.arquivo));
            while ((dados = reader.readLine()) != null) {
                contatosCadastrados.add(capturarContato(dados));
                this.stringContatosCadastrados.add(dados);
            }
        } catch (IOException e) {
            System.err.println("Não foi possível acessar o arquivo com os contatos salvos.");
            System.err.println("Reveja as permissões do arquivo e reinicie a aplicação.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Ocorreu um erro inesperado durante a execução da aplicação. Por favor, reinicie o programa.");
                }
            }
        }
        System.out.println("Contatos carregados com sucesso!");
        return contatosCadastrados;
    }

    private List<Telefone> getTodosTelefonesCadastrados() {
        List<Telefone> telefones = new ArrayList<>();
        for (Contato contato : this.contatosCadastrados) {
            for (Telefone telefoneContato : contato.getTelefones()) {
                telefones.add(telefoneContato);
            }
        }
        return telefones;
    }

    private Contato capturarContato(String dados) {
        Long idContato = 0L;
        List<String> nomeCompleto = new ArrayList<>();
        List<List<String>> telefones = new ArrayList<>();

        dados = dados.replace(" | ", "\n");
        Scanner leitorLinhas = new Scanner(dados);
        int controle = 0;
        while (leitorLinhas.hasNextLine()) {
            switch (controle) {
                case 0 -> {
                    idContato = Long.parseLong(leitorLinhas.nextLine());
                    controle++;
                }
                case 1 -> {
                    nomeCompleto = capturarNomeSobrenome(leitorLinhas.nextLine());
                    controle++;
                }
                default -> {
                    if (controle % 2 == 0) {
                        telefones.add(new ArrayList<>());
                        telefones.get(telefones.size() - 1).add(leitorLinhas.nextLine());
                    } else {
                        telefones.get(telefones.size() - 1).add(leitorLinhas.nextLine());
                    }
                    controle++;
                }
            }
        }
        leitorLinhas.close();

        String nomeContato = nomeCompleto.get(0);
        String sobrenomeContato = nomeCompleto.get(1);
        List<Telefone> telefonesContato = converterStringParaTelefone(telefones);

        return new Contato(idContato, nomeContato, sobrenomeContato, telefonesContato);
    }

    private List<Telefone> converterStringParaTelefone(List<List<String>> telefones) {
        List<Telefone> telefonesContato = new ArrayList<>();

        for (List<String> telefone : telefones) {
            Long id = Long.parseLong(telefone.get(0));

            String ddd = telefone
                    .get(1)
                    .substring(0, 2);

            Long numero = Long
                    .parseLong(telefone
                            .get(1)
                            .substring(2)
                            .trim());

            telefonesContato.add(new Telefone(id, ddd, numero));
        }
        return telefonesContato;
    }

    private List<String> capturarNomeSobrenome(String dados) {
        dados = dados.replace(" ", "\n");
        Scanner sc = new Scanner(dados);
        List<String> nomeCompleto = new ArrayList<>();

        while (sc.hasNextLine()) {
            nomeCompleto.add(sc.nextLine());
        }
        sc.close();

        return nomeCompleto;
    }

    public void persistirDados(String dado, boolean acrescentarDados) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.arquivo, acrescentarDados));
        writer.write(dado);
        writer.newLine();
        writer.close();
    }

    public void persistirDados (List<String> dados) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.arquivo, false));

        for (String dado : dados) {
            writer.write(dado);
            writer.newLine();
        }
        writer.close();
    }

    public List<String> getStringContatosCadastrados() {
        return stringContatosCadastrados;
    }

    public List<Contato> getContatosCadastrados() {
        return contatosCadastrados;
    }

    public List<Telefone> getTelefonesCadastrados() {
        return telefonesCadastrados;
    }

    public File getArquivo() {
        return arquivo;
    }
}
