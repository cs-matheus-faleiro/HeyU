package com.matheusfaleiro.heyu.activities.heyu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.matheusfaleiro.heyu.R;
import com.matheusfaleiro.heyu.activities.heyu.adapter.SectionsPageAdapter;
import com.matheusfaleiro.heyu.activities.welcome.WelcomeActivity;
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeyUActivity extends AppCompatActivity {


    @BindView(R.id.tabBarHeyULayout)
    TabLayout tabBarLayout;

    @BindView(R.id.viewPagerHeyU)
    ViewPager viewPagerHeyU;

    @BindView(R.id.hey_u_toolbar)
    Toolbar heyUToolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hey_u);

        ButterKnife.bind(this);

        setUpToolbar();

        firebaseAuth = FirebaseAuth.getInstance();

        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPagerHeyU.setAdapter(sectionsPageAdapter);

        tabBarLayout.setupWithViewPager(viewPagerHeyU);

    }

    private void setUpToolbar() {
        setSupportActionBar(heyUToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.hey_u));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_account_settings_button:
                break;
            case R.id.main_logout_button:
                FirebaseUserManagement.logoutFromHeyU();
                sendToWelcomeActivity();
                break;
        }

        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            sendToWelcomeActivity();
        }
    }

    private void sendToWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}
