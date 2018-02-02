package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.leandro.guerreirosapp.Adapter.Alunos.FaixasAdapter;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ConnectionHelper;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Helper.DateHelper;
import com.leandro.guerreirosapp.Helper.MaskUtil;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.Model.Graduacao;
import com.leandro.guerreirosapp.R;

import java.util.List;

public class AlunoActivity extends AppCompatActivity {
    Toolbar toolbar;
    Aluno aluno;
    Gson gson = new Gson();
    ProgressBar progressBar;
    FloatingActionButton fabEdit;
    RecyclerView recyclerView;
    FaixasAdapter adapter;
    List<Graduacao> mDataSet;

    TextView txtAluno, txtSexo, txtDataNasc, txtIdade, tvEmail, tvTelefone, tvRG, tvCPF, tvEndereco, tvModalidade, tvProfessor, tvDataInicio, tvPeso, tvRegistro, tvIsento;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalhes");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        fabEdit = findViewById(R.id.fab);
        txtAluno = findViewById(R.id.tvNome);
        txtSexo = findViewById(R.id.txtSexo);
        txtDataNasc = findViewById(R.id.tvDataNasc);
        txtIdade = findViewById(R.id.tvIdade);
        tvEmail = findViewById(R.id.tvEmail);
        tvTelefone = findViewById(R.id.tvTelefone);
        tvRG = findViewById(R.id.tvRG);
        tvCPF = findViewById(R.id.tvCPF);
        tvEndereco = findViewById(R.id.tvEndereco);
        tvModalidade = findViewById(R.id.tvModalidade);
        tvProfessor = findViewById(R.id.tvProfessor);
        tvDataInicio = findViewById(R.id.tvDataInicio);
        tvPeso = findViewById(R.id.tvPeso);
        tvRegistro = findViewById(R.id.tvRegistro);
        tvIsento = findViewById(R.id.tvIsento);
        recyclerView = findViewById(R.id.recycler_graduacoes);

        aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);

        txtAluno.setText(aluno.getNome());
        if(aluno.getSexo().equals("M")){
            txtSexo.setText("Masculino");
        } else{
            txtSexo.setText("Feminino");
        }
        txtDataNasc.setText(DateHelper.timeStampToBr(aluno.getNasc()));
        if(DateHelper.getIdade(aluno.getNasc()) != 1) {
            txtIdade.setText(String.valueOf(DateHelper.getIdade(aluno.getNasc())) + " anos");
        } else{
            txtIdade.setText(String.valueOf(DateHelper.getIdade(aluno.getNasc())) + " ano");
        }

        if(aluno.getEmail().length() > 0){
            tvEmail.setText(aluno.getEmail());
        } else{
            tvEmail.setText("Não Informado");
        }

        if(aluno.getTelefone().length() > 0) {
            if (aluno.getTelefone().length() > 10) {
                tvTelefone.setText(MaskUtil.addMask(aluno.getTelefone(), MaskUtil.CELMask));
            } else {
                tvTelefone.setText(MaskUtil.addMask(aluno.getTelefone(), MaskUtil.TELEFONEMask));
            }
        }
        else{
            tvTelefone.setText("Não Informado");
        }
        tvRG.setText(MaskUtil.addMask(aluno.getRg(), MaskUtil.RGMask));
        tvCPF.setText(MaskUtil.addMask(aluno.getCpf(), MaskUtil.CPFMask));


        if(!aluno.getEndereco().getEndereco().equals("")) {
            tvEndereco.setText(String.format("%s, %s %s - %s - %s/%s - CEP: %s",
                    aluno.getEndereco().getEndereco(),
                    aluno.getEndereco().getNumero(),
                    aluno.getEndereco().getComplemento(),
                    aluno.getEndereco().getBairro(),
                    aluno.getEndereco().getCidade(),
                    aluno.getEndereco().getUf(),
                    MaskUtil.addMask(aluno.getEndereco().getCep(), MaskUtil.CEPMask)

            ));
        } else {
            tvEndereco.setText("Não Informado");
        }

        if(aluno.isFuncional() &&aluno.isJiujitsu() ){
            tvModalidade.setText("JiuJitsu/Funcional");
        } else if(aluno.isFuncional() && aluno.isJiujitsu()){
            tvModalidade.setText("Funcional");
        } else{
            tvModalidade.setText("Jiu Jitsu");
        }

        if(aluno.isProfessor()){
            tvProfessor.setText("Sim");
        } else{
            tvProfessor.setText("Não");
        }

        tvDataInicio.setText(DateHelper.timeStampToBr(aluno.getInicio()));
        tvPeso.setText(aluno.getPeso() + " Kg");
        tvRegistro.setText(aluno.getRegistro());

        if(aluno.isIsencao()){
            tvIsento.setText("Sim");
        } else{
            tvIsento.setText("Não");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = aluno.getGraduacao();
        if(mDataSet != null) {
            adapter = new FaixasAdapter(mDataSet, this);
            recyclerView.setAdapter(adapter);
        }

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlunoActivity.this, NovoAlunoDados.class);
                intent.putExtra("aluno", getIntent().getStringExtra("aluno"));
                intent.putExtra("action", "edit");
                startActivity(intent );
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_user,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(AlunoActivity.this);
            builder.setTitle("Delete");
            builder.setMessage("Deseja realmente deletar esse aluno?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteItem();
                }
            });
            builder.setNegativeButton("Não", null);

            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItem() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseConfig.getFirebase().getReference("alunos").child(aluno.getEntityID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    FirebaseConfig.getFirebase().goOffline();
                    FirebaseConfig.getFirebase().getReference("alunos").child(aluno.getEntityID()).removeValue();
                    FirebaseConfig.getFirebase().goOnline();
                    CookieHelper.createCookieToast(AlunoActivity.this, "Sucesso!", "Aluno deltado com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
                    startActivity(new Intent(AlunoActivity.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });


        if(!ConnectionHelper.checkConnection(this)){
            CookieHelper.createCookieToast(AlunoActivity.this, "Sucesso!", "Aluno deltado com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
            startActivity(new Intent(AlunoActivity.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
