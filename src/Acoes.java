import java.io.File;
import java.io.IOException;
import java.util.List;

public class Acoes {
    protected List<Contato> contatos;
    protected File arquivo;

    public Acoes(String caminhoDoArquivo) {
        this.arquivo = verificarArquivo(caminhoDoArquivo);
    }

    public File verificarArquivo(String caminhoDoArquivo) {
        File arquivo = new File(caminhoDoArquivo);

        if (!arquivo.exists()) {
            System.out.println("Não foi encontrado nenhum arquivo com este nome.");
            System.out.printf("Criando um novo arquivo com o nome %s, no caminho %s...%n", arquivo.getName(), arquivo.getPath());
            try {
                if (arquivo.createNewFile()) {
                    System.out.printf("O arquivo %s foi criado com sucesso.%n", arquivo.getName());
                }
            } catch (IOException e) {
                System.err.println("Ocorreu um erro inesperado ao tentar criar o arquivo.");
                return null;
            }
        }
        return arquivo;
    }


}
