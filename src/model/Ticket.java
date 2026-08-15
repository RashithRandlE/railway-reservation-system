package model;

import java.util.List;

public class Ticket {

    // Price for 1km for 1 passenger
    private static final double FARE_PER_KM = 10.0;

    private final String bookingID;
    private final String userID;
    private final String trainID;
    private final String startLocation;
    private final String destination;
    private final List<String> seatNumbers;
    private final double distance;
    private final double ticketPrice;

    public Ticket(Booking booking, double distance) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance should be greater than zero");
        }

        this.bookingID = booking.getBookingID();
        this.userID = booking.getUserID();
        this.trainID = booking.getTrainID();
        this.startLocation = booking.getStartLocation();
        this.destination = booking.getDestination();
        this.seatNumbers = booking.getSeatNumbers();
        this.distance = distance;

        // Calculate total price
        this.ticketPrice = calculateTicketPrice(distance, booking.getTotalSeats());
    }

    // Calculate ticket price
    public double calculateTicketPrice(double distance, int numberOfSeats) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance should be greater than 0.");
        }
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than 0.");
        }
        return distance * FARE_PER_KM * numberOfSeats;
    }

    public String getBookingID(){
        return bookingID;
    }
    public String getUserID(){
        return userID;
    }
    public String getTrainID(){
        return trainID;
    }
    public String getStartLocation(){
        return startLocation;
    }
    public String getDestination(){
        return destination;
    }
    public List<String> getSeatNumbers(){
        return seatNumbers;
    }
    public double getDistance(){
        return distance;
    }
    public double getTicketPrice(){
        return ticketPrice;
    }
    public double getFarePerKm(){
        return FARE_PER_KM;
    }

    // Display ticket
    public void displayTicket() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("              TRAIN TICKET                ");
        System.out.println("==========================================");
        System.out.println("Booking ID      : " + bookingID);
        System.out.println("User ID         : " + userID);
        System.out.println("Train ID        : " + trainID);
        System.out.println("From            : " + startLocation);
        System.out.println("To              : " + destination);
        System.out.println("Seats           : " + seatNumbers);
        System.out.println("Distance        : " + distance + " km");
        System.out.println("Fare per km     : Rs. " + FARE_PER_KM);
        System.out.println("Total Price     : Rs. " + ticketPrice);
        System.out.println("==========================================");
        System.out.println("           BOOKING CONFIRMED              ");
        System.out.println("==========================================");
    }
}
