package com.leandro.guerreirosapp.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leandro.guerreirosapp.Adapter.Alunos.FaixasAdapter;
import com.leandro.guerreirosapp.Adapter.Alunos.SimpleItemTouchHelperCallback;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Helper.ValidationHelper;
import com.leandro.guerreirosapp.Model.Faixa;
import com.leandro.guerreirosapp.Model.Graduacao;
import com.leandro.guerreirosapp.R;

import java.util.ArrayList;
import java.util.List;

public class NovoAlunoModalidade extends AppCompatActivity {

    Toolbar toolbar;
    Calendar calendar, calendarGraduacao ;
    EditText editPeso, editInicio, editRegistro;
    CheckBox chkJiu, chkFunc;
    RecyclerView recyclerView;
    FaixasAdapter adapter;
    List<Graduacao> mDataset = new ArrayList<>();
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
        recyclerView = findViewById(R.id.recycler_faixas);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataset.add(new Graduacao(new Faixa("Branca"), Calendar.getInstance().getTimeInMillis()));
        mDataset.add(new Graduacao(new Faixa("Amarela/Branca"), Calendar.getInstance().getTimeInMillis()));
        mDataset.add(new Graduacao(new Faixa("Amarela"), Calendar.getInstance().getTimeInMillis()));
        mDataset.add(new Graduacao(new Faixa("Amarela/Preta"), Calendar.getInstance().getTimeInMillis()));

        adapter = new FaixasAdapter(mDataset);
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados Esportivos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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


        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


    }


    public void addFaixa(View view){

        startActivityForResult(new Intent(this, NovaFaixaActivity.class), 1);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
