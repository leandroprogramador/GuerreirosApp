package com.leandro.guerreirosapp.Model;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class Faixa {

    public Faixa(String idFaixa, String faixa, int cor) {
        this.idFaixa = idFaixa;
        this.faixa = faixa;
        this.cor = cor;
    }

    public Faixa(String faixa) {
        this.faixa = faixa;
    }

    public String getIdFaixa() {
        return idFaixa;
    }

    public void setIdFaixa(String idFaixa) {
        this.idFaixa = idFaixa;
    }

    private String idFaixa;
    private String faixa;
    private int cor;


    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }
}
