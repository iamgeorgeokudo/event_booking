package com.event_booking.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.event_booking.databinding.ActivityProfileBinding;
import com.event_booking.ui.bookings.MyTicketsActivity;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Display saved M-Pesa number
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedMpesa = prefs.getString("mpesa_number", "Not Set");
        binding.tvMpesaNumber.setText(savedMpesa);

        binding.btnLogout.setOnClickListener(v -> handleLogout());

        binding.btnBookingHistory.setOnClickListener(v -> {
            // Navigate to the My Tickets page we built earlier
            startActivity(new Intent(this, MyTicketsActivity.class));
        });
    }

    private void handleLogout() {
        // 1. Clear local data
        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

        // 2. Redirect to Login and Clear Task Stack (Crucial for security)
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}