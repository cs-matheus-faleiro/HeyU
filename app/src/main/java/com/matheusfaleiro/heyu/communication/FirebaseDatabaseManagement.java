package com.matheusfaleiro.heyu.communication;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matheusfaleiro.heyu.model.User;

import java.util.HashMap;

class FirebaseDatabaseManagement {

    private static boolean result;

    static boolean addUserInformationToDatabase(User user, String UID) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);

        HashMap<String, String> userDataHashMap = new HashMap<>();

        userDataHashMap.put("display_name", user.getDisplayUserName());
        userDataHashMap.put("current_status", user.getDisplayUserName());
        userDataHashMap.put("display_image", user.getUserName());
        userDataHashMap.put("thumb_image", user.getUserName());

        databaseReference.setValue(userDataHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
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
}
