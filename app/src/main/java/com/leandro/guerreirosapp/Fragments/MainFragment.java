package com.leandro.guerreirosapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.R;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {


    List<Aluno> alunos = new ArrayList<>();
    List<Aluno> alunosJiu = new ArrayList<>();
    List<Aluno> alunosFun = new ArrayList<>();

    TextView quantAlunos, txtAlunoLeg, quantJiu, quantFun;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        quantAlunos = view.findViewById(R.id.quant_alunos_text);
        txtAlunoLeg = view.findViewById(R.id.txt_alunos_leg);
        quantJiu = view.findViewById(R.id.quant_alunos_jiu);
        quantFun = view.findViewById(R.id.quant_alunos_fun);

        FirebaseConfig.getFirebase().getReference().child("alunos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alunos.clear();
                alunosFun.clear();
                alunosJiu.clear();
                for (DataSnapshot gSnapshots : dataSnapshot.getChildren()){
                    Aluno aluno = gSnapshots.getValue(Aluno.class);
                    alunos.add(aluno);
                }
                quantAlunos.setText(String.valueOf(alunos.size()));
                if(alunos.size() == 1){
                    txtAlunoLeg.setText("Aluno");
                } else{
                    txtAlunoLeg.setText("Alunos");
                }

                for(Aluno aluno : alunos){
                    if(aluno.isJiujitsu()){
                        alunosJiu.add(aluno);
                    }
                }
                quantJiu.setText(String.valueOf(alunosJiu.size()));

                for(Aluno aluno : alunos){
                    if(aluno.isFuncional()){
                        alunosFun.add(aluno);
                    }
                }

                quantFun.setText(String.valueOf(alunosFun.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        return view;
    }

}
