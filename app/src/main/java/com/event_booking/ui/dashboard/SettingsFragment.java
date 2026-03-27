package com.event_booking.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.event_booking.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        RecyclerView rvSettings = view.findViewById(R.id.rvSettings);
        rvSettings.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> settingsItems = new ArrayList<>();
        settingsItems.add("Edit profile information");
        settingsItems.add("Change password");
        settingsItems.add("Manage notification preferences");
        settingsItems.add("Payment methods");
        settingsItems.add("Help & support");
        settingsItems.add("Terms and privacy");

        rvSettings.setAdapter(new SimpleTextAdapter(settingsItems));
        return view;
    }
}
