package service;
import model.customer.Customer;
import model.room.IRoom;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import model.reservation.Reservation;
import java.util.ArrayList;
import java.util.List;
public class ReservationService {
    private static final ReservationService single_instance = new ReservationService();
    private static final Map<String,IRoom> rooms = new HashMap<>();
    private static final ArrayList<Reservation> reservations = new ArrayList<>();

    private ReservationService(){};
    public static ReservationService getInstance() {
        return single_instance;
    }

    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(),room);
    }
    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }


    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }
    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate){
        List<IRoom> roomsFounded = new ArrayList<>();
        try{
            for (Reservation reservation: reservations){
                if (reservation.getCheckInDate() == checkInDate && reservation.getCheckOutDate() == checkOutDate){
                    roomsFounded.add(reservation.getRoom());
                }
            }
        } catch (IllegalArgumentException ex){
            if (reservations.isEmpty()) return null;
        }
        return roomsFounded;
    }
    public Collection<IRoom> findAvailableRoom(final Date checkInDate, final Date checkOutDate){
        final Collection<IRoom> notAvailableRooms = new ArrayList<>();
        for (Reservation reservation: reservations){
            if(checkValidReservation(reservation, checkInDate, checkOutDate)){
                notAvailableRooms.add(reservation.getRoom());
            }
        }
        Collection<IRoom> availableRooms = new ArrayList<>(rooms.values());
        availableRooms.removeAll(notAvailableRooms);


        return availableRooms;
    }
    public Collection<IRoom> RecommendRoom(final Date checkInDate, final Date checkOutDate){
        Collection<IRoom> availableRooms = new ArrayList<>(rooms.values());
        final Collection<IRoom> notAvailableRooms = new ArrayList<>();
        if(availableRooms.isEmpty()){


            for(Reservation reservation: reservations){
                if(checkValidReservation(reservation, checkInDate, checkOutDate)){
                    notAvailableRooms.add(reservation.getRoom());
                }
            }

            availableRooms.removeAll(notAvailableRooms);
        }
        return availableRooms;
    }
    private boolean checkValidReservation(final Reservation reservation, final Date checkInDate,
                                        final Date checkOutDate){
        return checkInDate.before(reservation.getCheckOutDate())
                && checkOutDate.after(reservation.getCheckInDate());
            //return True if not valid
    }
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Map<String, Reservation> getReservationByEmail = new HashMap<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getEmail().equals(customer.getEmail())) {
                getReservationByEmail.put(customer.getEmail(), reservation);
            }
        }
        return getReservationByEmail.values();
    }

    public Collection<IRoom> find_a_room(String PaidRoom){
        Collection<IRoom> all_rooms = new ArrayList<>(rooms.values());
        Collection<IRoom> paid_rooms = new ArrayList<>();
        Collection<IRoom> free_rooms;
        for (IRoom room: all_rooms){
            if(!(room.isFree())){
                paid_rooms.add(room);
            }
        }
        all_rooms.removeAll(paid_rooms);
        free_rooms = new ArrayList<>(all_rooms);
        if (PaidRoom.equals("paid rooms")){
            return paid_rooms;
        }
        else{
            return free_rooms;
        }



    }



    public void printAllReservation(){
        System.out.println(reservations);
    }




}
