package com.leandro.guerreirosapp.Activities;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.MaskType;
import com.leandro.guerreirosapp.Helper.MaskUtil;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.Model.Endereco;
import com.leandro.guerreirosapp.R;

public class NovoAlunoEndereco extends AppCompatActivity {

    Toolbar toolbar;
    EditText editCep, editEndereco, editBairro, editCidade, editEstado;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno_endereco);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados de Endereço");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editCep = (EditText) findViewById(R.id.edit_cep);
        editEndereco = (EditText) findViewById(R.id.edit_end);
        editBairro = (EditText) findViewById(R.id.edit_bairro);
        editCidade = (EditText) findViewById(R.id.edit_cidade);
        editEstado = (EditText) findViewById(R.id.edit_uf);

        editCep.addTextChangedListener(MaskUtil.insert(editCep, MaskType.CEP));
    }

    public void cadastrar(View view){
        progress.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        Aluno aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);
        String cep = editCep.getText().toString();
        String endereco = editEndereco.getText().toString();
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();
        String estado = editEstado.getText().toString();

        Endereco mEndereco = new Endereco();
        mEndereco.setCep(cep);
        mEndereco.setEndereco(endereco);
        mEndereco.setBairro(bairro);
        mEndereco.setCidade(cidade);
        mEndereco.setUf(estado);

        aluno.setEndereco(mEndereco);
        aluno.setUltimaAlteracao(Calendar.getInstance().getTimeInMillis());


        FirebaseConfig.getFirebase().getReference().child("alunos").child(aluno.getEntityID()).setValue(aluno).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress.setVisibility(View.INVISIBLE);
            }

        });
        
        String json = gson.toJson(aluno);
        Intent intent = new Intent(NovoAlunoEndereco.this, NovoAlunoEndereco.class);
        intent.putExtra("aluno", json);
        intent.putExtra("action", "new");
        startActivity(intent);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
