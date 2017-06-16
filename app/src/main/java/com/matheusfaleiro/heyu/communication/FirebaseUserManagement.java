package com.matheusfaleiro.heyu.communication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.matheusfaleiro.heyu.model.User;

public class FirebaseUserManagement {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static void registerNewUser(final Context context,User userData) {
        firebaseAuth.createUserWithEmailAndPassword(userData.getUserName(), userData.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "FINALMENTE", Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "DEU RUIM LEK", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
