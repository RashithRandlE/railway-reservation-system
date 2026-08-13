package model;

public class Booking {
    private static int nextBookingNumber = 1;

    private final String bookingID;
    private final String userID;
    private final String startLocation;
    private final String destination;
    private final int totalSeats;

    // Constructor without the unused ID parameter
    public Booking(String userID, String startLocation, String destination, int totalSeats) {
        this.bookingID = String.format("B%04d", nextBookingNumber++);// this is used to generate next booking id
        this.userID = userID;
        this.startLocation = startLocation;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    // Getters
    public String getBookingID() {
        return bookingID;
    }

    public String getUserID() {
        return userID;
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
}
