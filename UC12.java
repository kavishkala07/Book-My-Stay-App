/**
 * UseCase12DataPersistenceRecovery.java
 *
 * Demonstrates file-based persistence using serialization
 * to save and restore booking + inventory state.
 */

import java.io.*;
import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println(guestName + " | " + roomType + " | " + roomId);
    }
}

// -------------------- SYSTEM STATE --------------------

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// -------------------- PERSISTENCE SERVICE --------------------

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with clean state.");
        }

        return null;
    }
}

// -------------------- MAIN APPLICATION --------------------

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v12.0");
        System.out.println("=====================================");

        PersistenceService persistenceService = new PersistenceService();

        // Attempt recovery
        SystemState state = persistenceService.load();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        if (state != null) {
            bookingHistory = state.bookingHistory;
            inventory = state.inventory;

            System.out.println("\nRecovered Booking History:");
            for (Reservation r : bookingHistory) {
                r.display();
            }

        } else {
            // Fresh system state
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single", 2);
            inventory.put("Double", 1);
        }

        // Simulate new booking
        System.out.println("\nProcessing new booking...");

        if (inventory.get("Single") > 0) {
            Reservation newRes = new Reservation("Alice", "Single", "S1");
            bookingHistory.add(newRes);

            inventory.put("Single", inventory.get("Single") - 1);

            System.out.println("New booking added:");
            newRes.display();
        } else {
            System.out.println("No rooms available.");
        }

        // Save state before shutdown
        SystemState newState = new SystemState(bookingHistory, inventory);
        persistenceService.save(newState);

        System.out.println("\nSystem shutting down safely...");
        System.out.println("Restart to see recovered state.");
    }
}
