package com.event_booking.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.event_booking.api.ApiClient;
import com.event_booking.api.ApiService;
import com.event_booking.databinding.ActivityEventsBinding;
import com.event_booking.model.Event;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 1. Implement the Click Listener interface
public class EventsActivity extends AppCompatActivity implements EventAdapter.OnEventClickListener {

    private ActivityEventsBinding binding;
    private EventAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back navigation logic
        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        apiService = ApiClient.getClient().create(ApiService.class);

        // 2. Setup RecyclerView with a LayoutManager
        binding.rvEvents.setLayoutManager(new LinearLayoutManager(this));

        fetchEvents();
    }

    private void fetchEvents() {
        setLoading(true);

        apiService.getAllEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                setLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();

                    if (events.isEmpty()) {
                        showEmptyState(true);
                    } else {
                        showEmptyState(false);
                        // 3. FIX: Pass 'this' as the second argument to satisfy the constructor
                        adapter = new EventAdapter(events, EventsActivity.this);
                        binding.rvEvents.setAdapter(adapter);
                    }
                } else {
                    Log.e("API_ERROR", "Status Code: " + response.code());
                    Toast.makeText(EventsActivity.this, "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                setLoading(false);
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(EventsActivity.this, "Network error. Check connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 4. Implement the required interface method
    @Override
    public void onEventClick(Event event) {
        // This is where you'll pass data to your Booking/Details activity
        Toast.makeText(this, "Selected: " + event.getTitle(), Toast.LENGTH_SHORT).show();

        /* Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra("EVENT_ID", event.getId());
        startActivity(intent);
        */
    }

    private void setLoading(boolean isLoading) {
        if (binding.progressBar != null) {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }

    private void showEmptyState(boolean isEmpty) {
        // If you have a 'No Events' TextView in your layout
        if (binding.tvNoEvents != null) {
            binding.tvNoEvents.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        }
    }
}