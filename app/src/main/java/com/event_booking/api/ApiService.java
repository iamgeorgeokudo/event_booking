package com.event_booking.api;

import com.event_booking.model.AuthResponse;
import com.event_booking.model.Booking;
import com.event_booking.model.Event;
import com.event_booking.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // Auth
    @POST("auth/register")
    Call<AuthResponse> registerUser(@Body User user);

    @POST("auth/login")
    Call<AuthResponse> loginUser(@Body User user);

    @POST("auth/forgot-password")
    Call<AuthResponse> forgotPassword(@Query("email") String email);

    // Events
    @GET("events")
    Call<List<Event>> getAllEvents();

    @POST("events")
    Call<Event> createEvent(@Body Event event);

    @PUT("events/{id}")
    Call<Event> updateEvent(@Path("id") Long id, @Body Event event);

    @DELETE("events/{id}")
    Call<String> deleteEvent(@Path("id") Long id);

    // Bookings
    @POST("bookings/book")
    Call<String> bookEvent(@Query("userId") Long userId,
                           @Query("eventId") Long eventId,
                           @Query("quantity") int quantity,
                           @Query("phoneNumber") String phoneNumber);

    @GET("api/bookings/user/{userId}")
    Call<List<Booking>> getMyTickets(@Path("userId") Long userId);
}
