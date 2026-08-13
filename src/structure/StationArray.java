package structure;
import model.Station;

public class StationArray {
    private final Station[] stations;
    private int size;

    // this is the constructor and it inizilize a fixed size array

    public StationArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Station capacity must be higher than 0.");// this is to handle empty array
        }
        this.stations = new Station[capacity];
        this.size = 0;
    }


    // add stations
    public boolean isFull() {
        return size == stations.length;
    }

    public boolean addStation(Station station) {

        if (station == null) {
            System.out.println("Cannot add a empty station!!!!!.");
            return false;
        }

        if (isFull()) {
            System.out.println("Station array is full. Cannot add another station!!!!!.");
            return false;
        }
        Station foundStation = searchStation(station.getStationName());
        if (foundStation != null) {
            System.out.println("Error: Station already exist!!!!");
            return false;
        }

        stations[size] = station;
        size = size + 1;

        System.out.println("Station:" + station.getStationName() + "added successfully!!!!");
        return true;
    }

    public boolean addStation(String stationId, String stationName, String stationAddress) {
        if (stationName == null || stationName.trim().isEmpty()) {
            System.out.println("Error: Station name cannot be empty.");
            return false;
        }
        Station newStation = new Station(stationId, stationName, stationAddress);
        return addStation(newStation);
    }

    //Linear Search
    public Station searchStation(String stationName) {
        if (stationName == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (stations[i].getStationName().equalsIgnoreCase(stationName)) {
                return stations[i];
            }
        }
        return null;
    }


    public void displayStations() {
        if (size == 0) {
            System.out.println("No stations stored.");
            return;
        }

        System.out.println("\n ===== RAILWAY STATION =====");
        for (int i = 0; i < size; i = i + 1) {
            System.out.println("Station " + (i + 1) + ": [" + stations[i].getStationId() + "] " +
                    stations[i].getStationName() + " - " + stations[i].getStationAddress());
        }
        System.out.println("========================");
    }
}




