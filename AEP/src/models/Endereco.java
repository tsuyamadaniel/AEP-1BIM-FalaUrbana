package models;

public class Endereco {

    private String rua;
    private String numero;
    private String cep;
    private String bairro;
    private String referencia;

    public Endereco(String rua, String numero, String cep, String bairro, String referencia) {
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.referencia = referencia;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getReferencia() {
        return referencia;
    }

    @Override
    public String toString() {

        String base = rua + ", " + numero + " - " + bairro;

        if (cep != null && !cep.isEmpty()) {
            base += " | CEP: " + cep;
        }

        if (referencia != null && !referencia.isEmpty()) {
            base += " | Ref: " + referencia;
        }

        return base;
    }
}