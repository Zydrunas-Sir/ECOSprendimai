package sample.JPA.user;


import org.apache.poi.ss.formula.functions.T;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin", columnDefinition="tinyint(1) default 0")
    private boolean isAdmin;
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp userCreationDate;
    @Column(name = "last_login", columnDefinition = "TIMESTAMP")
    private Timestamp lastLogin;
    @Column(name = "time_spend", nullable = false)
    private int timeSpend;

    public User(String firstName, String lastName, String email, String companyName, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String email, String companyName, String password, boolean isAdmin, Timestamp userCreationDate, Timestamp lastLogin, int timeSpend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.userCreationDate = userCreationDate;
        this.lastLogin = lastLogin;
        this.timeSpend = timeSpend;
    }

    public User(String email, boolean isAdmin) {
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Timestamp getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(Timestamp loggedDate) {
        this.userCreationDate = loggedDate;
    }

    public int getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(int timeSpend) {
        this.timeSpend = timeSpend;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin() {
        this.lastLogin =  (new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", loggedDate=" + userCreationDate +
                ", timeSpend=" + timeSpend +
                '}';
    }
}
