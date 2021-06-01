package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.JPA.ProductCatalog;
import sample.JPA.User;
import sample.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static sample.JPA.UserDAO.getAllUsers;

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
    public TableColumn registered;
    @FXML
    public TableColumn loginCount;
    @FXML
    public TableView<User> userTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserTable();
    }

    private void fillUserTable() {

        number.setCellValueFactory(new PropertyValueFactory<>("id"));
        company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        role.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
//        registered.setCellValueFactory(new PropertyValueFactory<>(""));
        loginCount.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        List<User> userList = getAllUsers();
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();
        for (User user : userList) {
            user.setTimeSpend(user.getTimeSpend() / 60);
            filteredUsers.add(user);
        }
        userTableView.setItems(filteredUsers);

    }

}
