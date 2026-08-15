package model;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final String fullName;
    private final String email;
    private final String password;
    private final String phone;

    private final List<Booking> bookings;

    public User(String username, String fullName, String email, String password, String phone, List<Booking> bookings) {
        this.username = requireText(username, "Username");
        this.fullName = requireText(fullName, "Full name");
        this.email = requireText(email, "Email");
        this.password = requireText(password, "Password");
        this.phone = requireText(phone, "Phone");
        this.bookings = bookings == null ? new ArrayList<Booking>() : new ArrayList<Booking>(bookings);
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
