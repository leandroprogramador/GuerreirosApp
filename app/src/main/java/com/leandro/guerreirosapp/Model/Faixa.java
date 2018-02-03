package com.leandro.guerreirosapp.Model;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class Faixa {

    public Faixa(String idFaixa, String faixa, String cor) {
        this.idFaixa = idFaixa;
        this.faixa = faixa;
        this.cor = cor;
    }

    public Faixa(String faixa) {
        this.faixa = faixa;
    }

    public Faixa() {
    }

    public String getIdFaixa() {
        return idFaixa;
    }

    public void setIdFaixa(String idFaixa) {
        this.idFaixa = idFaixa;
    }

    private String idFaixa;
    private String faixa;
    private String cor;


    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return getFaixa();
    }
}
