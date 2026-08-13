package structure;

import model.Station;
import model.Train;

public class TestStructures {

    public static void main(String[] args) {

        // =========================
        // TEST STATION ARRAY
        // =========================

        System.out.println("===== TESTING STATION ARRAY =====");

        StationArray stations = new StationArray(5);

        // Add stations
        stations.addStation(
                new Station("CMB", "Colombo Fort", "Colombo")
        );

        stations.addStation(
                new Station("KDY", "Kandy", "Kandy")
        );

        stations.addStation(
                new Station("BDL", "Badulla", "Badulla")
        );

        stations.addStation(
                new Station("MTR", "Matara", "Matara")
        );

        // Display stations
        stations.displayStations();

        // Search station
        System.out.println("\nSearching for Kandy:");

        Station foundStation = stations.searchStation("Kandy");

        if (foundStation != null) {
            System.out.println("Found: " + foundStation.getStationName());
        } else {
            System.out.println("Station not found.");
        }


        // =========================
        // TEST TRAIN LINKED LIST
        // =========================

        System.out.println("\n\n===== TESTING TRAIN LINKED LIST =====");

        TrainLinkedList trains = new TrainLinkedList();

        // Add trains
        trains.addTrain(
                new Train(
                        "T001",
                        "Podi Menike",
                        "Colombo Fort",
                        "Badulla",
                        500,
                        450
                )
        );

        trains.addTrain(
                new Train(
                        "T002",
                        "Udarata Menike",
                        "Colombo Fort",
                        "Kandy",
                        400,
                        350
                )
        );

        trains.addTrain(
                new Train(
                        "T003",
                        "Ruhunu Kumari",
                        "Colombo Fort",
                        "Matara",
                        450,
                        400
                )
        );


        // Display all trains
        System.out.println("\nAll Trains:");

        trains.displayTrains();


        // Search train by ID
        System.out.println("\nSearching for T002:");

        Train foundTrain = trains.searchTrainById("T002");

        if (foundTrain != null) {
            foundTrain.displayTrainInfo();
        } else {
            System.out.println("Train not found.");
        }


        // Search by route
        System.out.println("\nSearching Colombo Fort -> Kandy:");

        trains.searchTrainByRoute(
                "Colombo Fort",
                "Kandy"
        );


        // Check size
        System.out.println("\nNumber of trains: " + trains.getSize());


        // Delete train
        System.out.println("\nDeleting T002:");

        if (trains.deleteTrain("T002")) {
            System.out.println("Train deleted successfully.");
        } else {
            System.out.println("Train not found.");
        }


        // Display after deletion
        System.out.println("\nTrains after deletion:");

        trains.displayTrains();

        System.out.println("\nNumber of trains: " + trains.getSize());
    }
}