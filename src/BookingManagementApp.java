import java.util.*;
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
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void reduceRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return;
        }

        String roomId = generateRoomId(roomType);

        // store unique ID
        allocatedRoomIds.add(roomId);

        // map room type -> ids
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        // update inventory
        inventory.reduceRoom(roomType);

        // confirmation output
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }
    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return roomType + "-" + count;
    }
}

public class BookingManagementApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Single");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        service.allocateRoom(r1, inventory);
        service.allocateRoom(r2, inventory);
        service.allocateRoom(r3, inventory);
    }
}