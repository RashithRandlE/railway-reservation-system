package service;

import model.Booking;
import model.Station;
import model.Train;
import model.User;
import model.Ticket;
import structure.BookingAVLTree;
import structure.BookingStack;
import structure.BookingWaitingListQueue;
import structure.BookedSeatSet;
import structure.StationArray;
import structure.StationRouteGraph;
import structure.TrainBST;
import structure.TrainLinkedList;
import structure.UserHashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;


public class RailwayReservationSystem {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    //Strat with creating all the things like array ect
    private final UserHashTable users = new UserHashTable();
    private final StationArray stations = new StationArray(20);
    private final StationRouteGraph routes = new StationRouteGraph();
    private final TrainLinkedList trains = new TrainLinkedList();
    private final TrainBST trainIndex = new TrainBST();
    private final BookingAVLTree bookings = new BookingAVLTree();
    private final BookingWaitingListQueue waitingList = new BookingWaitingListQueue();
    private final BookingStack recentActions = new BookingStack();
    private final BookedSeatSet bookedSeats = new BookedSeatSet(64);
    private final List<Booking> bookingRecords = new ArrayList<Booking>();

    private void loadSampleData() {
        addStation(new Station("CMB", "Colombo Fort", "Colombo"));
        addStation(new Station("GPH", "Gampaha", "Gampaha"));
        addStation(new Station("VGD", "Veyangoda", "Gampaha"));
        addStation(new Station("PLG", "Polgahawela", "Kurunegala"));
        addStation(new Station("RBG", "Rambukkana", "Kegalle"));
        addStation(new Station("KDY", "Kandy", "Kandy"));
        addStation(new Station("NWP", "Nawalapitiya", "Kandy"));
        addStation(new Station("HTN", "Hatton", "Nuwara Eliya"));
        addStation(new Station("NUO", "Nanu Oya", "Nuwara Eliya"));
        addStation(new Station("ELL", "Ella", "Badulla"));
        addStation(new Station("BDL", "Badulla", "Badulla"));

        // The graph stores adjacent connections in both directions.
        routes.addRoute("CMB", "GPH", 20);
        routes.addRoute("GPH", "VGD", 25);
        routes.addRoute("VGD", "PLG",10);
        routes.addRoute("PLG", "RBG", 15);
        routes.addRoute("RBG", "KDY", 20);
        routes.addRoute("KDY", "NWP",30);
        routes.addRoute("NWP", "HTN",25);
        routes.addRoute("HTN", "NUO",10);
        routes.addRoute("NUO", "ELL",15);
        routes.addRoute("ELL", "BDL",25);

        addTrain(new Train("T001", "Podi Menike", "Colombo Fort", "Badulla", 500, 450));
        addTrain(new Train("T002", "Kandy Intercity", "Colombo Fort", "Kandy", 400, 350));
        addTrain(new Train("T003", "Main Line Express", "Colombo Fort", "Polgahawela", 450, 400));
        addTrain(new Train("T004", "Gampaha-Kandy Express", "Gampaha", "Kandy", 300, 0));
        addTrain(new Train("T005", "Udarata Menike", "Colombo Fort", "Badulla", 420, 300));
        addTrain(new Train("T006", "Ella Odyssey", "Colombo Fort", "Badulla", 280, 200));
        addTrain(new Train("T007", "Hill Country Express", "Kandy", "Badulla", 250, 180));
        addTrain(new Train("T008", "Badulla Night Mail", "Badulla", "Colombo Fort", 400, 250));
        addTrain(new Train("T009", "Kandy-Colombo Intercity", "Kandy", "Colombo Fort", 300, 220));

        // Add dummy bookings directly to the AVL tree for traversal testing
        Booking b1 = new Booking("randil", "T001", "Colombo Fort", "Badulla", 2, Arrays.asList("1", "2"));
        Booking b2 = new Booking("kaveesha", "T002", "Colombo Fort", "Kandy", 1, Arrays.asList("5"));
        Booking b3 = new Booking("user3", "T003", "Colombo Fort", "Polgahawela", 3, Arrays.asList("10", "11", "12"));
        Booking b4 = new Booking("abc234", "T004", "Gampaha", "Kandy", 2, Arrays.asList("20", "21"));
        Booking b5 = new Booking("osh", "T005", "Colombo Fort", "Badulla", 1, Arrays.asList("42"));
        
        bookings.insert(b1);
        bookings.insert(b2);
        bookings.insert(b3);
        bookings.insert(b4);
        bookings.insert(b5);
        
        bookingRecords.add(b1);
        bookingRecords.add(b2);
        bookingRecords.add(b3);
        bookingRecords.add(b4);
        bookingRecords.add(b5);
    }


    // Start with loading data
    public RailwayReservationSystem() {
        loadSampleData();
    }

    // Registers a new user by inserting it to the user hash table
    public boolean registerUser(String username, String fullName, String email, String password, String phone) {
        User user = new User(username, fullName, email, password, phone, new ArrayList<Booking>());
        return users.addUser(user);
    }

    // authenticate a user using the hash table
    public User login(String username, String password) {
        if (password == null) return null;
        User user = users.getUser(username);
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    // Authenticates admin
    public boolean adminLogin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }


    public List<Train> getAllTrains() {

        return Collections.unmodifiableList(trains.getAllTrains());
    }
    public TrainLinkedList getTrains() {
        return trains;
    }

    // Retrieves all stations

    public Station[] getAllStations() {
        return stations.getStations();
    }

    // This find trains that can travel between 2 station, it use graph bfs.

    public List<Train> findTrains(String startInput, String destinationInput) {
        Station start = findStation(startInput);
        Station destination = findStation(destinationInput);
        List<Train> matches = new ArrayList<Train>();
        if (start == null || destination == null
                || start.getStationId().equalsIgnoreCase(destination.getStationId())) return matches;

        // A train is usable when both requested stations occur on its ordered
        // graph path and the destination comes after the boarding station.
        for (Train train : trains.getAllTrains()) {
            Station serviceStart = findStation(train.getStartStation());
            Station serviceEnd = findStation(train.getDestination());
            if (serviceStart == null || serviceEnd == null) continue;
            List<String> servicePath = routes.bfs(serviceStart.getStationId(), serviceEnd.getStationId());
            int boardingIndex = indexOfIgnoreCase(servicePath, start.getStationId());
            int destinationIndex = indexOfIgnoreCase(servicePath, destination.getStationId());
            if (boardingIndex >= 0 && destinationIndex > boardingIndex) matches.add(train);
        }
        return matches;
    }



    //   use to get the real station name from id and name
    public String resolveStationName(String input) {
        Station station = findStation(input);
        return station == null ? input : station.getStationName();
    }

    // return bfs router
    public List<Station> findRoute(String startInput, String destinationInput) {
        Station start = findStation(startInput);
        Station destination = findStation(destinationInput);
        List<Station> result = new ArrayList<Station>();
        if (start == null || destination == null) return result;

        for (String stationId : routes.bfs(start.getStationId(), destination.getStationId())) {
            Station station = stations.searchStationById(stationId);
            if (station != null) result.add(station);
        }
        return result;
    }


    public Booking bookTrain(User user, String trainId, int seatCount) {
        List<String> generatedSeats = new ArrayList<String>();
        for (int i = 1; i <= seatCount; i++) generatedSeats.add(String.valueOf(i));
        return bookTrain(user, trainId, generatedSeats);
    }

    public Booking bookTrain(User user, String trainId, List<String> requestedSeats) {
        Train train = trainIndex.search(trainId);
        if (train == null) throw new IllegalArgumentException("Train ID was not found.");
        return bookTrain(user, trainId, train.getStartStation(), train.getDestination(), requestedSeats);
    }



    public Booking bookTrain(User user, String trainId, String boardingStation, String arrivalStation, List<String> requestedSeats) {

        if (user == null)
            throw new
                    IllegalArgumentException("A logged-in user is required.");
        Train train = trainIndex.search(trainId);
        if (train == null)
            throw new
                    IllegalArgumentException("Train ID was not found.");
        if (!findTrains(boardingStation, arrivalStation).contains(train)) {
            throw new IllegalArgumentException("This train cannot be used for the selected journey.");
        }
        List<String> seats = validateSeats(train, requestedSeats);

        Booking booking = new Booking(user.getUsername(), train.getTrainId(),
                resolveStationName(boardingStation), resolveStationName(arrivalStation),
                seats.size(), seats);

        if (train.getAvailableSeats() < seats.size() || !areSeatsAvailable(train.getTrainId(), seats)) {
            booking.setStatus(Booking.Status.WAITLISTED);
            waitingList.enqueue(booking);
        } else {
            booking.setStatus(Booking.Status.CONFIRMED);
            train.reserveSeats(seats.size());
            reserveSeatNumbers(train.getTrainId(), seats);

            // Find start and destination stations
            Station startStation = findStation(boardingStation);
            Station destinationStation = findStation(arrivalStation);

            // Find the route using the graph
            List<String> route = routes.bfs(
                    startStation.getStationId(),
                    destinationStation.getStationId()
            );

            // Calculate total distance using existing method
            double distance = routes.calculateDistance(
                    new ArrayList<>(route)
            );

            // Create ticket
            Ticket ticket = new Ticket(booking, distance);

            // Display ticket
            ticket.displayTicket();
        }


        bookings.insert(booking);
        bookingRecords.add(booking);
        user.getBookings().add(booking);
        recentActions.push(booking, booking.getStatus() == Booking.Status.CONFIRMED
                ? "BOOKED" : "WAITLISTED");
        return booking;
    }

    public boolean cancelBooking(User user, String bookingId) {
        if (user == null || bookingId == null) return false;
        Booking booking = bookings.search(bookingId);
        if (booking == null
                || !booking.getUserID().equalsIgnoreCase(user.getUsername())
                || booking.getStatus() == Booking.Status.CANCELLED) {
            return false;
        }

        if (booking.getStatus() == Booking.Status.WAITLISTED) {
            waitingList.remove(booking.getBookingID());
        } else {
            Train train = trainIndex.search(booking.getTrainID());
            if (train != null) {
                releaseSeatNumbers(booking);
                train.releaseSeats(booking.getTotalSeats());
                promoteWaitingBookings(train);
            }
        }

        booking.setStatus(Booking.Status.CANCELLED);
        bookings.delete(booking.getBookingID());
        recentActions.push(booking, "CANCELLED");
        return true;
    }

    public List<Booking> getBookingsFor(User user) {
        if (user == null) return Collections.emptyList();
        return Collections.unmodifiableList(user.getBookings());
    }

    public void displayRecentActions(User user, int limit) {
        recentActions.displayRecentActivityForUser(user.getUsername(), limit);
    }

    // ----- Admin operations -----

    public boolean addTrain(String id, String name, String start, String destination,
                            int totalSeats, int availableSeats) {
        Station startStation = findStation(start);
        Station endStation = findStation(destination);
        if (startStation == null || endStation == null || trainIndex.search(id) != null
                || !routes.isConnected(startStation.getStationId(), endStation.getStationId())) return false;
        return addTrain(new Train(id, name, startStation.getStationName(),
                endStation.getStationName(), totalSeats, availableSeats));
    }

    public boolean removeTrain(String trainId) {
        Train train = trainIndex.search(trainId);
        if (train == null || hasActiveBookings(train.getTrainId())) return false;
        trainIndex.delete(train.getTrainId());
        return trains.deleteTrain(train.getTrainId());
    }

    public Train findTrainById(String trainId) {
        return trainIndex.search(trainId);
    }

    public boolean addStation(String id, String name, String address) {
        if (stations.searchStationById(id) != null || stations.searchStation(name) != null) return false;
        return addStation(new Station(id, name, address));
    }

    public boolean removeStation(String stationId) {
        Station station = stations.searchStationById(stationId);
        if (station == null || isStationUsedByTrain(station.getStationName())
                || routes.hasRoutes(station.getStationId())) return false;
        routes.removeStation(station.getStationId());
        return stations.removeStation(station.getStationId());
    }

    public boolean addRoute(String firstStationId, String secondStationId, double distance) {
        return routes.addRoute(firstStationId, secondStationId, distance);
    }

    public boolean removeRoute(String firstStationId, String secondStationId) {
        double distance = routes.getDistance(firstStationId, secondStationId);

        if (!routes.removeRoute(firstStationId, secondStationId)) return false;
        // Roll back when this removal would break the path used by an existing train.
        for (Train train : trains.getAllTrains()) {
            Station start = findStation(train.getStartStation());
            Station end = findStation(train.getDestination());
            if (start != null && end != null
                    && !routes.isConnected(start.getStationId(), end.getStationId())) {
                routes.addRoute(firstStationId, secondStationId, distance);
                return false;
            }
        }
        return true;
    }

    public Booking findBookingById(String bookingId) {

        return bookings.search(bookingId);
    }

    public List<Booking> getAllBookingRecords() {
        return Collections.unmodifiableList(bookingRecords);
    }

    public void displayRegisteredUsers() {
        users.displayTable();
    }

    public void displayWaitingList() {
        waitingList.displayQueue();
    }

    public void displayUserWaitlistPosition(User user) {
        waitingList.displayQueueForUser(user.getUsername());
    }

    public void displaySystemRecentActions(int limit) {
        recentActions.displayRecentActivity(limit);
    }

    public void displayRouteGraph() {
        routes.displayGraph();
    }

    public boolean areStationsConnected(String startInput, String destinationInput) {
        Station start = findStation(startInput);
        Station destination = findStation(destinationInput);
        return start != null && destination != null
                && routes.isConnected(start.getStationId(), destination.getStationId());
    }

    public void displayTrainTraversal(String traversal) {
        if ("PRE".equalsIgnoreCase(traversal))
            trainIndex.preorder();
        else if ("POST".equalsIgnoreCase(traversal))
            trainIndex.postorder();
        else
            trainIndex.inorder();
    }

    public void displayBookingTraversal(String traversal) {
        if ("PRE".equalsIgnoreCase(traversal))
            bookings.preOrder();
        else if ("POST".equalsIgnoreCase(traversal))
            bookings.postOrder();
        else
            bookings.inOrder();
    }

    public void displayBookedSeatSet() {
        bookedSeats.displaySet();
    }

    private Station findStation(String input) {
        if (input == null) return null;
        Station station = stations.searchStationById(input);
        return station != null ? station : stations.searchStation(input);
    }

    private int indexOfIgnoreCase(List<String> values, String target) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equalsIgnoreCase(target)) return i;
        }
        return -1;
    }

    private List<String> validateSeats(Train train, List<String> requestedSeats) {
        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new IllegalArgumentException("At least one seat number is required.");
        }
        List<String> result = new ArrayList<String>();
        for (String value : requestedSeats) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("Seat numbers cannot be empty.");
            }
            String seat = value.trim();
            int number;
            try {
                number = Integer.parseInt(seat);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Seat numbers must be numeric.");
            }
            if (number < 1 || number > train.getTotalSeats()) {
                throw new IllegalArgumentException("Seat " + number + " is outside this train's capacity.");
            }
            String normalized = String.valueOf(number);
            if (result.contains(normalized)) {
                throw new IllegalArgumentException("Seat " + normalized + " was entered more than once.");
            }
            result.add(normalized);
        }
        return result;
    }

    private boolean areSeatsAvailable(String trainId, List<String> seats) {
        for (String seat : seats) {
            if (bookedSeats.contains(trainId, seat)) return false;
        }
        return true;
    }

    private void reserveSeatNumbers(String trainId, List<String> seats) {
        for (String seat : seats) bookedSeats.add(trainId, seat);
    }

    private void releaseSeatNumbers(Booking booking) {
        for (String seat : booking.getSeatNumbers()) {
            bookedSeats.remove(booking.getTrainID(), seat);
        }
    }

    // Scan one complete queue cycle in FIFO order. Every request for this train
    // that fits the available capacity and has free seat numbers is promoted.
    // Other requests are re-enqueued in their original relative order.

    private void promoteWaitingBookings(Train train) {
        int originalSize = waitingList.getSize();
        for (int i = 0; i < originalSize; i++) {
            Booking candidate = waitingList.dequeue();
            if (candidate.getTrainID().equalsIgnoreCase(train.getTrainId())

                    && train.getAvailableSeats() >= candidate.getTotalSeats()

                    && areSeatsAvailable(train.getTrainId(), candidate.getSeatNumbers())) {

                train.reserveSeats(candidate.getTotalSeats());

                reserveSeatNumbers(train.getTrainId(), candidate.getSeatNumbers());

                candidate.setStatus(Booking.Status.CONFIRMED);

                recentActions.push(candidate, "PROMOTED");
            } else {
                waitingList.enqueue(candidate);
            }
        }
    }

    private boolean hasActiveBookings(String trainId) {
        for (Booking booking : bookingRecords) {
            if (booking.getTrainID().equalsIgnoreCase(trainId)
                    && booking.getStatus() != Booking.Status.CANCELLED)
                return true;
        }
        return false;
    }

    private boolean isStationUsedByTrain(String stationName) {
        for (Train train : trains.getAllTrains()) {
            if (train.getStartStation().equalsIgnoreCase(stationName)
                    || train.getDestination().equalsIgnoreCase(stationName))
                return true;
        }
        return false;
    }

    private boolean addStation(Station station) {
        if (!stations.addStation(station))
            return false;
        routes.addStation(station);
        return true;
    }

    private boolean addTrain(Train train) {
        if (!trains.addTrain(train))
            return false;
        trainIndex.insert(train);
        return true;
    }


}
