/**
 * UseCase10BookingCancellation.java
 *
 * Demonstrates booking cancellation with rollback using Stack (LIFO),
 * ensuring inventory consistency and safe state reversal.
 */

import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        this.isActive = false;
    }

    public void display() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("Status         : " + (isActive ? "Active" : "Cancelled"));
    }
}

// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- CANCELLATION SERVICE --------------------

class CancellationService {

    // Track released room IDs using Stack (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  Map<String, Reservation> reservationMap,
                                  InventoryService inventory) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validation
        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation res = reservationMap.get(reservationId);

        if (!res.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Step 1: Push room ID to rollback stack
        rollbackStack.push(res.getRoomId());

        // Step 2: Restore inventory
        inventory.incrementRoom(res.getRoomType());

        // Step 3: Mark reservation cancelled
        res.cancel();

        System.out.println("Cancellation successful for " + reservationId);
        System.out.println("Released Room ID: " + res.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v10.0");
        System.out.println("=====================================");

        // Simulated confirmed reservations
        Map<String, Reservation> reservationMap = new HashMap<>();

        reservationMap.put("R1", new Reservation("R1", "Single", "S1"));
        reservationMap.put("R2", new Reservation("R2", "Double", "D1"));
        reservationMap.put("R3", new Reservation("R3", "Suite", "SU1"));

        InventoryService inventory = new InventoryService();
        CancellationService cancellationService = new CancellationService();

        // Perform cancellations
        cancellationService.cancelReservation("R2", reservationMap, inventory); // valid
        cancellationService.cancelReservation("R2", reservationMap, inventory); // already cancelled
        cancellationService.cancelReservation("R5", reservationMap, inventory); // invalid

        // Display reservation states
        System.out.println("\n----- Reservation Status -----");
        for (Reservation res : reservationMap.values()) {
            res.display();
            System.out.println("----------------------------");
        }

        // Display inventory
        inventory.displayInventory();

        // Display rollback stack
        cancellationService.displayRollbackStack();

        System.out.println("\nSystem state restored successfully after cancellations.");
        System.out.println("Thank you for using Book My Stay.");
    }
}
