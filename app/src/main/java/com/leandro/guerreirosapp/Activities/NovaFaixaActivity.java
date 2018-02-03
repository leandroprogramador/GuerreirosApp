package com.leandro.guerreirosapp.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leandro.guerreirosapp.Adapter.Alunos.FaixasSpinnerAdapter;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.CookieHelper;
import com.leandro.guerreirosapp.Model.Faixa;
import com.leandro.guerreirosapp.Model.Graduacao;
import com.leandro.guerreirosapp.R;

import java.util.ArrayList;
import java.util.List;

public class NovaFaixaActivity extends AppCompatActivity {

    Toolbar toolbar;
    Calendar calendar;
    EditText editGraduacao;
    Spinner faixasSpinner, grauSpinner;
    FaixasSpinnerAdapter faixasSpinnerAdapter;
    List<Faixa> faixas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_add_faixa);
        toolbar =  findViewById(R.id.my_toolbar);
        editGraduacao = findViewById(R.id.edit_data_graduacao);
        faixasSpinner = findViewById(R.id.faixas_spinner);
        grauSpinner = findViewById(R.id.grau_spinner);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nova Faixa");

        calendar = Calendar.getInstance();
        editGraduacao.setText(String.format("%02d/%02d/%04d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));


        editGraduacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NovaFaixaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editGraduacao.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));

                        calendar.set(year,month,dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        faixasSpinnerAdapter =  new FaixasSpinnerAdapter(NovaFaixaActivity.this, R.layout.row_spinner_faixas,faixas);
        faixasSpinner.setAdapter(faixasSpinnerAdapter);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.graus));
        grauSpinner.setAdapter(spinnerArrayAdapter);


        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseConfig.getFirebase().getReference().child("Faixas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Faixa faixa = snapshot.getValue(Faixa.class);
                            faixasSpinnerAdapter.addItem(faixa);
                            faixasSpinner.setSelection(0);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(NovaFaixaActivity.this, "Erro ao carregar as faixas!", Toast.LENGTH_SHORT).show();
                    }

                });
            }



        }).start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_faixa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_close){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFaixa(View view){

            long data = calendar.getTimeInMillis();
            Faixa faixa = (Faixa) faixasSpinner.getSelectedItem();
            String grau = grauSpinner.getSelectedItem().toString();
            if(grau.equals("Selecione")){
                grau = "";
            }
            Graduacao graduacao = new Graduacao(faixa, data, grau);
            Gson gson = new Gson();
            String json = gson.toJson(graduacao);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("data",json);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();





    }

}
