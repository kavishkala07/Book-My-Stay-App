/**
 * UseCase6RoomAllocationService.java
 *
 * This class demonstrates booking confirmation and safe room allocation
 * using Queue, HashMap, and Set to prevent double-booking.
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- INVENTORY --------------------

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\n----- Current Inventory -----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- BOOKING QUEUE --------------------

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------- BOOKING SERVICE --------------------

class BookingService {

    // Map of roomType -> allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> allAllocatedRoomIds = new HashSet<>();

    // Room ID counter
    private int roomCounter = 1;

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing booking for: " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness (defensive check)
                if (allAllocatedRoomIds.contains(roomId)) {
                    System.out.println("Error: Duplicate Room ID detected!");
                    continue;
                }

                // Add to global set
                allAllocatedRoomIds.add(roomId);

                // Add to room-type mapping
                allocatedRooms
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

                // Decrement inventory (atomic step)
                inventory.decrementAvailability(roomType);

                // Confirm reservation
                System.out.println("Booking Confirmed!");
                System.out.println("Guest Name : " + request.getGuestName());
                System.out.println("Room Type  : " + roomType);
                System.out.println("Room ID    : " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + roomType);
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replaceAll(" ", "").toUpperCase() + "-" + (roomCounter++);
    }

    public void displayAllocations() {
        System.out.println("\n----- Allocated Rooms -----");

        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v6.0");
        System.out.println("=====================================");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process bookings
        bookingService.processBookings(queue, inventory);

        // Show results
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay.");
    }
}
