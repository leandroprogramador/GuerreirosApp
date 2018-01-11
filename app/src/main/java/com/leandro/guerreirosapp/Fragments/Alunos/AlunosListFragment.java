package com.leandro.guerreirosapp.Fragments.Alunos;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.leandro.guerreirosapp.Activities.NovoAlunoDados;
import com.leandro.guerreirosapp.Adapter.Alunos.AlunosAdapter;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Activities.GuerreirosActivity;
import com.leandro.guerreirosapp.Model.Aluno;
import com.leandro.guerreirosapp.R;

import java.util.ArrayList;
import java.util.List;


public class AlunosListFragment extends Fragment implements AlunosAdapter.IAlunoClick {

    RecyclerView recyclerView;

    AlunosAdapter adapter;
    List<Aluno> mDataset;
    DatabaseReference databaseReference;
    final int CALL_PHONE_PERMISSION = 1;
    ProgressBar progress;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions((GuerreirosActivity)getContext(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
        }
        userID = getArguments().get("userID").toString();
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_alunos_list, container, false);
       FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), NovoAlunoDados.class).putExtra("userID",userID));

           }
       });
       recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDataset = new ArrayList<>();
        adapter = new AlunosAdapter(mDataset, AlunosListFragment.this);
        recyclerView.setAdapter(adapter);

        progress.setVisibility(View.VISIBLE);
       databaseReference =  FirebaseConfig.getFirebase().getReference();

        new Thread(new Runnable() {
            @Override
            public void run() {
                databaseReference.child("alunos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        adapter.removeAll();
                        for (DataSnapshot gSnapshots : dataSnapshot.getChildren()){
                             Aluno aluno = gSnapshots.getValue(Aluno.class);
                            adapter.addItem(aluno);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

            }
        }).start();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.INVISIBLE);
            }
        });

       return view;
    }

    @Override
    public void onPhoneClick(Object object, int position) {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions((GuerreirosActivity)getContext(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
        }
        else {
            Aluno aluno = (Aluno) object;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + aluno.getTelefone()));
            startActivity(intent);
        }
    }

    @Override
    public void onMailClick(Object object, int position) {
        Aluno aluno = (Aluno) object;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                "mailto:"+aluno.getEmail()));
        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
    }
}
