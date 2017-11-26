package com.leandro.guerreirosapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.SharedHelper;
import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Session;
import com.leandro.guerreirosapp.Model.Users;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passEditText;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseDatabase = FirebaseConfig.getFirebase();

        if(firebaseAuth.getCurrentUser() == null) {
            setContentView(R.layout.activity_login);
            emailEditText = (EditText) findViewById(R.id.edit_email);
            passEditText = (EditText) findViewById(R.id.edit_senha);
        }
        else{
            String userId = SharedHelper.getData(LoginActivity.this, getString(R.string.user_id));
            startActivity(new Intent(this, GuerreirosActivity.class).putExtra(getString(R.string.user_id), userId));
        }

    }

    public void cadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void login(View view){
        if(!emailEditText.getText().toString().equals("") && !passEditText.getText().toString().equals("")){
            Users user = new Users();
            user.setEmail(emailEditText.getText().toString());
            user.setPassword(ValidationHelper.toSha256(passEditText.getText().toString()));
            validarLogin(user);
        } else{
            Toast.makeText(this, "Preencha o e-mail e a senha para fazer o login!", Toast.LENGTH_SHORT).show();
        }
    }

    public void validarLogin(final Users user){

        firebaseAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword() ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser userFirebase = task.getResult().getUser();

                    Session session = new Session(userFirebase.getUid(), String.valueOf(new Date().getTime()));
                    firebaseDatabase.getReference().child("session").child(session.getUserID()).setValue(session);
                    SharedHelper.saveData(LoginActivity.this,getString(R.string.user_id), session.getUserID());
                    startActivity(new Intent(LoginActivity.this, GuerreirosActivity.class).putExtra(getString(R.string.user_id), session.getUserID()));
                }
                else {
                    if(task.getException().getMessage().contains("password is invalid")) {
                        Toast.makeText(LoginActivity.this, "Senha inválida!", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.getException().getMessage().contains("no user record corresponding")){
                        Toast.makeText(LoginActivity.this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.getException().getMessage().contains("email address is badly formatted.")){
                        Toast.makeText(LoginActivity.this, "Formato de e-mail inválido!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Não foi possível fazer o login. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
