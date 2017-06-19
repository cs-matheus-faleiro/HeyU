package com.matheusfaleiro.heyu.activities.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matheusfaleiro.heyu.R;
import com.matheusfaleiro.heyu.activities.heyu.activity.HeyUActivity;
import com.matheusfaleiro.heyu.communication.FirebaseDatabaseManagement;
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;
import com.matheusfaleiro.heyu.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView((R.id.textInputUserEmailRegistration))
    TextInputLayout textInputLayoutUserEmail;

    @BindView(R.id.editTextUserEmailRegistration)
    EditText editTextUserEmail;

    @BindView((R.id.textInputUserPasswordRegistration))
    TextInputLayout textInputLayoutUserPassword;

    @BindView(R.id.editTextUserPassword)
    EditText editTextUserPassword;

    @BindView((R.id.progressBarRegisterNewUser))
    ProgressBar progressBarRegisterNewUser;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegisterNewUser)
    public void registerNewUser() {
        createNewUser(editTextUserEmail.getText().toString(), editTextUserPassword.getText().toString());
    }

    private void createNewUser(String userEmail, String userPassword) {

        showProgress(true);

        removeErrorFromTextInputLayout();

        if ((isEmailValid(userEmail)) && (areAllObligatoryFieldsFilled(userEmail, userPassword))) {
            registerNewUserIntoFirebase(userEmail, userPassword);
        }

        showProgress(false);
    }

    private void registerNewUserIntoFirebase(String userEmail, String userPassword) {
        if (FirebaseUserManagement.registerNewUser(getApplicationContext(), setUserNameEmailAndPassword(userEmail, userPassword))) {
            startAnotherActivity(HeyUActivity.class);
            finish();
        } else {
            Toast.makeText(this, "Deu RUIM LEK", Toast.LENGTH_SHORT).show();
        }
    }

    private User setUserNameEmailAndPassword(String userEmail, String userPassword) {
        user.setUserName(userEmail);
        user.setUserPassword(userPassword);

        return user;
    }

    private boolean areAllObligatoryFieldsFilled(String userEmail, String userPassword) {

        boolean result = true;

        if (TextUtils.isEmpty(userEmail)) {
            textInputLayoutUserEmail.setError(getResources().getString(R.string.obligatory_field));
            result = false;
        } else if (TextUtils.isEmpty(userPassword)) {
            result = false;
            textInputLayoutUserPassword.setError(getResources().getString(R.string.obligatory_field));
        }

        return result;
    }

    private boolean isEmailValid(String userEmail) {

        boolean result = true;

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            textInputLayoutUserEmail.setError(getResources().getString(R.string.invalid_email));
            result = false;
        }

        return result;
    }

    private void removeErrorFromTextInputLayout() {
        textInputLayoutUserPassword.setError(null);
        textInputLayoutUserEmail.setError(null);
    }

    private void showProgress(final boolean show) {
        progressBarRegisterNewUser.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBarRegisterNewUser.animate().setDuration(10000).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBarRegisterNewUser.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void startAnotherActivity(Class goToSelectedActivity) {
        Intent intent = new Intent(this, goToSelectedActivity);
        startActivity(intent);
    }
}
