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
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.bookings = new ArrayList<>();
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
}
