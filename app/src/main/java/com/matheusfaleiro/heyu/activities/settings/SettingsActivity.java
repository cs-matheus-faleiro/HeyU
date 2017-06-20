package com.matheusfaleiro.heyu.activities.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.matheusfaleiro.heyu.R;
import com.matheusfaleiro.heyu.communication.FirebaseDatabaseManagement;
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;
import com.matheusfaleiro.heyu.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.imageViewThumbImage)
    ImageView imageViewThumbImage;

    @BindView(R.id.textViewDisplayName)
    TextView textViewDisplayName;

    @BindView(R.id.textViewCurrentStatus)
    TextView textViewCurrentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getUserDataFromDatabase();

        ButterKnife.bind(this);
    }

    public void getUserDataFromDatabase() {

        User user = FirebaseDatabaseManagement.getUserDataFromFirebase(FirebaseUserManagement.getCurrentUser());

        //textViewDisplayName.setText(user.getDisplayUserName());
        //textViewCurrentStatus.setText(user.getCurrentStatus());
    }

    @OnClick(R.id.buttonChangePhoto)
    public void changeCurrentPhoto() {

    }

    @OnClick(R.id.buttonChangeStatus)
    public void registerNewUser() {
        startAnotherActivity(ChangeStatusActivity.class);
    }

    private void startAnotherActivity(Class goToSelectedActivity) {
        Intent intent = new Intent(this, goToSelectedActivity);
        startActivity(intent);
    }
}
