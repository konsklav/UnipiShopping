package com.example.unipishopping.ui;

import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.unipishopping.core.login.LoginCallbacks;
import com.example.unipishopping.core.login.LoginError;
import com.example.unipishopping.core.login.LoginService;
import com.example.unipishopping.databinding.ActivityLoginBinding;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;

public class LoginActivity extends AppActivityBase<ActivityLoginBinding> implements LoginCallbacks {

    Button loginButton;

    @Override
    protected void onAfterCreate() {
        loginButton = getBinding().btnLogin;
        loginButton.setOnClickListener(v -> handleLogin());
    }

    /**
     * Called when 'login' button is pressed
     */
    private void handleLogin() {
        runOnUiThread(() -> loginButton.setEnabled(false));

        Editable username = getBinding().etLoginUsername.getText();
        Editable password = getBinding().etLoginPassword.getText();

        if (username == null || password == null) {
            Log.e("Login", "Username or password Editable is NULL!");
            return;
        }

        LoginService loginService = new LoginService();
        loginService.login(username.toString(), password.toString(), this);
    }

    @Override
    public void onLoginSuccess(User user) {
        Log.i("Login", "Logging in as user '" + user.getUsername() + "'.");
        Intent intent = new Intent(this, MainActivity.class);

        // Use the Parcelable implementation that User has.
        intent.putExtra(IntentExtras.USER_PARCELABLE, user);

        startActivity(intent);
    }

    @Override
    public void onLoginFail(LoginError error) {
        Toast.makeText(this, error.getDescription(), Toast.LENGTH_SHORT).show();
        runOnUiThread(() -> loginButton.setEnabled(true));
    }
}