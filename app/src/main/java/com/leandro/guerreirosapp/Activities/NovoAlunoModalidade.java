package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.leandro.guerreirosapp.Adapter.Alunos.FaixasAdapter;
import com.leandro.guerreirosapp.Adapter.Alunos.SimpleItemTouchHelperCallback;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ConnectionHelper;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Helper.DateHelper;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.Model.Graduacao;
import com.leandro.guerreirosapp.R;

import java.util.ArrayList;
import java.util.List;

public class NovoAlunoModalidade extends AppCompatActivity {

    Toolbar toolbar;
    Calendar calendar;
    EditText editPeso, editInicio, editRegistro;
    CheckBox chkJiu, chkFunc, chkIsento, chkProfessor;
    RecyclerView recyclerView;
    FaixasAdapter adapter;
    Gson gson = new Gson();
    List<Graduacao> mDataset = new ArrayList<>();
    Aluno aluno;
    String action;
    String acao = "";
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno_modalidade);

        toolbar = findViewById(R.id.my_toolbar);
        editPeso = findViewById(R.id.edit_peso);
        editInicio = findViewById(R.id.edit_inicio);
        editRegistro = findViewById(R.id.edit_registro);
        chkJiu = findViewById(R.id.checkbox_jiu);
        chkFunc = findViewById(R.id.checkbox_funcional);
        chkIsento = findViewById(R.id.checkbox_isento);
        chkProfessor = findViewById(R.id.checkbox_prof);
        recyclerView = findViewById(R.id.recycler_faixas);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados Esportivos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);
        action = getIntent().getStringExtra("action");

        editPeso.setText(aluno.getPeso());
        editRegistro.setText(aluno.getRegistro());
        editInicio.setText(DateHelper.timeStampToBr(aluno.getInicio()));
        if(aluno.isJiujitsu()){
            chkJiu.setChecked(true);
        }
        if(aluno.isFuncional()){
            chkFunc.setChecked(true);
        }

        if(aluno.isIsencao()){
            chkIsento.setChecked(true);
        }

        if(aluno.isProfessor()){
            chkProfessor.setChecked(true);
        }

        if(aluno.getGraduacao() != null){
            mDataset = aluno.getGraduacao();
        }


        calendar = Calendar.getInstance();
        editInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NovoAlunoModalidade.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editInicio.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
                        calendar.set(year,month,dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        adapter = new FaixasAdapter(mDataset, this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public void addFaixa(View view){

        startActivityForResult(new Intent(this, NovaFaixaActivity.class), 100);

    }

    public void cadastrar(View view){
        if(!chkFunc.isChecked() && !chkJiu.isChecked()) {
            CookieHelper.createCookieToast(this,"Erro!", "Selecione pelo menos uma modalidade!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);
        }
        else {
            String peso = editPeso.getText().toString();
            long dataInicio = calendar.getTimeInMillis();
            String registro = editRegistro.getText().toString();
            if (chkJiu.isChecked()) {
                aluno.setJiujitsu(true);
            } else{
                aluno.setJiujitsu(false);
            }


            if (chkFunc.isChecked()) {
                aluno.setFuncional(true);
            } else{
                aluno.setFuncional(false);
            }



            if (chkIsento.isChecked()) {
                aluno.setIsencao(true);
            }else{
                aluno.setIsencao(false);
            }

            if (chkProfessor.isChecked()) {
                aluno.setProfessor(true);
            }else{
                aluno.setProfessor(false);
            }

            aluno.setPeso(peso);
            aluno.setInicio(dataInicio);
            aluno.setRegistro(registro);
            aluno.setGraduacao(adapter.getmDataSet());

            if(chkProfessor.isChecked()){

                aluno.setProfessor(true);
                FirebaseConfig.getFirebase().getReference("professor").child(aluno.getEntityID()).setValue(aluno).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        }
                    }
                });
            }


            if(action.equals("new")){
                acao = "criado";
            }
            else{
                acao = "atualizado";
            }

            FirebaseConfig.getFirebase().getReference("alunos").child(aluno.getEntityID()).setValue(aluno).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete()){

                        CookieHelper.createCookieToast(NovoAlunoModalidade.this, "Sucesso!", "Aluno " + acao +" com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
                        startActivity(new Intent(NovoAlunoModalidade.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }
            });

            if(!ConnectionHelper.checkConnection(this)){
                CookieHelper.createCookieToast(NovoAlunoModalidade.this, "Sucesso!", "Aluno "+ acao +" com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
                startActivity(new Intent(NovoAlunoModalidade.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    Bundle res = data.getExtras();
                    String result = res.getString("data");

                    Graduacao graduacao = gson.fromJson(result, Graduacao.class);
                    adapter.add(graduacao);
                }
                break;
        }
    }
}
