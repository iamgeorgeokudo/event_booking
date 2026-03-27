package com.event_booking.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.event_booking.databinding.ActivityLoginBinding;
import com.event_booking.ui.dashboard.DashboardActivity;
import com.event_booking.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.txtForgotPassword.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class)));

        binding.txtRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        binding.btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Dummy front-end–only login:
        // accept any non-empty credentials and create a fake session
        setLoading(true);

        long fakeUserId = 1L;
        sessionManager.saveUser(email, fakeUserId);

        Toast.makeText(this, "Logged in (dummy)", Toast.LENGTH_SHORT).show();
        navigateToDashboard();

        setLoading(false);
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLoading(boolean isLoading) {
        binding.btnLogin.setEnabled(!isLoading);
        binding.etUsername.setEnabled(!isLoading);
        binding.etPassword.setEnabled(!isLoading);
        if (binding.progressBar != null) {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }
}