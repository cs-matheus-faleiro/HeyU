package com.matheusfaleiro.heyu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matheusfaleiro.heyu.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeyUActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hey_u);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLogin)
    public void goToLoginActivity() {
        //startAnotherActivity(LoginActivity.class);
    }

    @OnClick(R.id.buttonRegister)
    public void goToRegisterActivity() {
        startAnotherActivity(RegisterActivity.class);
    }

    private void startAnotherActivity(Class goToSelectedActivity) {
        Intent intent = new Intent(this, goToSelectedActivity);
        startActivity(intent);
    }
}