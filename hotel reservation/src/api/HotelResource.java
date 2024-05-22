package api;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import service.CustomerService;
import service.ReservationService;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


public class HotelResource {
    private static final CustomerService customerservice = CustomerService.getInstance();
    private static final ReservationService reservationservice = ReservationService.getInstance();

    public Customer getCustomer(String email){
        return customerservice.getCustomer(email);
    }

    public void createACustomers(String email, String firstname, String lastname){
        customerservice.addCustomer(email, firstname, lastname);
    }
    public IRoom getRoom(String roomId){
        return reservationservice.getARoom(roomId);
    }

    public Reservation BookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationservice.reserveARoom(customer,room,checkInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(Customer customer){
        return reservationservice.getCustomersReservation(customer);
    }
//    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
//        return reservationservice.findRooms(checkInDate, checkOutDate);
//    }
    public Collection<IRoom> AvailableRoom(Date checkInDate, Date checkOutDate){
        return reservationservice.findAvailableRoom(checkInDate, checkOutDate);
    }
    public Collection<IRoom> RecommendRoom(Date checkInDate, Date checkOutDate){
        return reservationservice.RecommendRoom(checkInDate, checkOutDate);
    }
    public Collection<IRoom> Find_a_Room(String PaidRoom){
        return reservationservice.find_a_room(PaidRoom);
    }

}
