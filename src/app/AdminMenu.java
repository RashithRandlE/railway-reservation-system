package app;

import model.Booking;
import model.Station;
import model.Train;
import service.RailwayReservationSystem;

import java.util.List;
import java.util.Scanner;


// This handles all the admin interaction
public class AdminMenu {
    private final RailwayReservationSystem system;
    private final Scanner scanner;

    public AdminMenu(RailwayReservationSystem system, Scanner scanner) {
        this.system = system;
        this.scanner = scanner;
    }

    public void loginAndRun() {
        String username = readText("Admin username: ");
        String password = readText("Admin password: ");
        if (!system.adminLogin(username, password)) {
            System.out.println("Incorrect admin username or password.");
            return;
        }
        System.out.println("Admin login successful.");
        runMenu();
    }

    private void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            switch (readInt("Choose an option: ")) {
                case 1:
                    trainMenu();
                    break;
                case 2:
                    networkMenu();
                    break;
                case 3:
                    bookingMenu();
                    break;
                case 4:
                    userActivityMenu();
                    break;
                case 5:
                    sortTrains();
                    break;
                case 0:
                    running = false;
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Manage trains");
        System.out.println("2. Manage stations and routes");
        System.out.println("3. Manage bookings");
        System.out.println("4. Users and activity");
        System.out.println("5. Sort trains");
        System.out.println("0. Log out");
    }

    // when admin choose option 1
    private void trainMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- TRAIN MANAGEMENT ---");
            System.out.println("1. View all trains");
            System.out.println("2. Search train by ID");
            System.out.println("3. Add train");
            System.out.println("4. Remove train");
            System.out.println("5. Demonstrate BST traversals");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ")) {
                case 1:
                    displayTrains();
                    break;
                case 2:
                    searchTrain();
                    break;
                case 3:
                    addTrain();
                    break;
                case 4:
                    removeTrain();
                    break;
                case 5:
                    treeTraversal(true);
                    break;
                case 0:
                    back = true;
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // admin choose option 2  of main menu
    private void networkMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- STATION AND ROUTE MANAGEMENT ---");
            System.out.println("1. View stations");
            System.out.println("2. Add station");
            System.out.println("3. Remove station");
            System.out.println("4. View route network");
            System.out.println("5. Add route connection");
            System.out.println("6. Remove route connection");
            System.out.println("7. Check route using BFS");
            System.out.println("8. Check connectivity using DFS");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ")) {
                case 1:
                    displayStations();
                    break;
                case 2:
                    addStation();
                    break;
                case 3:
                    removeStation();
                    break;
                case 4:
                    system.displayRouteGraph();
                    break;
                case 5:
                    addRoute();
                    break;
                case 6:
                    removeRoute();
                    break;
                case 7:
                    checkRoute();
                    break;
                case 8:
                    checkConnectivity();
                    break;
                case 0:
                    back = true;
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }


    // option 3
    private void bookingMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- BOOKING MANAGEMENT ---");
            System.out.println("1. Search booking by ID");
            System.out.println("2. View all bookings");
            System.out.println("3. View waiting list");
            System.out.println("4. Demonstrate AVL traversals");
            System.out.println("5. View booked-seat set");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ")) {
                case 1:
                    searchBooking();
                    break;
                case 2:
                    displayBookings();
                    break;
                case 3:
                    system.displayWaitingList();
                    break;
                case 4:
                    treeTraversal(false);
                    break;
                case 5:
                    system.displayBookedSeatSet();
                    break;
                case 0:
                    back = true;
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // Option 4
    private void userActivityMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- USERS AND ACTIVITY ---");
            System.out.println("1. View registered users");
            System.out.println("2. View recent system activity");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ")) {
                case 1:
                    system.displayRegisteredUsers();
                    break;
                case 2:
                    system.displaySystemRecentActions(10);
                    break;
                case 0:
                    back = true;
                    break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void displayTrains() {
        List<Train> trains = system.getAllTrains();
        System.out.println("\nID | Train | Route | Available/Total Seats");
        for (Train train : trains) displayTrain(train);
    }

    private void sortTrains() {
        boolean back = false;
        while (!back) {

            System.out.println("\n========== SORT TRAINS ==========");
            System.out.println("1. Sort by Train ID (Bubble sort)");
            System.out.println("2. Sort by Train name (Selection Sort)");
            System.out.println("3. Sort by Available Seats (Insertion Sort)");
            System.out.println("4. Sort by Total Seats (Merge Sort)");
            System.out.println("5. Sort by Start Station (Quick Sort)");
            System.out.println("0. Back");

            int choice = readInt("Choose sorting method: ");
            switch (choice) {

                case 1:
                    structure.TrainSorter.bubbleSortById(system.getTrains());
                    System.out.println("Trains sorted using Bubble Sort.");
                    displayTrains();
                    break;

                case 2:
                    structure.TrainSorter.selectionSortByName(system.getTrains());
                    System.out.println("Trains sorted using Selection Sort.");
                    displayTrains();
                    break;

                case 3:
                    structure.TrainSorter.insertionSortByAvailableSeats(system.getTrains());
                    System.out.println("Trains sorted using Insertion Sort.");
                    displayTrains();
                    break;

                case 4:
                    structure.TrainSorter.mergeSortByTotalSeats(system.getTrains());
                    System.out.println("Trains sorted using Merge Sort.");
                    displayTrains();
                    break;

                case 5:
                    structure.TrainSorter.quickSortByStartStation(system.getTrains());
                    System.out.println("Trains sorted using Quick Sort.");
                    displayTrains();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // Search Train
    private void searchTrain() {
        Train train = system.findTrainById(readText("Train ID: "));
        if (train == null) System.out.println("Train not found.");
        else displayTrain(train);
    }


    // Add train
    private void addTrain() {
        try {
            String id = readText("Train ID: ");
            String name = readText("Train name: ");
            displayStations();
            String start = readText("Start station ID or name: ");
            String destination = readText("Destination station ID or name: ");
            int total = readInt("Total Number of  seats: ");
            int available = readInt("Available seats: ");
            if (system.addTrain(id, name, start, destination, total, available)) {
                System.out.println("Train added successfully.");
            } else {
                System.out.println("Duplicate train ID, cannot add the Train.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Train was not added: " + exception.getMessage());
        }
    }


    // Remove train
    private void removeTrain() {
        String id = readText("Train ID to remove: ");
        if (system.removeTrain(id)) System.out.println("Train removed successfully.");
        else System.out.println("Train not found or it has active bookings.");
    }

    // Display Stations
    private void displayStations() {
        System.out.println("\nID | Station | Address");
        for (Station station : system.getAllStations()) {
            System.out.println(station.getStationId() + " | " + station.getStationName() + " | " + station.getStationAddress());
        }
    }


    // Add a station
    private void addStation() {
        try {
            boolean added = system.addStation(readText("Station ID: "),
                    readText("Station name: "), readText("Address: "));
            System.out.println(added ? "Station added successfully." : "Station ID or name already exists.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Station was not added: " + exception.getMessage());
        }
    }

    // Remove station
    private void removeStation() {
        String id = readText("Station ID to remove: ");
        if (system.removeStation(id)) System.out.println("Station and its routes were removed.");
        else System.out.println("Station not found, used by a train, or still has route connections.");
    }

    // add Route
    private void addRoute() {
        displayStations();
        String first = readText("First station ID: ");
        String second = readText("Second station ID: ");
        double distance = Double.parseDouble(readText("Distance: "));
        System.out.println(system.addRoute(first, second, distance)
                ? "Route added successfully." : "Route was not added. Check IDs or duplicates.");
    }

    // remove Route
    private void removeRoute() {
        String first = readText("First station ID: ");
        String second = readText("Second station ID: ");
        System.out.println(system.removeRoute(first, second)
                ? "Route removed successfully."
                : "Route not found or removal would disconnect an existing train.");
    }


    private void checkRoute() {
        displayStations();
        List<Station> route = system.findRoute(readText("Start station ID or name: "),
                readText("Destination station ID or name: "));
        if (route.isEmpty()) {
            System.out.println("No connected route was found.");
            return;
        }
        System.out.print("Shortest route (BFS): ");
        for (int i = 0; i < route.size(); i++) {
            if (i > 0) System.out.print(" -> ");
            System.out.print(route.get(i).getStationName());
        }
        System.out.println();
    }

    private void checkConnectivity() {
        displayStations();
        String start = readText("Start station ID or name: ");
        String destination = readText("Destination station ID or name: ");
        System.out.println(system.areStationsConnected(start, destination)
                ? "DFS result: the stations are connected."
                : "DFS result: the stations are not connected.");
    }

    // Traversal
    private void treeTraversal(boolean trainTree) {
        System.out.println("1. In-order");
        System.out.println("2. Pre-order");
        System.out.println("3. Post-order");
        int choice = readInt("Traversal type: ");
        String traversal;
        if (choice == 1) traversal = "IN";
        else if (choice == 2) traversal = "PRE";
        else if (choice == 3) traversal = "POST";
        else {
            System.out.println("Invalid traversal type.");
            return;
        }
        System.out.println("\n--- " + traversal + "-ORDER TRAVERSAL ---");
        if (trainTree) system.displayTrainTraversal(traversal);
        else system.displayBookingTraversal(traversal);
    }

    private void searchBooking() {
        Booking booking = system.findBookingById(readText("Booking ID: "));
        if (booking == null) System.out.println("Active booking not found.");
        else displayBooking(booking);
    }

    private void displayBookings() {
        List<Booking> bookings = system.getAllBookingRecords();
        if (bookings.isEmpty()) {
            System.out.println("No bookings recorded.");
            return;
        }
        for (Booking booking : bookings) displayBooking(booking);
    }

    private void displayTrain(Train train) {
        System.out.println(train.getTrainId() + " | " + train.getTrainName() + " | " + train.getStartStation() + " -> " + train.getDestination() + " | " + train.getAvailableSeats() + "/" + train.getTotalSeats());
    }

    private void displayBooking(Booking booking) {
        System.out.println(booking.getBookingID() + " | User: " + booking.getUserID() + " | Train: " + booking.getTrainID() + " | " + booking.getStartLocation() + " -> " + booking.getDestination() + " | Seats: " + booking.getSeatNumbers() + " | " + booking.getStatus());
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
