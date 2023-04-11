package com.app.ivans.ghimli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.app.ivans.ghimli.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(SignUpActivity.this));
        setContentView(binding.getRoot());

        binding.btnShowLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}