/**
 * UseCase3InventorySetup.java
 *
 * This class demonstrates centralized inventory management
 * using HashMap to maintain room availability.
 *
 * @author YourName
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory management class
class RoomInventory {

    // HashMap to store room type and availability
    private Map<String, Integer> inventory;

    // Constructor - initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type does not exist in inventory.");
        }
    }

    // Method to display full inventory
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main application class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v3.1");
        System.out.println("=====================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nChecking availability for Single Room:");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        // Update availability
        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        System.out.println("\nInventory after update:");
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay.");
    }
}
