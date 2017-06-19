package com.matheusfaleiro.heyu.communication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.matheusfaleiro.heyu.model.User;

public class FirebaseUserManagement {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    static boolean result;

    public static boolean registerNewUser(final Context context, final User userData) {
        firebaseAuth.createUserWithEmailAndPassword(userData.getUserName(), userData.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    result = true;
                    FirebaseDatabaseManagement.addUserInformationToDatabase(userData, getCurrentUser());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                result = false;
            }
        });

        return result;
    }

    public static boolean loginToHeyU(final Context context, User userData) {
        firebaseAuth.signInWithEmailAndPassword(userData.getUserName(), userData.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    result = true;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                result = false;
            }
        });

        return result;
    }

    public static String getCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser.getUid();
    }

    public static void logoutFromHeyU() {
        FirebaseAuth.getInstance().signOut();
    }
}
