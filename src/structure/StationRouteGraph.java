package structure;

import model.Station;
import java.util.*;

public class StationRouteGraph {
    private final Map<String, Station> stations = new LinkedHashMap<>();
    private final Map<String, Map<String, Double>> graph = new LinkedHashMap<>();

    // Add a station
    public boolean addStation(Station station) {
        if (station == null || station.getStationId().trim().isEmpty()) {
            return false;
        }
        String id = normaliseId(station.getStationId());
        if (stations.containsKey(id)) {
            return false;
        }
        stations.put(id, station);
        graph.put(id, new LinkedHashMap<>());

        return true;
    }

    // Add a two-way route with distance
    public boolean addRoute(String first, String second, double distance) {

        first = normaliseId(first);
        second = normaliseId(second);

        if (!stations.containsKey(first) || !stations.containsKey(second) || first.equals(second)
                || distance <= 0 || graph.get(first).containsKey(second)) {
            return false;
        }
        graph.get(first).put(second, distance);
        graph.get(second).put(first, distance);

        return true;
    }

    // Remove a two-way route
    public boolean removeRoute(String first, String second) {
        first = normaliseId(first);
        second = normaliseId(second);

        if (!graph.containsKey(first) || !graph.containsKey(second)) {
            return false;
        }
        boolean removed = graph.get(first).remove(second) != null;
        graph.get(second).remove(first);
        return removed;
    }

    // Remove a station and  its connected routes
    public boolean removeStation(String stationId) {
        stationId = normaliseId(stationId);

        if (!stations.containsKey(stationId)) {
            return false;
        }
        for (String neighbour : new ArrayList<>(graph.get(stationId).keySet())) {
            graph.get(neighbour).remove(stationId);
        }
        graph.remove(stationId);
        stations.remove(stationId);

        return true;
    }

    // Check whether a station has routes
    public boolean hasRoutes(String stationId) {
        stationId = normaliseId(stationId);
        return graph.containsKey(stationId) && !graph.get(stationId).isEmpty();
    }

    // BFS (Find route with fewest stops)
    public ArrayList<String> bfs(String start, String destination) {

        start = normaliseId(start);
        destination = normaliseId(destination);

        ArrayList<String> route = new ArrayList<>();
        if (!graph.containsKey(start) || !graph.containsKey(destination)) {
            return route;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                break;
            }

            for (String next : graph.get(current).keySet()) {
                if (visited.add(next)) {
                    queue.add(next);
                    previous.put(next, current);
                }
            }
        }

        if (!visited.contains(destination)) {
            return route;
        }

        String current = destination;
        while (current != null) {
            route.add(current);
            current = previous.get(current);
        }

        Collections.reverse(route);
        return route;
    }

    // DFS (Check connectivity)
    public boolean isConnected(String start, String destination) {

        start = normaliseId(start);
        destination = normaliseId(destination);

        if (!graph.containsKey(start) || !graph.containsKey(destination)) {
            return false;
        }

        Set<String> visited = new HashSet<>();
        dfs(start, visited);

        return visited.contains(destination);
    }

    // DFS recursive
    private void dfs(String current, Set<String> visited) {
        visited.add(current);

        for (String next : graph.get(current).keySet()) {
            if (!visited.contains(next)) {
                dfs(next, visited);
            }
        }
    }

    // Get distance between two stations
    public double getDistance(String firstStationId, String secondStationId) {
        if (!graph.containsKey(firstStationId) ||
                !graph.get(firstStationId).containsKey(secondStationId)) {
            return -1;
        }

        return graph.get(firstStationId).get(secondStationId);
    }

    // Calculate total distance
    public double calculateDistance(ArrayList<String> route) {
        double total = 0;

        for (int i = 0; i < route.size() - 1; i++) {
            String current = route.get(i);
            String next = route.get(i + 1);
            total += graph.get(current).get(next);
        }

        return total;
    }

    // Display graph
    public void displayGraph() {
        System.out.println("\n========= Railway Network =========");

        for (String stationId : graph.keySet()) {
            System.out.print(stations.get(stationId).getStationName() + " --> ");

            for (Map.Entry<String, Double> route : graph.get(stationId).entrySet()) {
                System.out.print(stations.get(route.getKey()).getStationName() + " (" + route.getValue() + " km), ");
            }
            System.out.println();
        }
    }

    // Normalise station ID
    private String normaliseId(String stationId) {
        return stationId == null ? "" : stationId.trim().toUpperCase(Locale.ROOT);
    }
}