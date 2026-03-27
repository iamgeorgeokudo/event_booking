package com.event_booking.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.event_booking.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Click listeners
        binding.btnRegister.setOnClickListener(v -> performRegistration());

        binding.txtLogin.setOnClickListener(v -> {
            finish(); // Go back to LoginActivity
        });
    }

    private void performRegistration() {
        String username = binding.etUsername.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

        // 1. Basic Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password too short (min 6 chars)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Dummy front-end only registration
        showLoading(true);
        binding.scrollView.postDelayed(() -> {
            showLoading(false);
            Toast.makeText(this, "Account created for " + username, Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }, 800);
    }

    private void showLoading(boolean isLoading) {
        binding.btnRegister.setEnabled(!isLoading);
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}