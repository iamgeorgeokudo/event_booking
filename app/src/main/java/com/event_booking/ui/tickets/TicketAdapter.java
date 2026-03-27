package com.event_booking.ui.tickets;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.event_booking.databinding.ItemTicketBinding;
import com.event_booking.model.Booking;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Booking> bookings;

    public TicketAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void updateData(List<Booking> newBookings) {
        this.bookings = newBookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTicketBinding binding = ItemTicketBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketBinding binding;

        public TicketViewHolder(ItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Booking booking) {
            binding.tvTicketTitle.setText(booking.getEvent().getTitle());
            binding.tvTicketLoc.setText(booking.getEvent().getLocation());
            binding.tvTicketDate.setText(booking.getBookingDate().toString());

            // 1. Handle Status Colors
            if (booking.isPaid()) {
                binding.tvStatus.setText("PAID");
                binding.tvStatus.setTextColor(Color.parseColor("#4CAF50")); // Green

                // 2. Generate QR Code ONLY if paid
                Bitmap qrCode = generateQRCode("TICKET-" + booking.getId());
                if (qrCode != null) {
                    binding.ivQRCode.setImageBitmap(qrCode);
                    binding.ivQRCode.setAlpha(1.0f);
                }
            } else {
                binding.tvStatus.setText("PENDING");
                binding.tvStatus.setTextColor(Color.parseColor("#FF9800")); // Orange
                binding.ivQRCode.setAlpha(0.2f); // Dim QR code if not paid
            }
        }

        private Bitmap generateQRCode(String text) {
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 400, 400);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                return bmp;
            } catch (WriterException e) {
                return null;
            }
        }
    }
}