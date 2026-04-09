public class Usuario {

    private String nome;
    private TipoUsuario tipo;
    private String CPF;
    private String email;
    private String senha;

    // 🔹 CONSTRUTOR COMPLETO (usuário normal / gestor)
    public Usuario(String nome, TipoUsuario tipo, String CPF, String email, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
    }

    // 🔹 CONSTRUTOR PARA ANÔNIMO (IMPORTANTE)
    public Usuario(TipoUsuario tipo) {
        this.tipo = tipo;
        this.nome = null;
        this.CPF = null;
        this.email = null;
        this.senha = null;
    }

    // 🔹 GETTERS
    public String getNome() {
        return nome;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public boolean isAnonimo() {
        return tipo == TipoUsuario.ANONIMO;
    }

    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    // 🔹 SETTERS (opcional)
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}