package com.event_booking.model;

import java.io.Serializable;

public class Booking implements Serializable {
    private Long id;
    private User user;
    private Event event;
    private int quantity;
    private double totalAmount;
    private boolean paid;

    // ADDED: This field must match the backend JSON key
    private String bookingDate;

    // Default constructor
    public Booking() {}

    // Constructor for creating a booking request
    public Booking(User user, Event event, int quantity, double totalAmount) {
        this.user = user;
        this.event = event;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paid = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    // ADDED: Getter and Setter for the date to fix the compiler error
    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}