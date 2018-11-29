package ua.nure.kn.gromak.usermanagement;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable{
    private static final long serialVersionUID = 4188062179191390064L;
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User(Long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName () {
        return new StringBuilder(getLastName())
                .append(", ")
                .append(getFirstName())
                .toString();
    }

    public int getAge(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


        calendar.setTime(getDateOfBirth());
        int yearOfBirth = calendar.get(Calendar.YEAR);
        int monthOfBirth = calendar.get(Calendar.MONTH);
        int dayOfBirth = calendar.get(Calendar.DAY_OF_MONTH);


        int currentAge = currentYear-yearOfBirth;
        if(currentMonth < monthOfBirth | (currentMonth == monthOfBirth & currentDay < dayOfBirth)) {
            currentAge -= 1;
        }
        return currentAge;
    }

}
