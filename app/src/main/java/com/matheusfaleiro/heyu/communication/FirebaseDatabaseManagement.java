package com.matheusfaleiro.heyu.communication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matheusfaleiro.heyu.model.User;

import java.util.HashMap;

public class FirebaseDatabaseManagement {


    static void addUserInformationToDatabase(User user, String UID) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);

        HashMap<String, String> userDataHashMap = new HashMap<>();

        userDataHashMap.put("display_name", user.getUserName());
        userDataHashMap.put("current_status", user.getUserName());
        userDataHashMap.put("display_image", user.getUserName());
        userDataHashMap.put("thumb_image", user.getUserName());

        databaseReference.setValue(userDataHashMap);
    }
}
