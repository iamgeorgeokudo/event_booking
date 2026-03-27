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

public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView rvUpdates = view.findViewById(R.id.rvUpdates);
        rvUpdates.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> updates = new ArrayList<>();
        updates.add("Your ticket for Sol Fest 2026 has been confirmed.");
        updates.add("Blankets & Wine early-bird offer ends in 2 days.");
        updates.add("Payment reminder: complete your Nairobi Jazz booking.");
        updates.add("New event added: Afro House Weekend at KICC.");

        rvUpdates.setAdapter(new SimpleTextAdapter(updates));
        return view;
    }
}
