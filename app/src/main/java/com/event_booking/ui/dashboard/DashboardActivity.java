package com.event_booking.ui.dashboard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.event_booking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements EventsFragment.OnEventSelectedListener {

    private String pendingBookingEventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        // 1. Load Events by default ONLY if this is the first time the activity is created
        if (savedInstanceState == null) {
            loadFragment(new EventsFragment());
        }

        // Navigation Listener
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_events) {
                fragment = new EventsFragment();
            } else if (id == R.id.nav_tickets) {
                fragment = new MyTicketsFragment();
            } else if (id == R.id.nav_book) {
                BookEventsFragment bookEventsFragment = new BookEventsFragment();
                if (pendingBookingEventName != null && !pendingBookingEventName.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putString(BookEventsFragment.ARG_SELECTED_EVENT_NAME, pendingBookingEventName);
                    bookEventsFragment.setArguments(args);
                }
                fragment = bookEventsFragment;
            } else if (id == R.id.nav_notifications) {
                fragment = new NotificationsFragment();
            } else if (id == R.id.nav_profile) {
                fragment = new ProfileFragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onEventSelected(String eventName) {
        pendingBookingEventName = eventName;
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_book);
    }

    // 4. FIX: Handle the Back Button
    // Without this, pressing back often closes the app immediately.
    // This logic returns to the Home (Events) tab first.
    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        if (bottomNav.getSelectedItemId() != R.id.nav_events) {
            bottomNav.setSelectedItemId(R.id.nav_events);
        } else {
            super.onBackPressed();
        }
    }
}