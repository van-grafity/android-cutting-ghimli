package com.app.ivans.ghimli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.app.ivans.ghimli.databinding.ActivityLoginBinding;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.utils.Extension;
import com.app.ivans.ghimli.utils.Validation;
import com.app.ivans.ghimli.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(LoginActivity.this));
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LoginActivity", "onStart");
        // If user logged in, go directly Home
//        if (API.currentUser(LoginActivity.this) != null && API.currentUser(LoginActivity.this).getId() != 0) {
//            showHome();
//        }else{
//            return;
//        }
    }

    private void logInUser() {
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (email.isEmpty()) {
            binding.inputEmail.setError(getString(R.string.email_required));
            binding.inputEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.inputEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.inputPassword.setError(getString(R.string.password_required));
            binding.inputPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.inputPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.inputPassword.requestFocus();
            return;
        }

        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(LoginActivity.this);
            }
        });

        userViewModel.getUserLoginResponseLiveData(email, password).observe(this, loginApiResponse -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!loginApiResponse.getMessage().equals("Unauthorised")) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        if (loginApiResponse.getData().getUser() != null) {
                            API.setToken(LoginActivity.this, loginApiResponse.getData().getToken());
                            API.setUser(LoginActivity.this, loginApiResponse.getData().getUser());
                            showHome();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        Toast.makeText(LoginActivity.this, loginApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });
    }

    private void showHome() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}