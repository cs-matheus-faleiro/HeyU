package com.matheusfaleiro.heyu.communication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matheusfaleiro.heyu.model.User;

import java.util.HashMap;

public class FirebaseDatabaseManagement {

    private static boolean result;
    private static User user = new User();

    static boolean addUserInformationToDatabase(User user, final String UID) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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

    public static User getUserDataFromFirebase(final String UID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setDisplayUserName(dataSnapshot.child("display_name").getValue().toString());
                user.setCurrentStatus(dataSnapshot.child("current_status").getValue().toString());
                user.setThumbImage(dataSnapshot.child("thumb_image").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return user;
    }
}