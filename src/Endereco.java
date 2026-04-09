public class Endereco {
    private String rua;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco(String rua, String numero, String cep, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String toString() {
        return rua + ", " + numero + " - " + bairro + " - " + cidade + "/" + estado + " - CEP: " + cep;
    }
}
