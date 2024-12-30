package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        while (true) {
            displayMainMenu();
            int action = getUserInputInt(scanner, "Please enter your choice: ");

            switch (action) {
                case 1 -> handleRentVehicle(scanner, admin);
                case 2 -> handleReturnVehicle(scanner, admin);
                case 3 -> handleAdminTask(scanner, admin, Main::addVehicle);
                case 4 -> handleAdminTask(scanner, admin, Admin::displayFleet);
                case 5 -> {
                    System.out.println("Exiting the application.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid action. Please try again.");
            }

            if (!shouldContinue(scanner)) {
                System.out.println("Exiting the application.");
                break;
            }
        }
    }

    private static void addVehicle() {
    }

    private static void displayMainMenu() {
        System.out.println("Welcome to the Vehicle Rental System");
        System.out.println("1. Rent a vehicle");
        System.out.println("2. Return a vehicle");
        System.out.println("3. Add a vehicle (Admin)");
        System.out.println("4. Display vehicle fleet (Admin)");
        System.out.println("5. Exit");
    }

    private static void handleRentVehicle(Scanner scanner, Admin admin) {
        admin.displayAvailableVehicles();
        String vehicleId = getUserInputString(scanner, "Enter vehicle ID to rent: ");

        Vehicle vehicle = admin.getVehicleById(vehicleId);
        if (vehicle == null || !vehicle.isAvailable()) {
            System.out.println("Vehicle not found or not available.");
            return;
        }

        String customerName = getUserInputString(scanner, "Enter your name: ");
        int rentalDays = getUserInputInt(scanner, "Enter number of rental days: ");

        Customer customer = new Customer(customerName);
        ((Rentable) vehicle).rent(customer, rentalDays);
        System.out.println("Rental cost: $" + vehicle.calculateRentalCost(rentalDays));
    }

    private static void handleReturnVehicle(Scanner scanner, Admin admin) {
        String vehicleId = getUserInputString(scanner, "Enter vehicle ID to return: ");

        Vehicle vehicle = admin.getVehicleById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        ((Rentable) vehicle).returnVehicle();
        System.out.println("Vehicle returned successfully.");
    }

    private static void handleAdminTask(Scanner scanner, Admin admin, Runnable task) {
        String password = getUserInputString(scanner, "Enter admin password: ");
        if (!admin.verifyPassword(password)) {
            System.out.println("Incorrect password. Access denied.");
            return;
        }
        task.run();
    }

    private static void addVehicle(Scanner scanner, Admin admin) {
        System.out.println("Select vehicle type to add:");
        for (VehicleType type : VehicleType.values()) {
            System.out.printf("%d. %s%n", type.ordinal() + 1, type);
        }

        int vehicleChoice = getUserInputInt(scanner, "Please enter your choice: ");
        if (vehicleChoice < 1 || vehicleChoice > VehicleType.values().length) {
            System.out.println("Invalid vehicle type.");
            return;
        }

        VehicleType type = VehicleType.values()[vehicleChoice - 1];
        String vehicleId = getUserInputString(scanner, "Enter vehicle ID: ");
        String model = getUserInputString(scanner, "Enter model: ");
        double baseRentalRate = getUserInputDouble(scanner, "Enter base rental rate: ");

        Vehicle vehicle = switch (type) {
            case CAR -> new Car(vehicleId, model, baseRentalRate);
            case MOTORCYCLE -> new Motorcycle(vehicleId, model, baseRentalRate);
            case TRUCK -> new Truck(vehicleId, model, baseRentalRate);
        };

        admin.addVehicle(vehicle);
        System.out.println("Vehicle added successfully.");
    }

    private static boolean shouldContinue(Scanner scanner) {
        String choice = getUserInputString(scanner, "Do you want to continue or exit? (continue/exit): ").toLowerCase();
        return choice.equals("continue");
    }

    private static int getUserInputInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    private static double getUserInputDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        return input;
    }

    private static String getUserInputString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private enum VehicleType {
        CAR, MOTORCYCLE, TRUCK
    }
}
