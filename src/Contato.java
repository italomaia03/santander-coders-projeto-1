import java.util.List;

public class Contato {
    private Long id;
    private String nome;
    private String sobrenome;
    private List<Telefone> telefones;

    public Contato(Long id, String nome, String sobrenome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefones = telefones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}
