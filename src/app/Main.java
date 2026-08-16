package app;

import service.RailwayReservationSystem;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        RailwayReservationSystem system = new RailwayReservationSystem();
        Scanner scanner = new Scanner(System.in);
        new UserMenu(system, scanner).run();
        scanner.close();
    }
}
