package manu;

import api.HotelResource;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;


import java.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class MainManu {

    public static final HotelResource hotelResource = new HotelResource();
    public static int exitApp = 5;

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int selection = 0;
    printMainMenu();

    while (selection != exitApp) {
        if (scanner.hasNextInt()) {
            selection = scanner.nextInt();

            switch (selection) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount(true);
                    break;
                case 4:
                    AdminManu.main(null);
                case 5:
                    scanner.close();
                    System.exit(0);
                case 6:
                    Find_a_Room();
                    scanner.nextLine();
                    break;
                default:
                    printMainMenu();
                    scanner.nextLine();
                    break;
            }
        } else {
            System.out.println("Invalid input. Please enter a valid menu option.");
            scanner.nextLine();
        }
    }
}
    public static void findAndReserveRoom() {
        Date alternateCheckInDate;
        Date alternateCheckOutDate;

        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/02/2022:");
        String checkInDateString = scanner.nextLine();
        Date checkInDate = checkValid(checkInDateString, scanner, "CheckIn");

        System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/02/2022:");
        String checkOutDateString = scanner.nextLine();
        Date checkOutDate = checkValid(checkOutDateString, scanner, "CheckOut");

        while (checkInDate != null && checkOutDate != null && checkOutDate.before(checkInDate)) {
            System.out.println("CheckOut Date must be after CheckIn Date. Please enter valid dates.");

            // Re-enter CheckOut Date
            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/02/2022:");
            checkInDateString = scanner.nextLine();
            checkInDate = checkValid(checkInDateString, scanner, "CheckIn");

            System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/02/2022:");
            checkOutDateString = scanner.nextLine();
            checkOutDate = checkValid(checkOutDateString, scanner, "CheckOut");
        }

        Collection<IRoom> availableRooms = hotelResource.AvailableRoom(checkInDate, checkOutDate);
        int recommend_day = 0;
        if (availableRooms.isEmpty()) {
            System.out.println("How many days out should the room recommendation search?");
            while (true) {
                String recommendDayInput = scanner.nextLine();

                if (recommendDayInput.isEmpty()) {
                    System.out.println("Invalid input, type in 0 to return to the main menu");
                    continue;
                }

                try {
                    recommend_day = Integer.parseInt(recommendDayInput);
                    if (recommend_day == 0) {
                        main(null);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, type in 0 to return to the main menu");
                }
            }



            Calendar calendarCheckIn = Calendar.getInstance();
            calendarCheckIn.setTime(checkInDate);
            calendarCheckIn.add(Calendar.DAY_OF_MONTH, recommend_day);
            alternateCheckInDate = calendarCheckIn.getTime();

            Calendar calendarCheckOut = Calendar.getInstance();
            calendarCheckOut.setTime(checkOutDate);
            calendarCheckOut.add(Calendar.DAY_OF_MONTH, recommend_day);
            alternateCheckOutDate = calendarCheckOut.getTime();

            availableRooms = hotelResource.AvailableRoom(alternateCheckInDate, alternateCheckOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms found");
                main(null);
            } else {
                System.out.println("Recommend rooms for " + alternateCheckInDate + " - " + alternateCheckOutDate);
                checkInDate = alternateCheckInDate;
                checkOutDate = alternateCheckOutDate;
                System.out.println(availableRooms);
            }
        } else {
            System.out.println(availableRooms);
        }

        String bookRoom;
        do {
            System.out.println("Would you like to book a room? y/n");
            bookRoom = scanner.nextLine().toLowerCase();
            if ("y".equals(bookRoom)) {
                String existAccount;
                do {
                    System.out.println("Do you have an account with us? y/n");
                    existAccount = scanner.nextLine().toLowerCase();
                    if ("y".equals(existAccount)) {
                        System.out.println("Enter your email: eg. name@domain.com");
                        final String customerEmail = scanner.nextLine();
                        if (hotelResource.getCustomer(customerEmail) == null) {
                            System.out.println("Customer not found.\nYou may need to create a new account.");
                            main(null);
                        } else {
                            reserveRoomForExistingCustomer(customerEmail, availableRooms, checkInDate, checkOutDate, scanner);
                        }
                    } else if ("n".equals(existAccount)) {
                        String newEmail;
                        System.out.println("Please create an account");
                        newEmail = createAccount(false);
                        reserveRoomForExistingCustomer(newEmail, availableRooms, checkInDate, checkOutDate, scanner);
                    } else {
                        System.out.println("Please enter Y(Yes) or N(No) or type in 'back' to return to Manu");
                    }
                } while (!("n".equals(existAccount) || "y".equals(existAccount) || "back".equals(existAccount)));
            } else if ("n".equals(bookRoom) || "back".equals(bookRoom)) {
                main(null);
            } else {
                System.out.println("Invalid input. Please enter Y(Yes) or N(No) or type in 'back' to return to Manu");
            }
        } while (!("n".equals(bookRoom) || "y".equals(bookRoom) || "back".equals(bookRoom)));
    }



    private static void reserveRoomForExistingCustomer(String customerEmail, Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate, Scanner scanner) {
        Customer customer = hotelResource.getCustomer(customerEmail);
        System.out.println("What room number would you like to reserve?");
        final String roomNumber = scanner.next();

        if (availableRooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
            final IRoom room = hotelResource.getRoom(roomNumber);
            Reservation reservation = hotelResource.BookARoom(customer, room, checkInDate, checkOutDate);
            System.out.println("Successfully reserved");
            System.out.println(reservation);
            main(null);
        }
    }



    public static Date checkValid(String dateString, Scanner scanner, String dateType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);

        while (true) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in MM/dd/yyyy format. Example: 02/02/2022");
                System.out.println("Enter " + dateType + " Date mm/dd/yyyy example 02/05/2022:");
                dateString = scanner.nextLine();

                if ("back".equals(dateString)) {
                    main(null);
                }
            }
        }
    }
    public static void seeMyReservations(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email: eg. name@domain.com");
        String email = scanner.next();
        Customer customer = hotelResource.getCustomer(email);
        Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(customer);

        if (customerReservations.isEmpty()) {
            System.out.println("No reservations for this customer.");
            printMainMenu();
        } else {
            System.out.println(customerReservations);
        }
    }

    public static String createAccount(boolean backToManu){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Customer First Name:");
            String firstName = scanner.next();
            System.out.println("Enter Customer Last Name:");
            String lastName = scanner.next();
            System.out.println("Enter your email: eg. name@domain.com");
            String email = scanner.next();
        try {
            hotelResource.createACustomers(email, firstName, lastName);
            System.out.println("Successfully create");
            if (backToManu){
                main(null);
            }
            else{
                return email;
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }
    }


    public static void Find_a_Room() {
        Scanner scanner = new Scanner(System.in);
        Collection<IRoom> rooms = new ArrayList<>();

        while (true) {
            try {
                System.out.println("Search for paid rooms or free rooms: eg. paid rooms, free rooms");
                String paidOrFree = scanner.nextLine();
                if (paidOrFree.equals("back")){
                    main(null);
                }
                if (!(paidOrFree.equals("paid rooms") || paidOrFree.equals("free rooms"))) {
                    throw new IllegalArgumentException("Invalid choice");
                }


                if (paidOrFree.equals("paid rooms")){
                    rooms = hotelResource.Find_a_Room("paid rooms");
                    System.out.println(rooms);
                }
                else{
                    rooms = hotelResource.Find_a_Room("free rooms");
                    System.out.println(rooms);
                }

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please enter a valid choice or type in back to return to main manu.");

            }
        }
    }











    public static void printMainMenu()
    {
        System.out.print("\nWelcome to the Hotel Reservation Application\n" +
                "--------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "6. Find_a_room\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");

    }
}
