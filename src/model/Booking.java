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


    public Booking(String userID, String trainID, String startLocation, String destination, int totalSeats, List<String> seatNumbers) {

        this.userID = requireText(userID, "User ID");
        this.trainID = requireText(trainID, "Train ID");
        this.startLocation = requireText(startLocation, "Start location");
        this.destination = requireText(destination, "Destination");
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Total seats must be higher than 0.");
        }
        // Generate the ID only after validation, so invalid bookings do not consume an ID.
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

    // this is uesd for make sure the answers are not empty
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
