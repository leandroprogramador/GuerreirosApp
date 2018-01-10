package com.leandro.guerreirosapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leani on 26/11/2017.
 */

public class Endereco {
    @SerializedName("logradouro")
    private String endereco;
    @SerializedName("cep")
    private String cep;
    @SerializedName("uf")
    private String uf;
    @SerializedName("localidade")
    private String cidade;
    @SerializedName("bairro")
    private String bairro;
    private String numero;
    private String complemento;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
