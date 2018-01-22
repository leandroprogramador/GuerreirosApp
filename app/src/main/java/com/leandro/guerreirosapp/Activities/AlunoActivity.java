package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ConnectionHelper;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.R;

public class AlunoActivity extends AppCompatActivity {
    Toolbar toolbar;
    Aluno aluno;
    Gson gson = new Gson();
    ProgressBar progressBar;
    FloatingActionButton fabEdit;
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
        aluno = gson.fromJson(getIntent().getStringExtra("aluno"), Aluno.class);

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
                if(task.isComplete()){
                    CookieHelper.createCookieToast(AlunoActivity.this, "Sucesso!", "Aluno deltado com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
                    startActivity(new Intent(AlunoActivity.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        });
        if(!ConnectionHelper.checkConnection(this)){
            CookieHelper.createCookieToast(AlunoActivity.this, "Sucesso!", "Aluno deltado com sucesso!","OK", R.drawable.ic_done_all_white_24dp, R.color.colorSuccess);
            startActivity(new Intent(AlunoActivity.this, GuerreirosActivity.class).putExtra("fragment", "alunos").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
