package com.event_booking.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class EventsFragment extends Fragment {

    private RecyclerView rvCategories;
    private RecyclerView rvUpcoming;
    private RecyclerView rvNearby;
    private OnEventSelectedListener onEventSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        rvCategories = view.findViewById(R.id.rvCategories);
        rvUpcoming = view.findViewById(R.id.rvUpcoming);
        rvNearby = view.findViewById(R.id.rvNearby);

        View searchCard = view.findViewById(R.id.cardSearch);
        View filterButton = view.findViewById(R.id.btnFilter);

        searchCard.setOnClickListener(v ->
                Toast.makeText(getContext(), "Search Nairobi events", Toast.LENGTH_SHORT).show()
        );
        filterButton.setOnClickListener(v ->
                Toast.makeText(getContext(), "Filter by date, price, or location", Toast.LENGTH_SHORT).show()
        );

        rvCategories.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        rvUpcoming.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        rvNearby.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryItem("Gaming", android.R.drawable.ic_media_play));
        categories.add(new CategoryItem("Arts", android.R.drawable.ic_menu_edit));
        categories.add(new CategoryItem("Business", android.R.drawable.ic_menu_agenda));
        categories.add(new CategoryItem("Fashion", android.R.drawable.ic_menu_gallery));

        List<EventItem> upcomingEvents = new ArrayList<>();
        upcomingEvents.add(new EventItem("Koroga Festival", "Nairobi, Kenya  |  29 May 8:00 PM", "KES 3,000", "Koroga Festival", R.drawable.event_placeholder, "Music"));
        upcomingEvents.add(new EventItem("Blankets & Wine", "Nairobi, Kenya  |  03 Jun 2:00 PM", "KES 2,500", "Blankets & Wine", R.drawable.ic_launcher_background, "Live"));
        upcomingEvents.add(new EventItem("City Lights Concert", "Nairobi, Kenya  |  08 Jun 7:00 PM", "KES 4,000", "City Lights Concert", R.drawable.ic_launcher_foreground, "Concert"));

        List<EventItem> nearbyEvents = new ArrayList<>();
        nearbyEvents.add(new EventItem("Jazz & Nyama Choma Night", "Westlands  |  12 Jun 8:00 PM", "KES 2,200", "Jazz & Nyama Choma Night", R.drawable.event_placeholder, "Jazz"));
        nearbyEvents.add(new EventItem("Rooftop Nairobi DJ Live", "Kilimani  |  15 Jun 9:30 PM", "KES 1,800", "Rooftop Nairobi DJ Live", R.drawable.ic_launcher_background, "DJ"));
        nearbyEvents.add(new EventItem("Uhuru Gardens Indie Fest", "Lang’ata  |  20 Jun 6:30 PM", "KES 2,000", "Uhuru Gardens Indie Fest", R.drawable.ic_launcher_foreground, "Indie"));

        rvCategories.setAdapter(new CategoriesAdapter(categories));
        rvUpcoming.setAdapter(new DashboardEventsAdapter(upcomingEvents));
        rvNearby.setAdapter(new DashboardEventsAdapter(nearbyEvents));

        return view;
    }

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        if (context instanceof OnEventSelectedListener) {
            onEventSelectedListener = (OnEventSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onEventSelectedListener = null;
    }

    private void selectBottomTab(int itemId) {
        if (getActivity() == null) {
            return;
        }
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav =
                getActivity().findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(itemId);
        }
    }

    static class CategoryItem {
        String name;
        int iconResId;

        CategoryItem(String name, int iconResId) {
            this.name = name;
            this.iconResId = iconResId;
        }
    }

    static class EventItem {
        String title;
        String meta;
        String price;
        String bookingName;
        int imageResId;
        String badge;

        EventItem(String title, String meta, String price, String bookingName, int imageResId, String badge) {
            this.title = title;
            this.meta = meta;
            this.price = price;
            this.bookingName = bookingName;
            this.imageResId = imageResId;
            this.badge = badge;
        }
    }

    class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

        private final List<CategoryItem> items;

        CategoriesAdapter(List<CategoryItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dashboard_category, parent, false);
            return new CategoryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            CategoryItem item = items.get(position);
            holder.tvCategoryName.setText(item.name);
            holder.imgCategoryIcon.setImageResource(item.iconResId);

            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(getContext(), item.name + " events", Toast.LENGTH_SHORT).show();
                selectBottomTab(R.id.nav_book);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder {
            ImageView imgCategoryIcon;
            TextView tvCategoryName;

            CategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                imgCategoryIcon = itemView.findViewById(R.id.imgCategoryIcon);
                tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            }
        }
    }

    class DashboardEventsAdapter extends RecyclerView.Adapter<DashboardEventsAdapter.EventViewHolder> {

        private final List<EventItem> items;

        DashboardEventsAdapter(List<EventItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dashboard_event, parent, false);
            return new EventViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            EventItem item = items.get(position);
            holder.tvTitle.setText(item.title);
            holder.tvMeta.setText(item.meta);
            holder.tvPrice.setText(item.price);
            holder.tvBadge.setText(item.badge);
            holder.imgEvent.setImageResource(item.imageResId);

            holder.itemView.setOnClickListener(v ->
            {
                Toast.makeText(getContext(), "Booking " + item.bookingName, Toast.LENGTH_SHORT).show();
                if (onEventSelectedListener != null) {
                    onEventSelectedListener.onEventSelected(item.bookingName);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class EventViewHolder extends RecyclerView.ViewHolder {
            ImageView imgEvent;
            TextView tvBadge;
            TextView tvMeta;
            TextView tvTitle;
            TextView tvPrice;

            EventViewHolder(@NonNull View itemView) {
                super(itemView);
                imgEvent = itemView.findViewById(R.id.imgEvent);
                tvBadge = itemView.findViewById(R.id.tvEventBadge);
                tvTitle = itemView.findViewById(R.id.tvEventName);
                tvMeta = itemView.findViewById(R.id.tvEventMeta);
                tvPrice = itemView.findViewById(R.id.tvEventPrice);
            }
        }
    }

    public interface OnEventSelectedListener {
        void onEventSelected(String eventName);
    }
}