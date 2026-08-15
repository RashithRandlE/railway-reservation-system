package structure;

import model.Booking;

public class BookingAVLTree {

    private static class Node {
        Booking b;
        Node left, right;
        int h = 1;

        Node (Booking b) {
            this.b = b;
        }

    }

    private Node root;

    private int height(Node n) {
        return n == null ? 0 : n.h;
    }

    private int balance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;

        y.h = Math.max(height(y.left), height(y.right)) + 1;
        x.h = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node  leftRotate (Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;

        x.h = Math.max(height(x.left), height(x.right)) + 1;
        y.h = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Adding a new Booking (Insert)
    public void insert (Booking b) {
        if (b == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }

        root = insert(root, b);
    }

    private Node insert(Node n, Booking b) {
        if (n == null)
            return new Node(b);

        int c = b.getBookingID().compareTo(n.b.getBookingID());

        // check if the booking ID is already exit
        if (c == 0) {
            System.out.println("Duplicate Booking ID: " + b.getBookingID());

            return n;
        }
        //choose correct side for a new booking.
        if (c < 0)
            n.left = insert(n.left, b);
        else
            n.right = insert(n.right, b);

        n.h = Math.max(height(n.left), height(n.right)) + 1;

        int bal = balance(n);

        // Left - Left case
        if (bal > 1 &&
                b.getBookingID().compareTo(n.left.b.getBookingID()) < 0)
            return rightRotate(n);

        // Right - Right case
        if (bal < -1 &&
                b.getBookingID().compareTo(n.right.b.getBookingID()) > 0)
            return leftRotate(n);

        // Left - Right case
        if (bal > 1) {
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }

        // Right - Left case
        if (bal < -1) {
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }

        return n;
    }

    // finding booking using BookingID (Search)
    public Booking search(String id) {

        if (id == null || id.trim().isEmpty()) return null;
        id = id.trim();

        Node n = root;

        while (n != null) {
            int c = id.compareTo(n.b.getBookingID());

            if (c == 0)
                return n.b;

            n = c < 0 ? n.left : n.right;
        }

        return null;
    }

    // removing booking from the tree (Delete)
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) return;
        root = delete(root, id.trim());
    }

    private Node delete(Node n, String id) {

        if (n == null)
            return null;

        int c = id.compareTo(n.b.getBookingID());

        if (c < 0)
            n.left = delete(n.left, id);

        else if (c > 0)
            n.right = delete(n.right, id);

        else {

            if (n.left == null)
                return n.right;

            if (n.right == null)
                return n.left;

            Node t = n.right;

            while (t.left != null)
                t = t.left;

            n.b = t.b;
            n.right = delete(n.right,
                    t.b.getBookingID());
        }

        n.h = Math.max(height(n.left), height(n.right))+1;

        int bal = balance(n);

        if (bal > 1 && balance(n.left) >= 0)
            return rightRotate(n);

        if (bal > 1) {
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }

        if (bal < -1 && balance(n.right) <= 0)
            return leftRotate(n);

        if (bal < -1) {
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }

        return n;
    }

    // display what are they booking in order. (In -Order)
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node n) {
        if (n != null) {
            inOrder(n.left);
            System.out.println(n.b.getBookingID());
            inOrder(n.right);
        }
    }

    // display bookings starting from root. (Pre -Order)
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node n) {
        if (n != null) {
            System.out.println(n.b.getBookingID());
            preOrder(n.left);
            preOrder(n.right);
        }
    }

    // display bookings after we visiting child nodes. (Post -Order)
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node n) {
        if (n != null) {
            postOrder(n.left);
            postOrder(n.right);
            System.out.println(n.b.getBookingID());
        }
    }
}
