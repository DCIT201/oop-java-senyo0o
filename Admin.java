package org.example;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private final List<Vehicle> vehicleFleet;
    private final String password;

    public Admin() {
        this.vehicleFleet = new ArrayList<>();
        this.password = "senYo123";
        prepopulateFleet();
    }

    private void prepopulateFleet() {
        addVehicle(new Car("CAR001", "Hyundai Creta", 20.0));
        addVehicle(new Car("CAR002", "Honda Civic", 15.0));
        addVehicle(new Car("CAR003", "Audi A5", 30.0));
        addVehicle(new Car("CAR004", "Lamborghini Urus", 720.0));
        addVehicle(new Car("CAR005", "Tesla Model S", 666.0));

        addVehicle(new Motorcycle("MOTO001", "Indian Scout", 990.0));
        addVehicle(new Motorcycle("MOTO002", "Yamaha YZF-R1", 695.0));
        addVehicle(new Motorcycle("MOTO003", "Honda CBR500R", 72.0));
        addVehicle(new Motorcycle("MOTO004", "Big Bear Choppers", 36.0));
        addVehicle(new Motorcycle("MOTO005", "Zero SR/F7", 93.0));

        addVehicle(new Truck("TRUCK001", "Ram 3500", 76.0));
        addVehicle(new Truck("TRUCK002", "Kenworth T680", 85.0));
        addVehicle(new Truck("TRUCK003", "Isuzu NPR", 57.0));
        addVehicle(new Truck("TRUCK004", "Mack Granite", 20.0));
        addVehicle(new Truck("TRUCK005", "Ford F-150", 777.0));
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleFleet.add(vehicle);
        System.out.printf("%s added to the fleet.%n", vehicle.getClass().getSimpleName());
    }

    public static void displayFleet(Admin admin) {  // Changed to static and added admin parameter
        if (admin.vehicleFleet.isEmpty()) {
            System.out.println("No vehicles in the fleet.");
            return;
        }

        System.out.println("Vehicle Fleet:");
        admin.vehicleFleet.forEach(vehicle -> System.out.printf("%s - ID: %s, Model: %s%n",
                vehicle.getClass().getSimpleName(), vehicle.getVehicleId(), vehicle.getModel()));
    }

    public void displayAvailableVehicles() {
        List<Vehicle> availableVehicles = vehicleFleet.stream()
                .filter(Vehicle::isAvailable)
                .toList();

        if (availableVehicles.isEmpty()) {
            System.out.println("No available vehicles.");
            return;
        }

        System.out.println("Available Vehicles:");
        availableVehicles.forEach(vehicle -> System.out.printf("%s - ID: %s, Model: %s, Rate: $%.2f per day%n",
                vehicle.getClass().getSimpleName(), vehicle.getVehicleId(), vehicle.getModel(), vehicle.getBaseRentalRate()));
    }

    public Vehicle getVehicleById(String vehicleId) {
        return vehicleFleet.stream()
                .filter(vehicle -> vehicle.getVehicleId().equals(vehicleId))
                .findFirst()
                .orElse(null);
    }

    public static void displayFleet() {
    }
}