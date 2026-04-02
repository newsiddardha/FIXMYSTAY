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

    public Map<String, Room> getAllRooms() {
        return inventory;
    }
}
class SearchService {
    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }
    public void displayAvailableRooms() {
        System.out.println("Available Room Options:");
        for (Map.Entry<String, Room> entry : inventory.getAllRooms().entrySet()) {
            Room room = entry.getValue();
            if (room.getAvailableRooms() > 0) {
                System.out.println(entry.getKey() + " -> " + room);
            }
        }
    }
}

public class BookingManagementApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", new Room(1, 250, 1500.0, 5));
        inventory.addRoomType("Double Room", new Room(2, 400, 2500.0, 3));
        inventory.addRoomType("Suite Room", new Room(3, 750, 5000.0, 0)); // No availability
        SearchService searchService = new SearchService(inventory);
        searchService.displayAvailableRooms();
    }
}
