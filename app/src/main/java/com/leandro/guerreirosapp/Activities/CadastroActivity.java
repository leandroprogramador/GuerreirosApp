package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Users;
import com.leandro.guerreirosapp.R;

public class CadastroActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editUser, editEmail, editSenha, editConfSenha;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        toolbar = findViewById(R.id.my_toolbar);
        editUser = findViewById(R.id.edit_user);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        editConfSenha = findViewById(R.id.edit_conf_senha);

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

            firebaseSignUp(user);
        }
    }

    private void firebaseSignUp(final Users user){
        FirebaseConfig.getFirebaseAuth().createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser userFirebase = task.getResult().getUser();
                    user.setIdUser(userFirebase.getUid().toString());
                    FirebaseConfig.getFirebase().getReference().child("users").child(userFirebase.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CadastroActivity.this, "Criado com sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
                else if(task.getException().getMessage().contains("The email address is already in use by another account.")){
                    Toast.makeText(CadastroActivity.this, "E-mail já esta cadastrado!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CadastroActivity.this, "Não foi possível criar o usuário!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
}
