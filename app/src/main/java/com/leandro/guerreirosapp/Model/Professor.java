package com.leandro.guerreirosapp.Model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by leandro.araujo on 15/01/2018.
 */

public class Professor extends Entidade {

    private boolean jiujitsu = false;
    private boolean funcional = false;
    private boolean isencao = false;
    private long inicio;
    private List<Graduacao> graduacao;
    private String registro;

    public Professor(){}


    public boolean isJiujitsu() {
        return jiujitsu;
    }

    public void setJiujitsu(boolean jiujitsu) {
        this.jiujitsu = jiujitsu;
    }

    public boolean isFuncional() {
        return funcional;
    }

    public void setFuncional(boolean funcional) {
        this.funcional = funcional;
    }

    public boolean isIsencao() {
        return isencao;
    }

    public void setIsencao(boolean isencao) {
        this.isencao = isencao;
    }

    public long getInicio() {
        return inicio;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public List<Graduacao> getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(List<Graduacao> graduacao) {
        this.graduacao = graduacao;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }





}
