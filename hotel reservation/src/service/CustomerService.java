package service;
import model.customer.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
public class CustomerService {
    private static final CustomerService single_instance = new CustomerService();
    private static final Map<String, Customer> customers = new HashMap<>();
    private CustomerService(){};
    public static CustomerService getInstance() {
        return single_instance;
    }
    public void addCustomer(final String email, final String firstName, final String lastName) {
        customers.put(email, new Customer(firstName, lastName, email));
    }
    public Customer getCustomer(final String customerEmail) {
        return customers.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }
}
