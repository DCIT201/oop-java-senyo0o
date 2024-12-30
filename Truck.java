package org.example;

public class Truck extends Vehicle implements Rentable {

    public Truck(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRentalRate() * days;
    }

    @Override
    public void rent(Customer customer, int days) {
        if (!isAvailable()) {
            System.out.println("Truck is not available for rental.");
            return;
        }
        setAvailable(false);
        System.out.printf("Truck rented by %s for %d days.%n", customer.getName(), days);
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Truck returned successfully.");
    }
}