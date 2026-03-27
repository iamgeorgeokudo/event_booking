package com.event_booking.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("message")
    private String message;

    // Added to capture the ID from the server response
    @SerializedName("userId")
    private Long userId;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}