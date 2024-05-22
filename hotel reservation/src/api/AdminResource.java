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
public class AdminResource {
    private static final CustomerService customerservice = CustomerService.getInstance();
    private static final ReservationService reservationservice = ReservationService.getInstance();

    public Customer getCustomers(String email){
        return customerservice.getCustomer(email);
    }
    public void addRoom(IRoom room){
        reservationservice.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationservice.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerservice.getAllCustomers();
    }
    public void displayAllReservations() {
        reservationservice.printAllReservation();
    }
}
