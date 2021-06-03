package sample.JPA.user;

import java.sql.Timestamp;

public class ObservableUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private String registered;
    private String lastLogin;
    private String timeSpentInDate;

    public ObservableUser(int id, String firstName, String lastName, String email, String companyName, String registered, String lastLogin, int timeSpend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.registered = registered;
        this.lastLogin = lastLogin;
        this.timeSpentInDate = secondsToDate(timeSpend);
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

    public String getTimeSpentInDate() {
        return timeSpentInDate;
    }

    public void setTimeSpentInDate(String timeSpentInDate) {
        this.timeSpentInDate = timeSpentInDate;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String secondsToDate(int spentTimeInSeconds) {
        String exportDate = null;
        int hours, minutes, minutesToDisplay, secondsToDisplay;
        minutes = spentTimeInSeconds / 60;
        secondsToDisplay = spentTimeInSeconds % 60;
        hours = minutes / 60;
        minutesToDisplay = minutes % 60;
        exportDate = hours + " val. " + minutesToDisplay + " min. " + secondsToDisplay + " s.";
        return exportDate;
    }
}
