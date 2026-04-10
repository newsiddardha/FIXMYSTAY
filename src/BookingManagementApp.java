import java.util.*;
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void reduce(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void display() {
        System.out.println("\nRemaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}
class RoomAllocationService {

    private Map<String, Integer> counters = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (!inventory.isAvailable(type)) {
            System.out.println("No rooms available for " + r.getGuestName());
            return;
        }

        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);

        String roomId = type + "-" + count;

        inventory.reduce(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName() + ", Room ID: " + roomId);
    }
}
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Critical section: queue access
            synchronized (bookingQueue) {
                if (!bookingQueue.hasRequests()) break;
                reservation = bookingQueue.getNextRequest();
            }

            // Critical section: inventory update
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class BookingManagementApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Display remaining inventory
        inventory.display();
    }
}