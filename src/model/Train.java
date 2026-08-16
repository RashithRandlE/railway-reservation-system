package model;

public class Train {
    private final String trainId;
    private final String trainName;
    private final String startStation;
    private final String destination;
    private final int totalSeats;
    private int availableSeats;

    public Train(String trainId, String trainName, String startStation, String destination, int totalSeats, int availableSeats) {
        this.trainId = requireText(trainId, "Train ID");
        this.trainName = requireText(trainName, "Train name");
        this.startStation = requireText(startStation, "Start station");
        this.destination = requireText(destination, "Destination");
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Total seats must be higher than 0.");
        }
        if (availableSeats < 0 || availableSeats > totalSeats) {
            throw new IllegalArgumentException("Available seats must be between 0 and total seats.");
        }
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }


    public String getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Reserve seats atomically; false means the request cannot be fulfilled.
    public boolean reserveSeats(int seatCount) {
        if (seatCount <= 0 || seatCount > availableSeats) return false;
        availableSeats -= seatCount;
        return true;
    }

    // Return seats after a cancellation without exceeding the train capacity.
    public boolean releaseSeats(int seatCount) {
        if (seatCount <= 0 || availableSeats + seatCount > totalSeats) return false;
        availableSeats += seatCount;
        return true;
    }

    public void displayTrainInfo(){
        System.out.println("Train ID: " + this.trainId);
        System.out.println("Train Name: " + this.trainName);
        System.out.println("Start Station: " + this.startStation);
        System.out.println("Destination: " + this.destination);
        System.out.println("Total Seats: " + this.totalSeats);
        System.out.println("Available Seats: " + this.availableSeats);
        System.out.println("--------------------------------------------------");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
