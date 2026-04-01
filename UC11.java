/**
 * UseCase11ConcurrentBookingSimulation.java
 *
 * Demonstrates thread-safe booking using synchronization
 * to prevent race conditions and double booking.
 */

import java.util.*;

// -------------------- RESERVATION REQUEST --------------------

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// -------------------- THREAD-SAFE QUEUE --------------------

class BookingQueue {

    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
        System.out.println("Request added: " + request.guestName);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Critical Section (synchronized)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- BOOKING PROCESSOR (THREAD) --------------------

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronized fetch
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            processBooking(request);
        }
    }

    private void processBooking(BookingRequest request) {

        boolean success = inventory.allocateRoom(request.roomType);

        if (success) {
            System.out.println("Booking CONFIRMED for " + request.guestName +
                    " (" + request.roomType + ")");
        } else {
            System.out.println("Booking FAILED for " + request.guestName +
                    " (" + request.roomType + ")");
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v11.0");
        System.out.println("=====================================");

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate concurrent requests
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Single")); // should fail
        queue.addRequest(new BookingRequest("David", "Double"));
        queue.addRequest(new BookingRequest("Eve", "Double")); // should fail

        // Create multiple threads
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely under concurrency.");
        System.out.println("Thank you for using Book My Stay.");
    }
}
