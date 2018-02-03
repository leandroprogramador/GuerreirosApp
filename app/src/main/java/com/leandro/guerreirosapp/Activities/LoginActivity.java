package com.leandro.guerreirosapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ConnectionHelper;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Helper.SharedHelper;
import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Session;
import com.leandro.guerreirosapp.Model.Users;
import com.leandro.guerreirosapp.R;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passEditText;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseDatabase = FirebaseConfig.getFirebase();
        String userId = SharedHelper.getData(LoginActivity.this, getString(R.string.user_id));
        if(!userId.equals("")){
            startActivity(new Intent(LoginActivity.this, GuerreirosActivity.class).putExtra(getString(R.string.user_id), userId));
        }
        else{
            setContentView(R.layout.activity_login);
            emailEditText = (EditText) findViewById(R.id.edit_email);
            passEditText = (EditText) findViewById(R.id.edit_senha);

        }
//        if (firebaseAuth.getCurrentUser() == null) {
//            setContentView(R.layout.activity_login);
//            emailEditText = (EditText) findViewById(R.id.edit_email);
//            passEditText = (EditText) findViewById(R.id.edit_senha);
//
//        } else {
//
//            startActivity(new Intent(LoginActivity.this, GuerreirosActivity.class).putExtra(getString(R.string.user_id), userId));
//        }

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });


    }

    public void cadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void login(View view) {
        if(!ConnectionHelper.checkConnection(this)){
            CookieHelper.createCookieToast(this,"Erro", "Verifique sua conexão!", "Entendi",R.drawable.ic_signal_cellular_connected_no_internet_4_bar_white_24dp, R.color.colorPrimaryDark);

        }
        else if (!emailEditText.getText().toString().equals("") && !passEditText.getText().toString().equals("")) {
            Users user = new Users();
            user.setEmail(emailEditText.getText().toString());
            user.setPassword(ValidationHelper.toSha256(passEditText.getText().toString()));
            validarLogin(user);
        } else {
            CookieHelper.createCookieToast(this,"Erro", "Preencha o e-mail e a senha para fazer o login!", "Entendi",R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);
        }
    }

    public void validarLogin(final Users user) {
        progressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser userFirebase = task.getResult().getUser();

                                Session session = new Session(userFirebase.getUid(), String.valueOf(new Date().getTime()));
                                firebaseDatabase.getReference().child("session").child(session.getUserID()).setValue(session);
                                SharedHelper.saveData(LoginActivity.this, getString(R.string.user_id), session.getUserID());
                                startActivity(new Intent(LoginActivity.this, GuerreirosActivity.class).putExtra(getString(R.string.user_id), session.getUserID()));
                            } else {
                                if (task.getException().getMessage().contains("password is invalid")) {
                                    CookieHelper.createCookieToast(LoginActivity.this, "Erro", "Senha inválida!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);
                                } else if (task.getException().getMessage().contains("no user record corresponding")) {
                                    CookieHelper.createCookieToast(LoginActivity.this, "Erro", "Usuário não cadastrado!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);
                                } else if (task.getException().getMessage().contains("email address is badly formatted.")) {
                                    CookieHelper.createCookieToast(LoginActivity.this, "Erro", "Formato de e-mail inválido!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);
                                } else {
                                    CookieHelper.createCookieToast(LoginActivity.this, "Erro", "Não foi possível fazer o login. Tente novamente!", "Entendi", R.drawable.ic_signal_cellular_connected_no_internet_4_bar_white_24dp, R.color.colorPrimaryDark);
                                }
                            }
                        }
                    });
                } catch (Exception ex){
                    CookieHelper.createCookieToast(LoginActivity.this, "Erro", "Não foi possível fazer o login. Tente novamente!", "Entendi", R.drawable.ic_error_white_24dp, R.color.colorPrimaryDark);

                }
            }
        }).start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
