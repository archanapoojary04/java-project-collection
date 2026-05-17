import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Guest {

    String name;
    String phone;
    String email;

    public Guest(String name, String phone, String email) {

        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

class Room {

    int roomNumber;
    String roomType;
    double pricePerDay;

    boolean occupied;

    Guest guest;

    LocalDate checkInDate;
    LocalDate checkOutDate;

    public Room(int roomNumber,
                String roomType,
                double pricePerDay) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;

        this.occupied = false;
    }
}

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();

    static Queue<Guest> waitlist = new LinkedList<>();

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "1234";

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   SmartStay Hotel Management System");
        System.out.println("======================================");

        login();

        initializeRooms();

        while (true) {

            try {

                System.out.println("\n===== HOTEL MANAGEMENT SYSTEM =====");

                System.out.println("1. Check-In Guest");
                System.out.println("2. Check-Out Guest");
                System.out.println("3. View Rooms");
                System.out.println("4. Search Guest");
                System.out.println("5. View Waitlist");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        checkIn();
                        break;

                    case 2:
                        checkOut();
                        break;

                    case 3:
                        viewRooms();
                        break;

                    case 4:
                        searchGuest();
                        break;

                    case 5:
                        viewWaitlist();
                        break;

                    case 6:
                        System.out.println("\nThank you for using SmartStay!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {

                System.out.println("Please enter valid numbers only.");
                scanner.nextLine();
            }
        }
    }

    static void login() {

        while (true) {

            System.out.println("\n===== ADMIN LOGIN =====");

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals(ADMIN_USERNAME)
                    && password.equals(ADMIN_PASSWORD)) {

                System.out.println("\nLogin Successful!");
                break;

            } else {

                System.out.println("\nInvalid Credentials.");
            }
        }
    }

    static void initializeRooms() {

        rooms.add(new Room(101, "Standard", 1000));
        rooms.add(new Room(102, "Standard", 1000));

        rooms.add(new Room(103, "Deluxe", 2000));
        rooms.add(new Room(104, "Deluxe", 2000));

        rooms.add(new Room(105, "Suite", 5000));
    }

    static void checkIn() {

        System.out.println("\n===== CHECK-IN =====");

        System.out.print("Enter Guest Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Guest guest = new Guest(name, phone, email);

        System.out.print("Enter Room Number: ");
        int roomNo = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.roomNumber == roomNo) {

                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {

            System.out.println("Room does not exist.");
            return;
        }

        if (selectedRoom.occupied) {

            System.out.println("Room already occupied.");

            waitlist.add(guest);

            System.out.println("Guest added to waitlist.");

            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.print("Enter Check-In Date (DD-MM-YYYY): ");

        selectedRoom.checkInDate =
                LocalDate.parse(scanner.nextLine(), formatter);

        System.out.print("Enter Check-Out Date (DD-MM-YYYY): ");

        selectedRoom.checkOutDate =
                LocalDate.parse(scanner.nextLine(), formatter);

        selectedRoom.guest = guest;

        selectedRoom.occupied = true;

        System.out.println("\nGuest Checked-In Successfully!");
    }

    static void checkOut() {

        System.out.println("\n===== CHECK-OUT =====");

        System.out.print("Enter Room Number: ");

        int roomNo = scanner.nextInt();
        scanner.nextLine();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo) {

                if (!room.occupied) {

                    System.out.println("Room already vacant.");
                    return;
                }

                long days = ChronoUnit.DAYS.between(
                        room.checkInDate,
                        room.checkOutDate);

                double roomCharge =
                        days * room.pricePerDay;

                double gst =
                        roomCharge * 0.18;

                double totalBill =
                        roomCharge + gst;

                System.out.println("\n========== BILL ==========");

                System.out.println("Guest Name : "
                        + room.guest.name);

                System.out.println("Room Number: "
                        + room.roomNumber);

                System.out.println("Room Type  : "
                        + room.roomType);

                System.out.println("Days Stayed: "
                        + days);

                System.out.println("Room Charge: Rs."
                        + roomCharge);

                System.out.println("GST (18%)  : Rs."
                        + gst);

                System.out.println("Total Bill : Rs."
                        + totalBill);

                System.out.println("==========================");

                room.occupied = false;

                room.guest = null;

                room.checkInDate = null;
                room.checkOutDate = null;

                System.out.println("\nCheck-Out Successful!");

                if (!waitlist.isEmpty()) {

                    Guest nextGuest = waitlist.poll();

                    System.out.println(
                            "Next Guest in Waitlist: "
                                    + nextGuest.name);
                }

                return;
            }
        }

        System.out.println("Room not found.");
    }

    static void viewRooms() {

        System.out.println("\n===== ROOM STATUS =====");

        for (Room room : rooms) {

            System.out.println("\nRoom Number : "
                    + room.roomNumber);

            System.out.println("Room Type   : "
                    + room.roomType);

            System.out.println("Price/Day   : Rs."
                    + room.pricePerDay);

            if (room.occupied) {

                System.out.println("Status      : Occupied");

                System.out.println("Guest Name  : "
                        + room.guest.name);

            } else {

                System.out.println("Status      : Vacant");
            }

            System.out.println("----------------------------");
        }
    }

    static void searchGuest() {

        System.out.println("\n===== SEARCH GUEST =====");

        System.out.print("Enter Guest Name: ");

        String guestName = scanner.nextLine();

        boolean found = false;

        for (Room room : rooms) {

            if (room.occupied &&
                    room.guest.name.equalsIgnoreCase(guestName)) {

                System.out.println("\nGuest Found!");

                System.out.println("Room Number: "
                        + room.roomNumber);

                System.out.println("Room Type  : "
                        + room.roomType);

                found = true;
            }
        }

        if (!found) {

            System.out.println("Guest not found.");
        }
    }

    static void viewWaitlist() {

        System.out.println("\n===== WAITLIST =====");

        if (waitlist.isEmpty()) {

            System.out.println("Waitlist is empty.");
            return;
        }

        for (Guest guest : waitlist) {

            System.out.println(
                    guest.name + " - " + guest.phone);
        }
    }
}