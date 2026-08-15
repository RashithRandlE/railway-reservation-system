package structure;

import model.Booking;

public class BookingWaitingListQueue {

    private static class Node{
        Booking booking;
        Node next;

        Node (Booking booking) {
            this.booking = booking;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public BookingWaitingListQueue(){
        front = null;
        rear = null;
    }

    //Add booking to the queue
    public void enqueue(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }
        Node newNode = new Node(booking);

        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    //Romove booking from the queue
    public Booking dequeue() {
        if (front == null) {
            return null;
        }

        Booking booking = front.booking;

        front = front.next;

        if (front == null) {
            rear = null;
        }
        size--;

        return booking;
    }
    //view the first booking
    public Booking peek() {

        if (front == null) {
            return null;
        }
        return front.booking;
    }
    //Check whether queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    public int getSize() {
        return size;
    }

    // Remove a specific waitlisted booking while preserving queue order.
    public boolean remove(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) return false;
        Node current = front;
        Node previous = null;

        while (current != null) {
            if (current.booking.getBookingID().equalsIgnoreCase(bookingId.trim())) {
                if (previous == null) front = current.next;
                else previous.next = current.next;
                if (current == rear) rear = previous;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
    public void displayQueue() {
        System.out.println("\n===== BOOKING WAITLIST (ALL USERS) =====");
        Node current = front;
        int position = 1;
        if (current == null) {
            System.out.println("No one on the waitlist.");
        }
        while (current != null) {
            System.out.println(position + ". Booking " + current.booking.getBookingID()
                    + " | User: " + current.booking.getUserID());
            current = current.next;
            position++;
        }
        System.out.println("==========================================");
    }

    public void displayQueueForUser(String userId) {
        System.out.println("\n===== YOUR WAITLIST STATUS =====");
        Node current = front;
        int position = 1;
        boolean found = false;
        while (current != null) {
            if (current.booking.getUserID().equals(userId)) {
                System.out.println("Position " + position + ": Booking " + current.booking.getBookingID());
                found = true;
            }
            current = current.next;
            position++;
        }
        if (!found) System.out.println("You have no bookings on the waitlist.");
        System.out.println("=================================");
    }
}
