package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.JPA.ProductCatalog;
import sample.JPA.user.User;
import sample.JPA.user.ObservableUser;
import sample.Main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import static sample.JPA.user.UserDAO.getAllUsers;

public class UserStatsController extends Main implements Initializable {

    @FXML
    public TableColumn number;
    @FXML
    public TableColumn company;
    @FXML
    public TableColumn name;
    @FXML
    public TableColumn surname;
    @FXML
    public TableColumn email;
    @FXML
    public TableColumn registeredOn;
    @FXML
    public TableColumn lastLogin;
    @FXML
    public TableColumn loginCount;
    @FXML
    public TableView<ObservableUser> userTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserTable();
    }

    private void fillUserTable() {

        loginCount.setSortable(false);
        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        number.setCellValueFactory(new PropertyValueFactory<>("id"));
        company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        company.setMinWidth(50);
        name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        name.setMinWidth(50);
        surname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        surname.setMinWidth(50);
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setMinWidth(50);
        registeredOn.setCellValueFactory(new PropertyValueFactory<>("registered"));
        registeredOn.setMinWidth(50);
        lastLogin.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        lastLogin.setMinWidth(50);
        loginCount.setCellValueFactory(new PropertyValueFactory<>("timeSpentInDate"));
        loginCount.setMinWidth(50);

        List<User> userList = getAllUsers();
        ObservableList<ObservableUser> filteredUsers = FXCollections.observableArrayList();

        for (User user : userList) {
            String registeredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getUserCreationDate());
            String lastLoginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getLastLogin());
            filteredUsers.add(new ObservableUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCompanyName(), registeredDate, lastLoginDate, user.getTimeSpend()));
        }

        userTableView.setItems(filteredUsers);

    }

}
