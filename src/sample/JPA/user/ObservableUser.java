package sample.JPA.user;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import java.sql.Timestamp;

public class ObservableUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private String registered;
    private String lastLogin;
    private CheckBox isBlockedCheck;
    private Boolean isBlocked;
    private String timeSpentInDate;

    public ObservableUser(int id, String firstName, String lastName, String email, String companyName, String registered, String lastLogin, Boolean isBlocked, int timeSpend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.registered = registered;
        this.lastLogin = lastLogin;
        this.isBlocked = isBlocked;
        setIsBlockedCheck(isBlocked);
        this.isBlockedCheck = getIsBlockedCheck();
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

    public void setBlocked(Boolean blocked) {
        this.isBlocked = blocked;
    }

    public CheckBox getIsBlockedCheck() {
        return this.isBlockedCheck;
    }

    public void setIsBlockedCheck(Boolean isBlocked) {
        this.isBlockedCheck = new CheckBox();
        if (this.isBlockedCheck.isSelected()) {
            setBlocked(false);
        } else {
            setBlocked(true);
        }
        this.isBlockedCheck.selectedProperty().addListener((ObservableBooleanValue -> {
            System.out.println("CHECKBOX HAS BEEN SELECTED");

            UserDAO.updateBlockedStatus(this.isBlockedCheck.isSelected(), getId());
        }));
        this.isBlockedCheck.setSelected(isBlocked);
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
