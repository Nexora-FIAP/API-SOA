package com.br.nexora.nexora.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class Cliente {
    private String nome;
    private int cpf;
    private String email;
    private Date dataNascimento;
    private PerfilInvestidor perfilInvestidor;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public PerfilInvestidor getPerfilInvestidor() {
        return perfilInvestidor;
    }

    public void setPerfilInvestidor(PerfilInvestidor perfilInvestidor) {
        this.perfilInvestidor = perfilInvestidor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}