package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Helper.DateHelper;
import com.leandro.guerreirosapp.Helper.MaskType;
import com.leandro.guerreirosapp.Helper.MaskUtil;
import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.R;

public class NovoAlunoDados extends AppCompatActivity {

    Toolbar toolbar;
    EditText nomeEdit, emailEdit, telefoneEdit, rgEdit, cpfEdit, dataNascEdit;
    RadioButton radioBtM, radioBtF;
    ProgressBar progress;
    Calendar calendar = Calendar.getInstance();
    Aluno aluno;
    String action = "new";
    Gson gson = new Gson();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);
        toolbar = findViewById(R.id.my_toolbar);
        progress = findViewById(R.id.progressBar);
        nomeEdit = findViewById(R.id.edit_nome);
        emailEdit = findViewById(R.id.edit_email);
        telefoneEdit = findViewById(R.id.edit_telefone);
        rgEdit = findViewById(R.id.edit_rg);
        cpfEdit = findViewById(R.id.edit_cpf);
        dataNascEdit = findViewById(R.id.edit_nasc);
        radioBtF = findViewById(R.id.rdFeminino);
        radioBtM = findViewById(R.id.rdMasculino);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados Pessoais");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        dataNascEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NovoAlunoDados.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dataNascEdit.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
                        calendar.set(year,month,dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        telefoneEdit.addTextChangedListener(MaskUtil.insert(telefoneEdit, MaskType.TELEFONE));
        rgEdit.addTextChangedListener(MaskUtil.insert(rgEdit, MaskType.RG));
        cpfEdit.addTextChangedListener(MaskUtil.insert(cpfEdit, MaskType.CPF));

        action = getIntent().getStringExtra("action");
        if(action.equals("edit")){
            aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);
            nomeEdit.setText(aluno.getNome());
            emailEdit.setText(aluno.getEmail());
            telefoneEdit.setText(aluno.getTelefone());
            rgEdit.setText(aluno.getRg());
            cpfEdit.setText(aluno.getCpf());
            dataNascEdit.setText(DateHelper.timeStampToBr(aluno.getNasc()));
            if(aluno.getSexo().equals("M")){
                radioBtM.setChecked(true);
            }
            if(aluno.getSexo().equals("F")){
                radioBtM.setChecked(true);
            }
        } else{
            aluno = new Aluno();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    public void cadastrar(View view){
        progress.setVisibility(View.VISIBLE);
        String nome = nomeEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String telefone = telefoneEdit.getText().toString();
        String rg = rgEdit.getText().toString();
        String cpf = cpfEdit.getText().toString();
        String dataNasc = dataNascEdit.getText().toString();

        if(nome.equals("")){

            CookieHelper.createCookieToast(NovoAlunoDados.this, "Erro", "O campo nome é obrigatório!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);

            progress.setVisibility(View.INVISIBLE);
        }
        else if (!email.equals("") && !ValidationHelper.validarEmail(email)){
            CookieHelper.createCookieToast(NovoAlunoDados.this, "Erro", "O e-mail digitado é inválido!", "Entendi", R.drawable.ic_signal_cellular_connected_no_internet_4_bar_white_24dp, R.color.colorPrimaryDark);

            progress.setVisibility(View.INVISIBLE);
        }
        else{
            if(action.equals("new")) {
                final String id = nome.toLowerCase().trim().replace(" ", "_") + ValidationHelper.toSha256(nome + calendar.getTimeInMillis());
                aluno.setEntityID(id);
            }
            aluno.setCadastradoPor(getIntent().getStringExtra("userID"));
            aluno.setCadastradoEm(calendar.getTimeInMillis());
            aluno.setNome(nome);
            aluno.setEmail(email);
            aluno.setTelefone(MaskUtil.unmask(telefone));
            aluno.setRg(MaskUtil.unmask(rg));
            aluno.setCpf(MaskUtil.unmask(cpf));
            aluno.setNasc(calendar.getTimeInMillis());
            if(radioBtF.isChecked()){
                aluno.setSexo("F");
            }
            if(radioBtM.isChecked()){
                aluno.setSexo("M");
            }
            aluno.setUltimaAlteracao(aluno.getCadastradoEm());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    FirebaseConfig.getFirebase().getReference().child("alunos").child(aluno.getEntityID()).setValue(aluno).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                    String json = gson.toJson(aluno);
                    Intent intent = new Intent(NovoAlunoDados.this, NovoAlunoEndereco.class);
                    intent.putExtra("aluno", json);
                    intent.putExtra("action", action);
                    startActivity(intent);
                }
            }).start();





            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.INVISIBLE);
                }
            });

        }

    }
}
