/**
 * UseCase9ErrorHandlingValidation.java
 *
 * Demonstrates validation, custom exceptions, and fail-fast design
 * to ensure system reliability and prevent invalid booking states.
 */

import java.util.*;

// -------------------- CUSTOM EXCEPTION --------------------

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryService() {
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 1);
        roomInventory.put("Suite", 1);
    }

    public boolean isRoomTypeValid(String roomType) {
        return roomInventory.containsKey(roomType);
    }

    public int getAvailableRooms(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        if (!isRoomTypeValid(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = roomInventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // Safe update
        roomInventory.put(roomType, available - 1);

        System.out.println("Room allocated successfully for type: " + roomType);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- VALIDATOR --------------------

class BookingValidator {

    public static void validate(String guestName, String roomType, InventoryService inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.isRoomTypeValid(roomType)) {
            throw new InvalidBookingException("Room type does not exist: " + roomType);
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("Room not available for type: " + roomType);
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v9.0");
        System.out.println("=====================================");

        InventoryService inventory = new InventoryService();

        // Test scenarios
        String[][] testBookings = {
            {"Alice", "Single"},     // valid
            {"", "Double"},          // invalid guest name
            {"Bob", "King"},         // invalid room type
            {"Charlie", "Suite"},    // valid
            {"David", "Suite"}       // unavailable
        };

        for (String[] booking : testBookings) {

            String guest = booking[0];
            String room = booking[1];

            System.out.println("\nProcessing booking for: " + guest + ", Room: " + room);

            try {
                // Step 1: Validate input (Fail Fast)
                BookingValidator.validate(guest, room, inventory);

                // Step 2: Allocate room
                inventory.allocateRoom(room);

                System.out.println("Booking confirmed for " + guest);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // Final inventory state
        inventory.displayInventory();

        System.out.println("\nSystem remains stable after handling errors.");
        System.out.println("Thank you for using Book My Stay.");
    }
}
