package com.event_booking.ui.bookings;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.event_booking.api.ApiClient;
import com.event_booking.api.ApiService;
import com.event_booking.databinding.ActivityMyTicketsBinding;
import com.event_booking.model.Booking;
import com.event_booking.ui.tickets.TicketAdapter;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketsActivity extends AppCompatActivity {

    private ActivityMyTicketsBinding binding;
    private TicketAdapter adapter;
    private ApiService apiService;
    private Long userId = 1L; // In a real app, get this from SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupUI();
        fetchTickets();
    }

    private void setupUI() {
        // Setup Toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Digital Tickets");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Setup RecyclerView
        binding.rvTickets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TicketAdapter(new ArrayList<>());
        binding.rvTickets.setAdapter(adapter);

        // Setup Swipe to Refresh
        binding.swipeRefresh.setOnRefreshListener(this::fetchTickets);
    }

    private void fetchTickets() {
        binding.swipeRefresh.setRefreshing(true);
        apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getMyTickets(userId).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Booking> bookings = response.body();
                    if (bookings.isEmpty()) {
                        binding.tvEmptyState.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvEmptyState.setVisibility(View.GONE);
                        adapter.updateData(bookings);
                    }
                } else {
                    Toast.makeText(MyTicketsActivity.this, "Failed to load tickets", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                Toast.makeText(MyTicketsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}