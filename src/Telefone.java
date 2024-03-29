public class Telefone {
    private Long id;
    private String ddd;
    private Long numero;

    public Telefone(Long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public String getDdd() {
        return ddd;
    }

    public Long getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return String.format("%d - (%s) %d",this.id, this.ddd, this.numero);
    }
}
