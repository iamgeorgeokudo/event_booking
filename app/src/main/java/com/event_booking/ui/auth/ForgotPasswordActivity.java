package com.event_booking.ui.auth;

import android.content.Intent; // Added for navigation
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.event_booking.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FIX: The "Login now" click listener
        binding.txtBackToLogin.setOnClickListener(v -> {
            // Simply finish this activity to go back to the previous LoginActivity
            finish();
        });

        binding.btnReset.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                sendResetRequest(email);
            } else {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResetRequest(String email) {
        binding.btnReset.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        binding.getRoot().postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnReset.setEnabled(true);
            Toast.makeText(ForgotPasswordActivity.this,
                    "Dummy reset link sent to " + email, Toast.LENGTH_LONG).show();
            finish();
        }, 800);
    }
}