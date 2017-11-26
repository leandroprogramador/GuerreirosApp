package com.leandro.guerreirosapp.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by leani on 03/11/2017.
 */

public class FirebaseConfig {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;

    public static FirebaseDatabase getFirebase(){
        if(firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);


        }
        return firebaseDatabase;
    }


    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
