package structure;

import model.User;
import java.util.Locale;

//We used a hash table for store users by username and separate chaining used to handle collisions

public class UserHashTable {
    private static final int DEFAULT_CAPACITY = 16;
    private final UserNode[] buckets;
    private final int capacity;
    private int size;

    //Used a node inside in each bucket
    private static class UserNode {
        User user;
        UserNode next;

        UserNode(User user) {

            this.user = user;

        }
    }

    public UserHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public UserHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "The hash table capacity must be higher than 0."
            );
        }
        this.capacity = capacity;
        this.buckets = new UserNode[capacity];
        this.size = 0;
    }

    //Get the bucket for a username
    private int hash(String username) {
        username = username.trim().toLowerCase(Locale.ROOT);

        int hash = 0;

        for (int i = 0; i < username.length(); i++) {
            hash = (hash * 31 + username.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }

    //Adding a User
    public boolean addUser(User user) {
        if (user == null) return false;

        //Checking for duplicated usernames
        if (getUser(user.getUsername()) != null) {
            System.out.println("Error : Username already exists.");
            return false;
        }

        int index = hash(user.getUsername());
        UserNode newNode = new UserNode(user);

        //Adding a user to the front of the bucket
        newNode.next = buckets[index];
        buckets[index] = newNode;

        size++;
        return true;
    }

    //Search and find a user by username
    public User getUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        username = username.trim();

        int index = hash(username);
        UserNode current = buckets[index];

        while (current != null) {
            if (current.user.getUsername().equalsIgnoreCase(username)) {
                return current.user;
            }

            current = current.next;

        }

        return null;

    }

    //Remove the matching User
    public boolean removeUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        username = username.trim();

        int index = hash(username);

        UserNode current = buckets[index];
        UserNode previous = null;

        while (current != null) {
            if (current.user.getUsername().equalsIgnoreCase(username)) {

                //If the user is the first node in bucket
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return true;
            }
            previous = current;
            current = current.next;

        }

        return false;
    }

    //Load Factor
    public double loadFactor() {
        return (double) size / capacity;
    }

    public int getSize() {
        return size;
    }

    //Display all buckets and stored usernames
    public void displayTable() {
        System.out.println("\n ====== USER HASH TABLE ======");

        for (int i = 0; i < capacity; i++) {

            System.out.print("Bucket " + i + " : ");

            UserNode current = buckets[i];

            if (current == null) {
                System.out.println("(empty )");
                continue;
            }

            while (current != null) {
                System.out.print(current.user.getUsername());

                if (current.next != null) {
                    System.out.print(" -> ");
                }

                current = current.next;
            }

            System.out.println();
        }

        System.out.println("Load Factor: " + loadFactor());
        System.out.println("===============================");
    }
}