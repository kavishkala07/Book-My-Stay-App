/**
 * UseCase7AddOnServiceSelection.java
 *
 * This class demonstrates how add-on services can be attached
 * to reservations without modifying core booking logic.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

// -------------------- ADD-ON SERVICE --------------------

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " : $" + cost);
    }
}

// -------------------- SERVICE MANAGER --------------------

class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap
            .computeIfAbsent(reservationId, k -> new ArrayList<>())
            .add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.display();
        }
    }

    // Calculate total service cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v7.0");
        System.out.println("=====================================");

        // Simulated reservation IDs (from Use Case 6)
        String reservation1 = "SINGLEROOM-1";
        String reservation2 = "SUITEROOM-2";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 20.0);
        AddOnService wifi = new AddOnService("WiFi", 10.0);
        AddOnService spa = new AddOnService("Spa Access", 50.0);

        // Guest selects services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, wifi);

        manager.addService(reservation2, spa);

        // Display services
        manager.displayServices(reservation1);
        System.out.println("Total Add-On Cost: $" +
                manager.calculateTotalCost(reservation1));

        manager.displayServices(reservation2);
        System.out.println("Total Add-On Cost: $" +
                manager.calculateTotalCost(reservation2));

        System.out.println("\nCore booking and inventory remain unchanged.");
        System.out.println("Thank you for using Book My Stay.");
    }
}
