package app;

import model.Booking;
import model.Station;
import model.Train;
import model.User;
import service.RailwayReservationSystem;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// this class handle all the user interactions
public class UserMenu {
    private final RailwayReservationSystem system;
    private final Scanner scanner;

    public UserMenu(RailwayReservationSystem system, Scanner scanner) {
        this.system = system;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== RAILWAY RESERVATION SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Log in");
            System.out.println("3. Admin login");
            System.out.println("0. Exit");

            switch (readInt("Choose an option: ")) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    new AdminMenu(system, scanner).loginAndRun();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using the railway reservation system.");
    }

    // Using this method we don't need to keep asking System.out .print or scanner.nextLine()
    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //If user choos option 1
    private void register() {
        try {
            String username = readText("Username: ");
            String fullName = readText("Full name: ");
            String email = readText("Email: ");
            String password = readText("Password: ");
            String phone = readText("Phone: ");

            if (system.registerUser(username, fullName, email, password, phone)) {
                System.out.println("Registration successful. Please log in.");
            } else {
                System.out.println("Registration failed: that username already exists.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Registration failed: " + exception.getMessage());
        }
    }

    private void login() {
        String username = readText("Username: ");
        String password = readText("Password: ");
        User user = system.login(username, password);
        if (user == null) {
            System.out.println("Incorrect username or password.");
            return;
        }
        System.out.println("Welcome, " + user.getFullName() + ".");
        LoggedInMenu(user);
    }

    private void LoggedInMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== USER MENU =====");
            System.out.println("1. Book a train");
            System.out.println("2. View all available trains");
            System.out.println("3. Check route between stations");
            System.out.println("4. Sort trains ");
            System.out.println("5. View booking status");
            System.out.println("6. Cancel booking");
            System.out.println("7. View recent actions");
            System.out.println("0. Log out");

            switch (readInt("Choose an option: ")) {
                case 1:
                    bookTrain(user);

                    break;
                case 2:
                    displayTrains(system.getAllTrains());

                    break;
                case 3:
                    checkRoute();

                    break;
                case 4:
                    sortTrains();

                    break;
                case 5:
                    displayBookingStatus(user);
                    break;
                case 6:
                    cancelBooking(user);
                    break;
                case 7:
                    system.displayRecentActions(user, 10);
                    break;
                case 0:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Book Train method
    private void bookTrain(User user) {
        displayStations();
        String start = readText("Enter start station ID or name: ");
        String destination = readText("Enter destination station ID or name: ");
        List<Train> matches = system.findTrains(start, destination);

        if (matches.isEmpty()) {
            System.out.println("No direct trains are available for that route.");
            return;
        }

        displayTrains(matches);
        String boardingName = system.resolveStationName(start);
        String arrivalName = system.resolveStationName(destination);
        System.out.println("\nYou can use following train(s)  from " + boardingName + " to " + arrivalName + ":");
        for (Train train : matches) {
            System.out.println("- " + train.getTrainId() + " " + train.getTrainName() + " | Board at " + boardingName + " | Leave at " + arrivalName
                    + " | Full service: " + train.getStartStation() + " -> " + train.getDestination() + " | Available seats: " + train.getAvailableSeats());
        }
        String trainId = readText("Enter the train ID to book: ");
        boolean belongsToRoute = false;
        for (Train train : matches) {
            if (train.getTrainId().equalsIgnoreCase(trainId)) {
                belongsToRoute = true;
                break;
            }
        }
        if (!belongsToRoute) {
            System.out.println("That train is not available for the selected route.");
            return;
        }
        List<String> seats = readSeatNumbers();
        try {
            Booking booking = system.bookTrain(user, trainId, start, destination, seats);
            System.out.println("Booking ID: " + booking.getBookingID());
            System.out.println("Seats: " + booking.getSeatNumbers());
            if (booking.getStatus() == Booking.Status.WAITLISTED) {
                System.out.println("The Train is fully booked.");
                System.out.println("Your request was added to the waiting list.");
            } else {
                System.out.println("Booking confirmed successfully.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Booking failed: " + exception.getMessage());
        }
    }

    // Display station method
    private void displayStations() {
        System.out.println("\n===== AVAILABLE STATIONS =====");
        for (Station station : system.getAllStations()) {
            System.out.println(station.getStationId() + " - " + station.getStationName());
        }
    }

    // Check the routes
    private void checkRoute() {
        displayStations();
        String start = readText("Enter start station ID or name: ");
        String destination = readText("Enter destination station ID or name: ");

        List<Station> route = system.findRoute(start, destination);
        if (route.isEmpty()) {
            System.out.println("No connected route was found. Check the station names or IDs.");
            return;
        }

        System.out.print("Shortest route (BFS): ");
        for (int i = 0; i < route.size(); i++) {
            if (i > 0) System.out.print(" -> ");
            System.out.print(route.get(i).getStationName());
        }
        System.out.println();
        System.out.println("Number of connections: " + (route.size() - 1));
    }



    private void displayBookingStatus(User user) {
        List<Booking> bookings = system.getBookingsFor(user);
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }
        System.out.println("\nID | Train | Route | Seat numbers | Status");
        for (Booking booking : bookings) {
            System.out.println(booking.getBookingID() + " | " + booking.getTrainID() + " | " + booking.getStartLocation() + " -> " + booking.getDestination() + " | " + booking.getSeatNumbers() + " | " + booking.getStatus());
        }

        system.displayUserWaitlistPosition(user);
    }
    // cancel booking
    private void cancelBooking(User user) {
        displayBookingStatus(user);
        String bookingId = readText("Enter booking ID to cancel: ");
        if (system.cancelBooking(user, bookingId)) {
            System.out.println("Booking cancelled successfully.");
            System.out.println("Waitlist checked: The next person in line got your seats.");
        } else {
            System.out.println("Active booking not found.");
        }
    }


    // display train method
    private void displayTrains(List<Train> trains) {
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }
        System.out.println("\nID | Train | Route | Available/Total Seats");
        for (Train train : trains) {
            System.out.println(train.getTrainId() + " | " + train.getTrainName()
                    + " | " + train.getStartStation() + " -> " + train.getDestination()

                    + " | " + train.getAvailableSeats() + "/" + train.getTotalSeats());
        }
    }

    // sort train
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
                    system.getTrains().displayTrains();
                    break;

                case 2:
                    structure.TrainSorter.selectionSortByName(system.getTrains());
                    System.out.println("Trains sorted using Selection Sort.");
                    system.getTrains().displayTrains();
                    break;

                case 3:
                    structure.TrainSorter.insertionSortByAvailableSeats(system.getTrains());
                    System.out.println("Trains sorted using Insertion Sort.");
                    system.getTrains().displayTrains();
                    break;

                case 4:
                    structure.TrainSorter.mergeSortByTotalSeats(system.getTrains());
                    System.out.println("Trains sorted using Merge Sort.");
                    system.getTrains().displayTrains();
                    break;

                case 5:
                    structure.TrainSorter.quickSortByStartStation(system.getTrains());
                    System.out.println("Trains sorted using Quick Sort.");
                    system.getTrains().displayTrains();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private List<String> readSeatNumbers() {
        String input = readText("Enter seat numbers separated by commas (example: 1,2): ");
        List<String> seats = new ArrayList<String>();
        for (String seat : input.split(",")) seats.add(seat.trim());
        return seats;
    }


}
