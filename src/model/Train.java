package model;

public class Train {
    private String trainId;
    private String trainName;
    private String startStation;
    private String destination;
    private int totalSeats;
    private int availableSeats;

    public Train(String trainId, String trainName, String startStation, String destination, int totalSeats, int availableSeats) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.startStation = startStation;
        this.destination = destination;
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

    public void displayTrainInfo(){
        System.out.println("Train ID: " + this.trainId);
        System.out.println("Train Name: " + this.trainName);
        System.out.println("Start Station: " + this.startStation);
        System.out.println("Destination: " + this.destination);
        System.out.println("Total Seats: " + this.totalSeats);
        System.out.println("Available Seats: " + this.availableSeats);

    }
}
