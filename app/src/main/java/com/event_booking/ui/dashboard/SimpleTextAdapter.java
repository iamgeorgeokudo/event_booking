package com.event_booking.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.event_booking.R;

import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.TextViewHolder> {

    private final List<String> items;

    public SimpleTextAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_text, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.tvItemText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemText;

        TextViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemText = itemView.findViewById(R.id.tvItemText);
        }
    }
}
