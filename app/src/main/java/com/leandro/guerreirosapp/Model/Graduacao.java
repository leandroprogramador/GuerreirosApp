package com.leandro.guerreirosapp.Model;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class Graduacao {
    Faixa faixa;
    long dataGraduacao;
    String grau;

    public Graduacao(Faixa faixa, long dataGraduacao, String grau) {
        this.faixa = faixa;
        this.dataGraduacao = dataGraduacao;
        this.grau = grau;
    }

    public Graduacao() {
    }

    public Faixa getFaixa() {
        return faixa;
    }

    public void setFaixa(Faixa faixa) {
        this.faixa = faixa;
    }

    public long getDataGraduacao() {
        return dataGraduacao;
    }

    public void setDataGraduacao(long dataGraduacao) {
        this.dataGraduacao = dataGraduacao;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }
}
