package models;

import enums.TipoUsuario;

public class Usuario {
    private String nome;
    private TipoUsuario tipo;
    private String CPF;
    private String email;
    private String senha;

    public Usuario(String nome, TipoUsuario tipo, String CPF, String email, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(TipoUsuario tipo) {
        this.tipo = tipo;
        this.nome = null;
        this.CPF = null;
        this.email = null;
        this.senha = null;
    }

    public String getNome() {
        return this.nome;
    }

    public TipoUsuario getTipo() {
        return this.tipo;
    }

    public boolean isAnonimo() {
        return this.tipo == TipoUsuario.ANONIMO;
    }

    public String getCPF() {
        return this.CPF;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

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

