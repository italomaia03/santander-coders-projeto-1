public class Telefone {
    private static long contador;
    private Long id;
    private String ddd;
    private Long numero;

    public Telefone(String ddd, Long numero) {
        this.id = ++contador;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone(Long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public static long getContador() {
        return contador;
    }

    public static void setContador(long contador) {
        Telefone.contador = contador;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
}
