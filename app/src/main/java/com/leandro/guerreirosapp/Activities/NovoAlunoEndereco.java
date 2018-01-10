package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.MaskType;
import com.leandro.guerreirosapp.Helper.MaskUtil;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.Model.Endereco;
import com.leandro.guerreirosapp.R;



public class NovoAlunoEndereco extends AppCompatActivity implements View.OnFocusChangeListener{

    Toolbar toolbar;
    EditText editCep, editEndereco, editBairro, editCidade;
    MaterialSpinner ufSpinner;
    ProgressBar progress;
    Gson gson;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno_endereco);

        toolbar = findViewById(R.id.my_toolbar);
        progress = findViewById(R.id.progressBar);
        gson = new Gson();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados de Endereço");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editCep = findViewById(R.id.edit_cep);
        editEndereco = findViewById(R.id.edit_end);
        editBairro = findViewById(R.id.edit_bairro);
        editCidade = findViewById(R.id.edit_cidade);
        ufSpinner = findViewById(R.id.uf_spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.ufs));
        ufSpinner.setAdapter(spinnerArrayAdapter);
        editCep.addTextChangedListener(MaskUtil.insert(editCep, MaskType.CEP));
        editCep.setOnFocusChangeListener(this);


    }

    public void cadastrar(View view){
        progress.setVisibility(View.VISIBLE);
        Aluno aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);
        String cep = editCep.getText().toString();
        String endereco = editEndereco.getText().toString();
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();
        String estado = ufSpinner.getItems().get(ufSpinner.getSelectedIndex()).toString();

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
        Intent intent = new Intent(NovoAlunoEndereco.this, NovoAlunoModalidade.class);
        intent.putExtra("aluno", json);
        intent.putExtra("action", "new");
        startActivity(intent);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(editCep.getText().length()>=7){
            progress.setVisibility(View.VISIBLE);
           new Thread(new Runnable() {
               @Override
               public void run() {
                   RequestQueue requestQueue = Volley.newRequestQueue(NovoAlunoEndereco.this);
                   String url = String.format("https://viacep.com.br/ws/%s/json/", MaskUtil.unmask(editCep.getText().toString()));

                   StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Endereco endereco = gson.fromJson(response, Endereco.class);
                           editEndereco.setText(endereco.getEndereco());
                           editBairro.setText(endereco.getBairro());
                           editCidade.setText(endereco.getCidade());

                           for(int i =0; i<ufSpinner.getItems().size(); i++){
                               if(ufSpinner.getItems().get(i).equals(endereco.getUf())){
                                   ufSpinner.setSelectedIndex(i + 1);

                               }
                           }
                            
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   progress.setVisibility(View.INVISIBLE);
                               }
                           });
                           

                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   progress.setVisibility(View.INVISIBLE);
                                   Toast.makeText(NovoAlunoEndereco.this, "Cep inválido, não foi possível encontrar endereço correspondente.", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                   });
                   requestQueue.add(request);
               }
           }).start();

          


        }
    }
}
