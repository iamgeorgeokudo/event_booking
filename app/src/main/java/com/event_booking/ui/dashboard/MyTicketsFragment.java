package com.event_booking.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.event_booking.R;
import java.util.ArrayList;
import java.util.List;

public class MyTicketsFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tickets, container, false);

        recyclerView = view.findViewById(R.id.rvMyTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data representing Kenyan events
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket("Sol Fest 2026", "19th Dec 2026", "KES 2,500"));
        ticketList.add(new Ticket("Safari Sevens", "03 Oct 2026", "KES 1,000"));
        ticketList.add(new Ticket("Nyege Nyege Festival", "12th Nov 2026", "KES 5,000"));

        TicketAdapter adapter = new TicketAdapter(ticketList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Ticket Data Model
    static class Ticket {
        String name, date, price;
        Ticket(String name, String date, String price) {
            this.name = name;
            this.date = date;
            this.price = price;
        }
    }

    // Adapter for the RecyclerView
    class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
        List<Ticket> tickets;

        TicketAdapter(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Ticket t = tickets.get(position);
            holder.tvName.setText(t.name);
            holder.tvDate.setText(t.date);

            // This now works because tvPrice is declared in the ViewHolder below
            if (holder.tvPrice != null) {
                holder.tvPrice.setText(t.price);
            }

            // Simple click listener for the card
            holder.itemView.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Opening " + t.name, Toast.LENGTH_SHORT).show()
            );
        }

        @Override
        public int getItemCount() {
            return tickets.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvDate, tvPrice;

            ViewHolder(View itemView) {
                super(itemView);
                // Ensure these IDs exist in item_ticket.xml
                tvName = itemView.findViewById(R.id.tvTicketEventName);
                tvDate = itemView.findViewById(R.id.tvTicketDate);
                tvPrice = itemView.findViewById(R.id.tvTicketPrice);

                // This check helps you find the error during runtime
                if (tvName == null) {
                    android.util.Log.e("ERROR", "ID tvTicketEventName not found in item_ticket.xml");
                }
            }
        }

    }
}