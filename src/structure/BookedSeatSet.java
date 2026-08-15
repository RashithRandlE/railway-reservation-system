package structure;

import java.util.Locale;

public class BookedSeatSet {
    private static final int DEFAULT_CAPACITY = 16;
    private final SeatNode[] buckets;
    private final int capacity;
    private int size;

    private static class SeatNode {
        String seatKey;
        SeatNode next;
        SeatNode(String seatKey) { this.seatKey = seatKey; }
    }

    public BookedSeatSet() {
        this(DEFAULT_CAPACITY);
    }

    public BookedSeatSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Set capacity must be higher than 0.");
        }
        this.capacity = capacity;
        this.buckets = new SeatNode[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }

    private String buildKey(String trainId, String seatNumber) {
        if (trainId == null || trainId.trim().isEmpty()
                || seatNumber == null || seatNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Train ID and seat number cannot be empty.");
        }
        return trainId.trim().toUpperCase(Locale.ROOT) + "#"
                + seatNumber.trim().toUpperCase(Locale.ROOT);
    }

    // returns false when the seat is already taken (duplicate prevented)
    public boolean add(String trainId, String seatNumber) {
        if (contains(trainId, seatNumber)) {
            System.out.println("Error: Seat " + seatNumber + " on train " + trainId + " is already booked.");
            return false;
        }
        String key = buildKey(trainId, seatNumber);
        int index = hash(key);
        SeatNode newNode = new SeatNode(key);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        return true;
    }

    public boolean contains(String trainId, String seatNumber) {
        String key = buildKey(trainId, seatNumber);
        int index = hash(key);
        SeatNode current = buckets[index];
        while (current != null) {
            if (current.seatKey.equals(key)) return true;
            current = current.next;
        }
        return false;
    }

    public boolean remove(String trainId, String seatNumber) {
        String key = buildKey(trainId, seatNumber);
        int index = hash(key);
        SeatNode current = buckets[index];
        SeatNode previous = null;
        while (current != null) {
            if (current.seatKey.equals(key)) {
                if (previous == null) buckets[index] = current.next;
                else previous.next = current.next;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public int getSize() {
        return size;
    }
    public void displaySet() {
        System.out.println("\n===== BOOKED SEAT SET =====");
        if (size == 0) {
            System.out.println("No seats are currently booked.");
        }
        for (SeatNode bucket : buckets) {
            SeatNode current = bucket;
            while (current != null) {
                System.out.println(current.seatKey);
                current = current.next;
            }
        }
        System.out.println("Total booked seats: " + size);
        System.out.println("===========================");
    }
}
