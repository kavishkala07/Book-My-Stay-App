/**
 * UseCase8BookingHistoryReport.java
 *
 * This class demonstrates booking history tracking and reporting
 * using List to maintain ordered reservation records.
 *
 * @author YourName
 * @version 8.0
 */

import java.util.*;

// -------------------- RESERVATION MODEL --------------------

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
        System.out.println("Room ID    : " + roomId);
    }
}

// -------------------- BOOKING HISTORY --------------------

class BookingHistory {

    // List to maintain ordered bookings
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored for " + reservation.getGuestName());
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display full history
    public void displayHistory() {
        System.out.println("\n----- Booking History -----");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation reservation : history) {
            reservation.display();
            System.out.println("--------------------------");
        }
    }
}

// -------------------- REPORT SERVICE --------------------

class BookingReportService {

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n----- Booking Summary Report -----");

        if (reservations.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        // Count bookings per room type
        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomTypeCount.put(
                r.getRoomType(),
                roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        // Display summary
        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " bookings");
        }

        System.out.println("Total Bookings : " + reservations.size());
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v8.0");
        System.out.println("=====================================");

        // Initialize components
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("Alice", "Single Room", "SINGLEROOM-1");
        Reservation r2 = new Reservation("Bob", "Double Room", "DOUBLEROOM-2");
        Reservation r3 = new Reservation("Charlie", "Suite Room", "SUITEROOM-3");
        Reservation r4 = new Reservation("David", "Single Room", "SINGLEROOM-4");

        // Store bookings
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);

        // Display history
        history.displayHistory();

        // Generate report
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting completed without modifying booking data.");
        System.out.println("Thank you for using Book My Stay.");
    }
}
