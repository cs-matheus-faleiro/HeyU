package com.matheusfaleiro.heyu.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.matheusfaleiro.heyu.R;
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;
import com.matheusfaleiro.heyu.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView((R.id.textInputUserEmailSignIn))
    TextInputLayout textInputUserEmailSignIn;

    @BindView(R.id.editTextUserEmailSignIn)
    EditText editTextUserEmailSignIn;

    @BindView((R.id.textInputUserPasswordSignIn))
    TextInputLayout textInputUserPasswordSignIn;

    @BindView(R.id.editTextUserPasswordSignIn)
    EditText editTextUserPasswordSignIn;

    @BindView((R.id.progressBarLogin))
    ProgressBar progressBarLogin;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLoginUser)
    public void signInUser() {
        performSignInProcess(editTextUserEmailSignIn.getText().toString(), editTextUserPasswordSignIn.getText().toString());
    }

    private void performSignInProcess(String userEmail, String userPassword) {

        showProgress(true);

        removeErrorFromTextInputLayout();

        if ((isEmailValid(userEmail)) && (areAllObligatoryFieldsFilled(userEmail, userPassword))) {

            user.setUserName(userEmail);
            user.setUserPassword(userPassword);

            FirebaseUserManagement.loginToHeyU(getApplicationContext(), user);
            finish();
        }

        showProgress(false);
    }

    private boolean areAllObligatoryFieldsFilled(String userEmail, String userPassword) {

        boolean result = true;

        if (TextUtils.isEmpty(userEmail)) {
            textInputUserEmailSignIn.setError(getResources().getString(R.string.obligatory_field));
            result = false;
        } else if (TextUtils.isEmpty(userPassword)) {
            result = false;
            textInputUserPasswordSignIn.setError(getResources().getString(R.string.obligatory_field));
        }

        return result;
    }

    private boolean isEmailValid(String userEmail) {

        boolean result = true;

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            textInputUserEmailSignIn.setError(getResources().getString(R.string.invalid_email));
            result = false;
        }

        return result;
    }

    private void removeErrorFromTextInputLayout() {
        textInputUserEmailSignIn.setError(null);
        textInputUserPasswordSignIn.setError(null);
    }

    private void showProgress(final boolean show) {
        progressBarLogin.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBarLogin.animate().setDuration(1000).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBarLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
