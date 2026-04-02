import java.util.HashMap;
import java.util.Map;

class Room {
    private int beds;
    private int sizeSqft;
    private double pricePerNight;
    private int availableRooms;

    public Room(int beds, int sizeSqft, double pricePerNight, int availableRooms) {
        this.beds = beds;
        this.sizeSqft = sizeSqft;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    public int getBeds() {
        return beds;
    }

    public int getSizeSqft() {
        return sizeSqft;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    @Override
    public String toString() {
        return "Beds: " + beds +
                ", Size: " + sizeSqft + " sqft" +
                ", Price per night: " + pricePerNight +
                ", Available Rooms: " + availableRooms;
    }
}
class RoomInventory {
    private Map<String, Room> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, Room room) {
        inventory.put(roomType, room);
    }

    public Room getRoomDetails(String roomType) {
        return inventory.get(roomType);
    }

    public void updateAvailability(String roomType, int newCount) {
        Room room = inventory.get(roomType);
        if (room != null) {
            room.setAvailableRooms(newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    public void displayInventory() {
        System.out.println("Hotel Room Inventory Status:");
        for (Map.Entry<String, Room> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BookingManagementApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", new Room(1, 250, 1500.0, 5));
        inventory.addRoomType("Double Room", new Room(2, 400, 2500.0, 3));
        inventory.addRoomType("Suite Room", new Room(3, 750, 5000.0, 2));
        inventory.displayInventory();
        System.out.println("\nDetails of Double Room:");
        System.out.println(inventory.getRoomDetails("Double Room"));
        inventory.updateAvailability("Double Room", 2);
        System.out.println("\nAfter update:");
        inventory.displayInventory();
    }
}
