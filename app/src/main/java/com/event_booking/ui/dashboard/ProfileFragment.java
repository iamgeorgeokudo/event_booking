package com.event_booking.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.event_booking.R;
// Assuming your LoginActivity is in the base package
// import com.event_booking.LoginActivity;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnLogout = view.findViewById(R.id.btnLogoutProfile);
        TextView tvUserName = view.findViewById(R.id.tvProfileName);

        // Set dummy data
        tvUserName.setText("Jane Doe");

        btnLogout.setOnClickListener(v -> {
            // Navigate back to Login and clear the backstack
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);

            // For now, just close the activity to simulate logout
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }
}