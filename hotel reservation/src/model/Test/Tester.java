package model.Test;
import model.customer.Customer;

public class Tester {
    public static void main(String[] args){
        Customer customer = new Customer("first","second", "aaaa@domain.com");
        System.out.println(customer);

    }
}
