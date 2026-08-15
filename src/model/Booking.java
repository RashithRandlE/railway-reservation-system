package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Booking {
    public enum Status {
        CONFIRMED, WAITLISTED, CANCELLED
    }

    private static int nextBookingNumber = 1;

    private final String bookingID;
    private final String userID;
    private final String trainID;
    private final String startLocation;
    private final String destination;
    private final int totalSeats;
    private final List<String> seatNumbers;
    private Status status;

    // Constructor
    public Booking(String userID, String startLocation, String destination, int totalSeats) {
        this(userID, "UNASSIGNED", startLocation, destination, totalSeats, Collections.<String>emptyList());
    }

    // to use in reservation system
    public Booking(String userID, String trainID, String startLocation, String destination, int totalSeats) {
        this(userID, trainID, startLocation, destination, totalSeats,
                Collections.<String>emptyList());
    }

    // Stores the exact seats so the Set ADT can prevent duplicate allocation.
    public Booking(String userID, String trainID, String startLocation, String destination, int totalSeats, List<String> seatNumbers) {
        this.userID = requireText(userID, "User ID");
        this.trainID = requireText(trainID, "Train ID");
        this.startLocation = requireText(startLocation, "Start location");
        this.destination = requireText(destination, "Destination");

        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Total seats must be higher than 0.");
        }

        // generate a booking id after validation
        this.bookingID = String.format("B%04d", nextBookingNumber++);
        this.totalSeats = totalSeats;
        this.seatNumbers = seatNumbers == null
                ? new ArrayList<String>() : new ArrayList<String>(seatNumbers);
        this.status = Status.CONFIRMED;
    }

    // Getters
    public String getBookingID() {
        return bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public String getTrainID() {
        return trainID;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public List<String> getSeatNumbers() {
        return Collections.unmodifiableList(seatNumbers);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) throw new IllegalArgumentException("Booking status cannot be null.");
        this.status = status;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
