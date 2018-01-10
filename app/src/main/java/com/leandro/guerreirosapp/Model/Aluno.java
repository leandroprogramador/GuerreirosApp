package com.leandro.guerreirosapp.Model;

/**
 * Created by leani on 26/11/2017.
 */

public class Aluno extends Entidade {
    private String cadastradoPor;
    private long cadastradoEm;
    private boolean jiujitsu;
    private boolean funcional;
    private long ultimaAlteracao;
    private String modalidade;
    private boolean isencao;

    public Aluno() {
    }

    public Aluno(String nome, String telefone, String email, String modalidade) {
        super(nome, telefone, email);
        this.modalidade = modalidade;
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

    public boolean isIsencao() {
        return isencao;
    }

    public void setIsencao(boolean isencao) {
        this.isencao = isencao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
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


}
