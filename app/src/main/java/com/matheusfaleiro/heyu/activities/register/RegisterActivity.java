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
import com.matheusfaleiro.heyu.communication.FirebaseUserManagement;
import com.matheusfaleiro.heyu.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView((R.id.textInputUserDisplayName))
    TextInputLayout textInputUserDisplayName;

    @BindView(R.id.editTextUserDisplayNameRegistration)
    EditText editTextUserDisplayNameRegistration;

    @BindView((R.id.textInputUserEmailRegistration))
    TextInputLayout textInputLayoutUserEmail;

    @BindView(R.id.editTextUserEmailRegistration)
    EditText editTextUserEmail;

    @BindView((R.id.textInputUserPasswordRegistration))
    TextInputLayout textInputLayoutUserPassword;

    @BindView(R.id.editTextUserPassword)
    EditText editTextUserPassword;

    @BindView(R.id.textInputUserPasswordConfirmationRegistration)
    TextInputLayout textInputLayoutUserPasswordConfirmationRegistration;

    @BindView(R.id.editTextUserPasswordConfirmationRegistration)
    EditText editTextUserPasswordConfirmationRegistration;

    @BindView((R.id.progressBarRegisterNewUser))
    ProgressBar progressBarRegisterNewUser;

    User userToBeRegistered = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegisterNewUser)
    public void registerNewUser() {
        createNewUser(editTextUserDisplayNameRegistration.getText().toString(), editTextUserEmail.getText().toString(), editTextUserPassword.getText().toString());
    }

    private void createNewUser(String userDisplayName, String userEmail, String userPassword) {

        showProgress(true);

        removeErrorFromTextInputLayout();

        if ((areAllObligatoryFieldsFilled(userDisplayName, userEmail, userPassword)) && (isEmailValid(userEmail))) {
            if (areThePasswordsEqual(editTextUserPassword.getText().toString(), editTextUserPasswordConfirmationRegistration.getText().toString()))
                registerNewUserIntoFirebase(userEmail, userPassword);
        }

        showProgress(false);
    }

    private boolean areThePasswordsEqual(String userPassword, String userPasswordConfirmation) {
        if (!TextUtils.equals(userPassword, userPasswordConfirmation)) {
            Toast.makeText(this, getResources().getString(R.string.your_passwords_do_not_mismatch), Toast.LENGTH_LONG).show();
            textInputLayoutUserPassword.setError(getResources().getString(R.string.your_passwords_do_not_mismatch));
            textInputLayoutUserPasswordConfirmationRegistration.setError(getResources().getString(R.string.your_passwords_do_not_mismatch));

            return false;
        }

        return true;
    }

    private void registerNewUserIntoFirebase(String userEmail, String userPassword) {
        if (FirebaseUserManagement.registerNewUser(setUserNameEmailAndPassword(userEmail, userPassword))) {
            startAnotherActivity(HeyUActivity.class);
        } else {
            Toast.makeText(this, "Deu RUIM LEK", Toast.LENGTH_SHORT).show();
        }
    }

    private User setUserNameEmailAndPassword(String userEmail, String userPassword) {
        userToBeRegistered.setDisplayUserName(editTextUserDisplayNameRegistration.getText().toString());
        userToBeRegistered.setUserName(userEmail);
        userToBeRegistered.setUserPassword(userPassword);

        return userToBeRegistered;
    }

    private boolean areAllObligatoryFieldsFilled(String displayName, String userEmail, String userPassword) {

        boolean result = true;

        if (TextUtils.isEmpty(displayName)) {
            textInputUserDisplayName.setError(getResources().getString(R.string.obligatory_field));
            result = false;
        } else if (TextUtils.isEmpty(userEmail)) {
            textInputLayoutUserEmail.setError(getResources().getString(R.string.obligatory_field));
            result = false;
        } else if (TextUtils.isEmpty(userPassword)) {
            textInputLayoutUserPassword.setError(getResources().getString(R.string.obligatory_field));
            result = false;
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
        textInputUserDisplayName.setError(null);
        textInputLayoutUserEmail.setError(null);
        textInputLayoutUserPassword.setError(null);
        textInputLayoutUserPasswordConfirmationRegistration.setError(null);
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
        finish();
    }
}
