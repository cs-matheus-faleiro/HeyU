package com.matheusfaleiro.heyu.activities.settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matheusfaleiro.heyu.R;
import com.matheusfaleiro.heyu.communication.FirebaseDatabaseManagement;
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeStatusActivity extends AppCompatActivity {

    @BindView(R.id.changeStatusToolbar)
    Toolbar changeStatusToolbar;

    @BindView(R.id.textInputUserCurrentStatus)
    TextInputLayout textInputUserCurrentStatus;

    @BindView(R.id.editTextUserCurrentStatus)
    EditText editTextUserCurrentStatus;

    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);
        progressDialog = new ProgressDialog(this);

        ButterKnife.bind(this);

        setUpToolbar();

        getCurrentStatus();
    }

    @OnClick(R.id.buttonChangeCurrentStatus)
    public void updateUserStatus() {
       // progressDialog = new ProgressDialog(getApplicationContext());
        //progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseUserManagement.getCurrentUser());

        databaseReference.child("current_status").setValue(editTextUserCurrentStatus.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //progressDialog.dismiss();
                }
            }
        });
    }

    private void getCurrentStatus() {
        editTextUserCurrentStatus.setText(FirebaseDatabaseManagement.getUserCurrentStatus());
    }

    private void setUpToolbar() {
        setSupportActionBar(changeStatusToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.status));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
