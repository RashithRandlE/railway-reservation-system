package structure;
import model.Station;

public class StationArray {
    private final Station[] stations;
    private int size;

    // Create a fixed-capacity station array.

    public StationArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Station capacity must be higher than 0.");
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
            System.out.println("Cannot add an empty station.");
            return false;
        }

        if (isFull()) {
            System.out.println("Station array is full. Cannot add another station.");
            return false;
        }
        // Both station ID and station name identify a station and must be unique.
        if (searchStation(station.getStationName()) != null
                || searchStationById(station.getStationId()) != null) {
            System.out.println("Error: Station already exists.");
            return false;
        }

        stations[size] = station;
        size++;

        System.out.println("Station: " + station.getStationName() + " added successfully.");
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
        if (stationName == null || stationName.trim().isEmpty()) {
            return null;
        }
        stationName = stationName.trim();
        for (int i = 0; i < size; i++) {
            if (stations[i].getStationName().equalsIgnoreCase(stationName)) {
                return stations[i];
            }
        }
        return null;
    }

    // Linear search by station ID.
    public Station searchStationById(String stationId) {
        if (stationId == null || stationId.trim().isEmpty()) return null;
        for (int i = 0; i < size; i++) {
            if (stations[i].getStationId().equalsIgnoreCase(stationId.trim())) {
                return stations[i];
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    // Remove by station ID and shift later elements to keep the array compact.
    public boolean removeStation(String stationId) {
        if (stationId == null || stationId.trim().isEmpty()) return false;
        for (int i = 0; i < size; i++) {
            if (stations[i].getStationId().equalsIgnoreCase(stationId.trim())) {
                for (int j = i; j < size - 1; j++) {
                    stations[j] = stations[j + 1];
                }
                stations[--size] = null;
                return true;
            }
        }
        return false;
    }

    // Return a copy so menu code can read stations without changing this array.
    public Station[] getStations() {
        Station[] result = new Station[size];
        System.arraycopy(stations, 0, result, 0, size);
        return result;
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
