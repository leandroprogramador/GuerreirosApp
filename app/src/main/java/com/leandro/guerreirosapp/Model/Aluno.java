package com.leandro.guerreirosapp.Model;

import java.util.List;

/**
 * Created by leani on 26/11/2017.
 */

public class Aluno extends Entidade {

    private boolean jiujitsu = false;
    private boolean funcional = false;
    private boolean isencao = false;
    private long inicio;
    private List<Graduacao> graduacao;
    private String registro;



    public Aluno() {
    }

    public boolean isIsencao() {
        return isencao;
    }

    public void setIsencao(boolean isencao) {
        this.isencao = isencao;
    }


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

    public List<Graduacao> getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(List<Graduacao> graduacao) {
        this.graduacao = graduacao;
    }

    public long getInicio() {
        return inicio;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }


}
