/**
 * UseCase5BookingRequestQueue.java
 *
 * This class demonstrates booking request handling using a Queue
 * to ensure First-Come-First-Served (FIFO) processing.
 *
 * No inventory updates or room allocation are performed here.
 *
 * @author YourName
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// -------------------- RESERVATION MODEL --------------------

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

    public void display() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}

// -------------------- BOOKING QUEUE --------------------

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void displayQueue() {
        System.out.println("\n----- Booking Request Queue -----");

        if (queue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        for (Reservation reservation : queue) {
            reservation.display();
            System.out.println("----------------------------");
        }
    }

    // Peek next request (without removing)
    public Reservation peekNextRequest() {
        return queue.peek();
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v5.0");
        System.out.println("=====================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNextRequest();

        if (next != null) {
            next.display();
        }

        System.out.println("\nAll requests are stored in arrival order (FIFO).");
        System.out.println("No booking or inventory update performed.");

        System.out.println("\nThank you for using Book My Stay.");
    }
}
