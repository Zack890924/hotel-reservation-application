package manu;
import api.AdminResource;

import model.room.FreeRoom;
import model.room.IRoom;
import model.room.roomType;
import model.room.Room;



import java.util.Scanner;


public class AdminManu {
    public static final AdminResource adminResource = new AdminResource();
    public static int BackToMainMenu = 5;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int selection = 0;
        printAdminMenu();
        while (selection != BackToMainMenu) {
            if (scanner.hasNextInt()){
                selection = scanner.nextInt();

                switch (selection) {
                    case 1:
                        System.out.println(adminResource.getAllCustomers());
                        break;
                    case 2:
                        System.out.println(adminResource.getAllRooms());
                        break;
                    case 3:
                        adminResource.displayAllReservations();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:

                        MainManu.main(null);
                        break;
                    default:
                        printAdminMenu();
                        break;
                }
            }
            else {
                System.out.println("Invalid input. Please enter a valid menu option.");
                scanner.nextLine();
            }
            }

        scanner.close();
    }
    public static void addARoom(){
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Room Number");
        String roomNumber = scanner.nextLine();
        System.out.println("Enter Price Per Night");
        Double roomPrice = null;
        while (roomPrice == null) {
            if (scanner.hasNextDouble()) {
                roomPrice = scanner.nextDouble();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a valid number for Price Per Night.");
                scanner.nextLine();  // 清除缓冲区
            }
        }
        System.out.println("Enter Room Type: 1 for single bed, 2 for double bed");
        roomType Type;



        int roomTypeChoice = -1;
        while (roomTypeChoice != 1 && roomTypeChoice != 2) {
            if (scanner.hasNextInt()) {
                roomTypeChoice = scanner.nextInt();
                if (roomTypeChoice != 1 && roomTypeChoice != 2) {
                    System.out.println("Invalid input. Please enter 1 for single bed or 2 for double bed.");
                    scanner.nextLine();
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for Room Type.");
                scanner.nextLine();
            }
        }

        if (roomTypeChoice == 1) {
            Type = roomType.SINGLE;
        } else  {
            Type = roomType.DOUBLE;
        }
        if (roomPrice == 0){
            IRoom room = new FreeRoom(roomNumber, 0.0 , Type);
            adminResource.addRoom(room);
        }else{
            IRoom room = new Room(roomNumber, roomPrice, Type);
            adminResource.addRoom(room);
        }


        System.out.println("Room added successfully!");







        System.out.println("Would you like to add another room?");
        String anotherRoomRequest;
        do {
            anotherRoomRequest = scanner.nextLine().toLowerCase();

            if ("y".equals(anotherRoomRequest)) {
                addARoom();
                System.out.println("Would you like to add another room?");
            } else if ("n".equals(anotherRoomRequest)) {
                main(null);
            } else{
                System.out.println("Please enter Y(Yes) or N(No)");
            }

        } while (!"n".equals(anotherRoomRequest));
        scanner.close();
    }


    public static void printAdminMenu()
    {
        System.out.print("\nAdmin Manu\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Manu\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

}


