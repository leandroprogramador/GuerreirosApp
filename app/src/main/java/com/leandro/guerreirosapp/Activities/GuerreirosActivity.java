package com.leandro.guerreirosapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.leandro.guerreirosapp.Firebase.FirebaseConfig;
import com.leandro.guerreirosapp.Fragments.Alunos.AlunosListFragment;
import com.leandro.guerreirosapp.Fragments.MainFragment;
import com.leandro.guerreirosapp.Helper.SharedHelper;
import com.leandro.guerreirosapp.Model.Users;
import com.leandro.guerreirosapp.R;

public class GuerreirosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String userID;
    View headerLayout;
    TextView txtUserLogadoHeader, txtEmailLogadoHeader;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guerreiros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Guerreiros de Cristo");


        userID = getIntent().getStringExtra(getString(R.string.user_id));





        configureDrawer(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseConfig.getFirebase().getReference("users").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Users user = dataSnapshot.getValue(Users.class);
                            txtEmailLogadoHeader.setText(user.getEmail());
                            txtUserLogadoHeader.setText(user.getUser());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });
                }
                catch (Exception ex){

                }
            }
        }).start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        if(getIntent().getStringExtra("fragment") != null) {
            if (getIntent().getStringExtra("fragment").equals("alunos")) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AlunosListFragment(), "Alunos").commit();
            }
        }else {

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainFragment(), "Principal").commit();
        }




    }

    private void configureDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerLayout = LayoutInflater.from(this).inflate(R.layout.nav_header_guerreiros, navigationView);

        txtUserLogadoHeader = (TextView) navigationView.findViewById(R.id.user_header);
        txtEmailLogadoHeader = (TextView) navigationView.findViewById(R.id.email_header);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Deseja realmente sair?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            alert.setNegativeButton("Não", null);
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guerreiros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putString("userID", userID);
        if(id == R.id.nav_inicial){
            getSupportActionBar().setTitle("Guerreiros de Cristo");
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment()).commit();
        }
        else if (id == R.id.nav_alunos) {
            getSupportActionBar().setTitle("Alunos");
            AlunosListFragment alunosListFragment = new  AlunosListFragment();
            alunosListFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,alunosListFragment).commit();
        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_financeiro) {

        } else if (id == R.id.nav_lista) {

        } else if (id == R.id.nav_professores) {

        } else if (id == R.id.nav_usuarios) {

        }
        else if(id == R.id.nav_sair){
            FirebaseConfig.getFirebaseAuth().signOut();
            FirebaseConfig.getFirebase().getReference("session").child(userID).removeValue();
            SharedHelper.deleteData(GuerreirosActivity.this,getString(R.string.user_id));
            startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
