package com.leandro.guerreirosapp.Model;

/**
 * Created by leani on 26/11/2017.
 */

public class Entidade {
    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    private String entityID;
    private String nome;
    private String telefone;
    private String email;
    private String rg;
    private String cpf;
    private long nasc;
    private String sexo;
    private String peso;
    private Endereco endereco = new Endereco();
    private long ultimaAlteracao;
    private String cadastradoPor;
    private long cadastradoEm;

    public Entidade(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    private String foto;

    public Entidade() {
    }

    public Entidade(String nome, String telefone, String email, String rg, String cpf, long nasc, String sexo, String peso, Endereco endereco, String foto) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.nasc = nasc;
        this.sexo = sexo;
        this.peso = peso;
        this.endereco = endereco;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public long getNasc() {
        return nasc;
    }

    public void setNasc(long nasc) {
        this.nasc = nasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public long getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public void setUltimaAlteracao(long ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public String getCadastradoPor() {
        return cadastradoPor;
    }

    public void setCadastradoPor(String cadastradoPor) {
        this.cadastradoPor = cadastradoPor;
    }

    public long getCadastradoEm() {
        return cadastradoEm;
    }

    public void setCadastradoEm(long cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
    }
}
