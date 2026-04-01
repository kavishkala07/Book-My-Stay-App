/**
 * UseCase4RoomSearch.java
 *
 * This class demonstrates read-only room search functionality
 * using centralized inventory and domain objects.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.HashMap;
import java.util.Map;

// -------------------- DOMAIN MODEL --------------------

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Beds            : " + numberOfBeds);
        System.out.println("Price per Night : $" + pricePerNight);
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}

// -------------------- INVENTORY --------------------

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose all inventory (read-only usage)
    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// -------------------- SEARCH SERVICE --------------------

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("----- Available Rooms -----");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println();
            }
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v4.0");
        System.out.println("=====================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        Room[] rooms = {
            new SingleRoom(),
            new DoubleRoom(),
            new SuiteRoom()
        };

        // Perform search (read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("Search completed. No changes were made to inventory.");
        System.out.println("\nThank you for using Book My Stay.");
    }
}
