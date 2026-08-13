package model;

public class Station {
    private final String stationId;
    private final String stationName;
    private final String stationAddress;

    public Station(String stationId, String stationName, String stationAddress) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationAddress = stationAddress;
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
}
