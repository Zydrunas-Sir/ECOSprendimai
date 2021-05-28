package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import sample.JPA.User;
import sample.JPA.UserDAO;
import sample.JPA.UserHolder;
import sample.utils.Constants;
import sample.utils.Validation;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginController {

    public Button username_button;
    public Label login_info_label;
    public TextField email_textfield;
    public PasswordField password_passwordfield;
    public Button register_button;
    public Button dashboard_button;


    public void login() {
        if (!email_textfield.getText().isEmpty()   //If text field filled returns false
                && !password_passwordfield.getText().isEmpty()) {


            if (Validation.isValidEmail(email_textfield.getText()) && Validation.isValidPassword(password_passwordfield.getText())) {


                //Comparing two objects (email)
                //If email from text field equals email from database
                //Go to dashboard
                //TODO: Do encryption and decryption
                //TODO: Finish validation compare passwords

                //Getting list from UserDAO class with credentials of password and username
                User credentials = UserDAO.searchUserByEmail(email_textfield.getText());


                //checking password is it match

                try {
                    assert credentials != null;
                    if (checkPass(password_passwordfield.getText(), credentials.getPassword())) {

                        User u = new User(credentials.getEmail(),credentials.isAdmin());
                        UserHolder holder = UserHolder.getInstance();
                        holder.setUser(u);

                        goTodashboard();

                    } else if (!checkPass(password_passwordfield.getText(), credentials.getPassword())) {

                        login_info_label.setStyle("-fx-text-fill: red;");
                        login_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT);
                    }
                } catch (NullPointerException npe) {
                    login_info_label.setStyle("-fx-text-fill: red;");
                    login_info_label.setText(Constants.EMAIL_NOT_EXIST);
                }

            } else {
                login_info_label.setStyle("-fx-text-fill: red;");
                login_info_label.setText(Constants.CREDENTIALS_EMAIL_AND_PASSWORD_NOT_CORRECT);
            }
        } else {
            login_info_label.setStyle("-fx-text-fill: red;");
            login_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
        }
    }

    public void windowCloseLoginButton() { //Uzdaro prisijungimo langa
        Stage stage = (Stage) register_button.getScene().getWindow();
        stage.close();
    }

    public void register() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.REGISTER_VIEW_DIRECTORY_PATH));
            Stage registerStage = new Stage();
            Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
            scene.getStylesheets().add(getClass().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
            registerStage.setTitle("Registracija");
            registerStage.setScene(scene);
            registerStage.setResizable(false);
            registerStage.show();
            windowCloseLoginButton();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goTodashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.DASHBOARD_VIEW_DIRECTORY_PATH));
            Stage dashboardStage = new Stage();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
            dashboardStage.setTitle("Produktų peržiūros langas");
            dashboardStage.setScene(scene);
            dashboardStage.setMinWidth(1345);
            dashboardStage.show();
            windowCloseLoginButton();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * @param hashedPassword is encrypted password
     * @param plainPassword  password witch declared in password field
     */
    public static boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            return true;
        } else {
            return false;
        }


    }

}
