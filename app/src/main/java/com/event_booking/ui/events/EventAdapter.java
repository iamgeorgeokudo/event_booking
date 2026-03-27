package com.event_booking.ui.events;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.event_booking.databinding.ItemEventBinding;
import com.event_booking.model.Event;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    public EventAdapter(List<Event> eventList, OnEventClickListener listener) {
        this.eventList = eventList;
        this.listener = listener;
    }

    // FIX: This method allows DashboardActivity to update the data after API call
    public void updateList(List<Event> newList) {
        this.eventList = newList;
        notifyDataSetChanged(); // Refreshes the UI
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Matching your latest item_event.xml IDs
        holder.binding.tvEventName.setText(event.getTitle());
        holder.binding.tvEventLocation.setText(event.getLocation());
        holder.binding.tvEventPrice.setText("Ksh " + event.getPrice());

        if (event.getDateTime() != null) {
            holder.binding.tvEventDate.setText(event.getDateTime());
        }

        // Using the imgEvent ID we set in the Carousel/Horizontal layouts
        Glide.with(holder.itemView.getContext())
                .load(event.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.binding.imgEvent);

        holder.itemView.setOnClickListener(v -> listener.onEventClick(event));
    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ItemEventBinding binding;
        public EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}