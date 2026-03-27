package com.event_booking.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Event implements Serializable {
    private Long id;
    private String title;
    private String location;
    private String description; // Added to match your Spring Boot Entity

    @SerializedName("dateTime")
    private String dateTime;

    private double price;
    private int totalTickets;

    // 1. ADD THIS FIELD: This is what the Adapter is looking for
    @SerializedName("imageUrl")
    private String imageUrl;

    public Event() {}

    public Event(String title, String location, String dateTime, double price, int totalTickets, String imageUrl) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
        this.price = price;
        this.totalTickets = totalTickets;
        this.imageUrl = imageUrl;
    }

    // --- GETTERS AND SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // 2. ADD THIS GETTER: This fixes the "cannot find symbol" error
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Removed the placeholder getName() and getDate() methods as they aren't needed.
}