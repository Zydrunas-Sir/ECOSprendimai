package sample.JPA;


import javax.persistence.*;

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
   // @Column(name = "is_admin")
   // private boolean isAdmin;
   // @Basic
   // @Column(name = "date")
   // @Temporal(TemporalType.TIMESTAMP)
   // private Date timestamp;
   // @Column(name = "time_spend")
   // private int timeSpend;
    // private String passwordConfirm;

    public User( String firstName, String lastName, String email, String companyName, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String companyName, String password, String passwordConfirm) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
