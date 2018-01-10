package com.leandro.guerreirosapp.Model;

/**
 * Created by leandro.araujo on 10/01/2018.
 */

public class Graduacao {
    Faixa faixa;
    long dataGraduacao;

    public Graduacao(Faixa faixa, long dataGraduacao) {
        this.faixa = faixa;
        this.dataGraduacao = dataGraduacao;
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
}
