/**
 * UseCase2RoomInitialization.java
 *
 * This class demonstrates basic room modeling using abstraction,
 * inheritance, and static availability representation.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract class representing a generic Room
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    // Constructor
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Number of Beds  : " + numberOfBeds);
        System.out.println("Price per Night : $" + pricePerNight);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}

// Main application class
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v2.1");
        System.out.println("=====================================\n");

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display Single Room Details
        System.out.println("----- Single Room Details -----");
        singleRoom.displayDetails();
        System.out.println("Available Rooms : " + singleRoomAvailability + "\n");

        // Display Double Room Details
        System.out.println("----- Double Room Details -----");
        doubleRoom.displayDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailability + "\n");

        // Display Suite Room Details
        System.out.println("----- Suite Room Details -----");
        suiteRoom.displayDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailability + "\n");

        System.out.println("Thank you for using Book My Stay.");
    }
}
