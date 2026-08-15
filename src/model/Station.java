package model;

public class Station {
    private final String stationId;
    private final String stationName;
    private final String stationAddress;

    public Station(String stationId, String stationName, String stationAddress) {

        this.stationId = requireText(stationId, "Station ID");
        this.stationName = requireText(stationName, "Station name");
        this.stationAddress = requireText(stationAddress, "Station address");
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
