package model.customer;

import java.util.regex.Pattern;

public class Customer {

    private static final String emailRegex = "^[a-zA-Z0-9_]+@.+(\\.com)$"
            ;
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName  = lastName;
        validation(email);
        this.email = email;

    }
    private void validation(final String email){
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid Email");
        }
    }
    public String getFirstName() { return this.firstName;}
    public String getLastName() { return this.lastName;}
    public String getEmail() { return this.email;}

    public String toString(){
        return "First Name: " + this.firstName
                + " Last Name: " + this.lastName
                + " Email: " + this.email;
    }


}
