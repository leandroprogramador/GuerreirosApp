package com.leandro.guerreirosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Users;

public class CadastroActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editUser, editEmail, editSenha, editConfSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        editUser = (EditText) findViewById(R.id.edit_user);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editSenha = (EditText) findViewById(R.id.edit_senha);
        editConfSenha = (EditText) findViewById(R.id.edit_conf_senha);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Novo Usuário");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void cadastrar(View view){
        if(editSenha.getText().toString().length() < 6){
            Toast.makeText(this, "Senha deve possuir pelo menos 6 caracteres!", Toast.LENGTH_SHORT).show();
        }
        else if(!editSenha.getText().toString().equals(editConfSenha.getText().toString())){
            Toast.makeText(this, "A confirmação da senha não corresponde!", Toast.LENGTH_SHORT).show();
        }
        else if(!ValidationHelper.validarEmail(editEmail.getText().toString())){
            Toast.makeText(this, "Insira um e-mail válido!", Toast.LENGTH_SHORT).show();
        }
        else{
            Users user = new Users();
            user.setUser(editUser.getText().toString());
            user.setEmail(editEmail.getText().toString());
            user.setPassword(ValidationHelper.toSha256(editSenha.getText().toString()));
        }
    }
}
