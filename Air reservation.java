import java.util.*;

class Passenger {

    String name;
    int age;
    String passportNumber;

    public Passenger(String name,
                     int age,
                     String passportNumber) {

        this.name = name;
        this.age = age;
        this.passportNumber = passportNumber;
    }
}

class Seat {

    int seatNumber;
    String seatClass;
    double price;

    boolean reserved;

    Passenger passenger;

    public Seat(int seatNumber,
                String seatClass,
                double price) {

        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;

        this.reserved = false;
    }
}
 class AirlineReservationSystem {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Seat> seats = new ArrayList<>();

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "1234";

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   SkyJet Airline Reservation System");
        System.out.println("======================================");

        login();

        initializeSeats();

        while (true) {

            try {

                System.out.println("\n===== MENU =====");

                System.out.println("1. Reserve Seat");
                System.out.println("2. Cancel Reservation");
                System.out.println("3. View Seats");
                System.out.println("4. Search Passenger");
                System.out.println("5. Generate Boarding Pass");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        reserveSeat();
                        break;

                    case 2:
                        cancelReservation();
                        break;

                    case 3:
                        viewSeats();
                        break;

                    case 4:
                        searchPassenger();
                        break;

                    case 5:
                        generateBoardingPass();
                        break;

                    case 6:
                        System.out.println(
                                "\nThank you for using SkyJet!");

                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {

                System.out.println(
                        "Please enter valid input.");

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

                System.out.println(
                        "\nInvalid Credentials.");
            }
        }
    }

    static void initializeSeats() {

        seats.add(new Seat(1, "Economy", 5000));
        seats.add(new Seat(2, "Economy", 5000));
        seats.add(new Seat(3, "Economy", 5000));

        seats.add(new Seat(4, "Business", 10000));
        seats.add(new Seat(5, "Business", 10000));

        seats.add(new Seat(6, "First Class", 20000));
    }

    static void reserveSeat() {

        System.out.println("\n===== RESERVE SEAT =====");

        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Passport Number: ");
        String passport = scanner.nextLine();

        Passenger passenger =
                new Passenger(name, age, passport);

        System.out.print("Enter Seat Number: ");
        int seatNo = scanner.nextInt();
        scanner.nextLine();

        Seat selectedSeat = null;

        for (Seat seat : seats) {

            if (seat.seatNumber == seatNo) {

                selectedSeat = seat;
                break;
            }
        }

        if (selectedSeat == null) {

            System.out.println("Seat does not exist.");
            return;
        }

        if (selectedSeat.reserved) {

            System.out.println(
                    "Seat already reserved.");

            return;
        }

        selectedSeat.passenger = passenger;
        selectedSeat.reserved = true;

        System.out.println("\nSeat Reserved Successfully!");

        System.out.println("Ticket Price: Rs."
                + selectedSeat.price);
    }

    static void cancelReservation() {

        System.out.println(
                "\n===== CANCEL RESERVATION =====");

        System.out.print("Enter Seat Number: ");

        int seatNo = scanner.nextInt();
        scanner.nextLine();

        for (Seat seat : seats) {

            if (seat.seatNumber == seatNo) {

                if (!seat.reserved) {

                    System.out.println(
                            "Seat already available.");

                    return;
                }

                seat.reserved = false;
                seat.passenger = null;

                System.out.println(
                        "Reservation Cancelled.");

                return;
            }
        }

        System.out.println("Seat not found.");
    }

    static void viewSeats() {

        System.out.println("\n===== SEAT STATUS =====");

        for (Seat seat : seats) {

            System.out.println("\nSeat Number : "
                    + seat.seatNumber);

            System.out.println("Class       : "
                    + seat.seatClass);

            System.out.println("Price       : Rs."
                    + seat.price);

            if (seat.reserved) {

                System.out.println("Status      : Reserved");

                System.out.println("Passenger   : "
                        + seat.passenger.name);

            } else {

                System.out.println("Status      : Available");
            }

            System.out.println("----------------------------");
        }
    }

    static void searchPassenger() {

        System.out.println(
                "\n===== SEARCH PASSENGER =====");

        System.out.print("Enter Passenger Name: ");

        String name = scanner.nextLine();

        boolean found = false;

        for (Seat seat : seats) {

            if (seat.reserved &&
                    seat.passenger.name.equalsIgnoreCase(name)) {

                System.out.println("\nPassenger Found!");

                System.out.println("Seat Number : "
                        + seat.seatNumber);

                System.out.println("Seat Class  : "
                        + seat.seatClass);

                found = true;
            }
        }

        if (!found) {

            System.out.println("Passenger not found.");
        }
    }

    static void generateBoardingPass() {

        System.out.println(
                "\n===== BOARDING PASS =====");

        System.out.print("Enter Seat Number: ");

        int seatNo = scanner.nextInt();
        scanner.nextLine();

        for (Seat seat : seats) {

            if (seat.seatNumber == seatNo) {

                if (!seat.reserved) {

                    System.out.println(
                            "Seat is not reserved.");

                    return;
                }

                System.out.println(
                        "\n========== BOARDING PASS ==========");

                System.out.println("Passenger Name : "
                        + seat.passenger.name);

                System.out.println("Age            : "
                        + seat.passenger.age);

                System.out.println("Passport No    : "
                        + seat.passenger.passportNumber);

                System.out.println("Seat Number    : "
                        + seat.seatNumber);

                System.out.println("Class          : "
                        + seat.seatClass);

                System.out.println("Ticket Price   : Rs."
                        + seat.price);

                System.out.println(
                        "===================================");

                return;
            }
        }

        System.out.println("Seat not found.");
    }
}