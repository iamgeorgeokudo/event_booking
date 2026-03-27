package com.event_booking.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.event_booking.R;

public class BookEventsFragment extends Fragment {
    public static final String ARG_SELECTED_EVENT_NAME = "selected_event_name";

    private Spinner spnDummyEvents;
    private TextView tvBookingEventTitle;
    private TextView tvBookingDateVenue;
    private TextView tvTicketPrice;
    private TextView tvBookingTotal;
    private EditText etTicketQuantity;
    private EditText etPhoneNumber;
    private Button btnMpesa;

    private final DummyEvent[] dummyEvents = new DummyEvent[]{
            new DummyEvent("Sol Fest 2026 - VIP", "19 Dec 2026", "Uhuru Gardens", 5500),
            new DummyEvent("Blankets & Wine", "10 May 2026", "Carnivore Grounds", 2500),
            new DummyEvent("Nairobi Jazz Night", "02 Jun 2026", "KICC", 1800)
    };

    private DummyEvent selectedEvent = dummyEvents[0];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_events, container, false);

        spnDummyEvents = view.findViewById(R.id.spnDummyEvents);
        tvBookingEventTitle = view.findViewById(R.id.tvBookingEventTitle);
        tvBookingDateVenue = view.findViewById(R.id.tvBookingDateVenue);
        tvTicketPrice = view.findViewById(R.id.tvTicketPrice);
        tvBookingTotal = view.findViewById(R.id.tvBookingTotal);
        etTicketQuantity = view.findViewById(R.id.etTicketQuantity);
        etPhoneNumber = view.findViewById(R.id.etMpesaNumber);
        btnMpesa = view.findViewById(R.id.btnLipaNaMpesa);

        setupDummyEventDropdown();
        updateEventSummary();
        applyPreselectedEventIfAvailable();

        etTicketQuantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updateTotal();
            }
        });

        btnMpesa.setOnClickListener(v -> {
            int quantity = getTicketQuantity();
            String phone = etPhoneNumber.getText().toString().trim();

            if (quantity < 1) {
                etTicketQuantity.setError("Enter at least 1 ticket");
                return;
            }

            if (!isValidKenyanPhone(phone)) {
                etPhoneNumber.setError("Enter a valid Safaricom number");
                return;
            }

            int total = selectedEvent.price * quantity;
            String message = "Dummy STK Push sent to " + phone + " for KES " + total;
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

            new AlertDialog.Builder(requireContext())
                    .setTitle("Payment Successful")
                    .setMessage("Booked " + quantity + " ticket(s) for " + selectedEvent.name + ".\nAmount: KES " + total)
                    .setPositiveButton("OK", null)
                    .show();
        });

        return view;
    }

    private void applyPreselectedEventIfAvailable() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        String selectedEventName = args.getString(ARG_SELECTED_EVENT_NAME);
        if (selectedEventName == null || selectedEventName.trim().isEmpty()) {
            return;
        }

        for (int i = 0; i < dummyEvents.length; i++) {
            if (dummyEvents[i].name.equalsIgnoreCase(selectedEventName.trim())) {
                spnDummyEvents.setSelection(i);
                return;
            }
        }
    }

    private void setupDummyEventDropdown() {
        String[] eventNames = new String[dummyEvents.length];
        for (int i = 0; i < dummyEvents.length; i++) {
            eventNames[i] = dummyEvents[i].name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                eventNames
        );
        spnDummyEvents.setAdapter(adapter);

        spnDummyEvents.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = dummyEvents[position];
                updateEventSummary();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // No-op for dummy flow
            }
        });
    }

    private void updateEventSummary() {
        tvBookingEventTitle.setText(selectedEvent.name);
        tvBookingDateVenue.setText(selectedEvent.date + " \u2022 " + selectedEvent.venue);
        tvTicketPrice.setText("Ticket Price: KES " + selectedEvent.price);
        updateTotal();
    }

    private void updateTotal() {
        int quantity = getTicketQuantity();
        int total = selectedEvent.price * quantity;
        tvBookingTotal.setText("Total: KES " + total);
    }

    private int getTicketQuantity() {
        String value = etTicketQuantity.getText().toString().trim();
        if (value.isEmpty()) {
            return 1;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isValidKenyanPhone(String phone) {
        return phone.matches("^(07\\d{8}|01\\d{8})$");
    }

    static class DummyEvent {
        String name;
        String date;
        String venue;
        int price;

        DummyEvent(String name, String date, String venue, int price) {
            this.name = name;
            this.date = date;
            this.venue = venue;
            this.price = price;
        }
    }
}