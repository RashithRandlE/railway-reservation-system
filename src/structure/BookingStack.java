package structure;

import model.Booking;

public class BookingStack {

    private int size;

    private static class Node {
        Booking booking;
        String action;
        Node next;

        Node(Booking booking, String action){
            this.booking = booking;
            this.action = action;
            this.next = null;
        }
    }
    private Node top;

    public void push (Booking booking){push(booking, "Booked");}

    public void push(Booking booking, String action) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        if (action == null || action.trim().isEmpty()){
            throw new IllegalArgumentException("Action cannot be empty");
        }
        Node newNode = new Node(booking, action.trim().toUpperCase());

        newNode.next = top;
        top = newNode;
        size++;
    }
    public Booking pop () {
        if(top == null){
            return null;
        }

        Booking booking = top.booking;

        top = top.next;
        size--;

        return booking;
    }

    //View top booking
    public Booking peek() {

        if(top == null){
            return null;
        }
        return top.booking;
    }
    //Check empty
    public boolean isEmpty() {
        return top == null;
    }
    public void displayRecentActivity(int n) {
        System.out.println("\n===== RECENT BOOKING ACTIVITY (ALL USERS) =====");
        Node current = top;
        int count = 0;
        // A non-positive limit intentionally displays no records.
        while (current != null && count < n) {
            System.out.println((count + 1) + ". " + current.action + " | Booking " + current.booking.getBookingID()
                    + " | User: " + current.booking.getUserID()
                    + " | " + current.booking.getStartLocation() + " -> " + current.booking.getDestination());
            current = current.next;
            count++;
        }
        if (count == 0) System.out.println("No activity recorded.");
        System.out.println("=================================================");
    }

    public void displayRecentActivityForUser(String userId, int n) {
        System.out.println("\n===== YOUR RECENT ACTIVITY =====");
        Node current = top;
        int count = 0;
        while (current != null && count < n) {
            if (current.booking.getUserID().equals(userId)) {
                count++;
                System.out.println(count + ". " + current.action + " | Booking " + current.booking.getBookingID()
                        + " | " + current.booking.getStartLocation() + " -> " + current.booking.getDestination());
            }
            current = current.next;
        }
        if (count == 0) System.out.println("No activity recorded.");
        System.out.println("=================================");
    }

    public int getSize() {
        return size;
    }
}