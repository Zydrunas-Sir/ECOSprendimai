package sample.controller;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import sample.JPA.user.User;
import sample.JPA.user.UserDAO;
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController extends Main implements Initializable {

    public Button register_button;
    public Label form_info_label;
    public TextField company_name_textfield;
    public PasswordField password_passwordfield;
    public TextField email_textfield;
    public TextField first_name_textfield;
    public TextField last_name_textfield;
    public TextField password_confirm_passwordfield;


    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onMouseClick();
    }

    //Checks all register fields are correct
    public void register() {

        if (first_name_textfield.getText().isEmpty()
                || last_name_textfield.getText().isEmpty()
                || email_textfield.getText().isEmpty()
                || company_name_textfield.getText().isEmpty()
                || password_passwordfield.getText().isEmpty()
                || password_confirm_passwordfield.getText().isEmpty()
        ) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
            return;
        } else if (!Validation.isValidFirstName(first_name_textfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_FIRST_NAME);
            return;
        } else if (!Validation.isValidLastName(last_name_textfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_LAST_NAME);
            return;
        } else if (!Validation.isValidEmail(email_textfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED_EMAIL);
            return;
        } else if (!Validation.isValidCompanyName(company_name_textfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_COMPANY_NAME);
            return;
        } else if (!Validation.isValidPassword(password_passwordfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PASSWORD);
            return;
        } else if (!Validation.isValidPassword(password_confirm_passwordfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PASSWORD);
            return;
        } else if (!password_passwordfield.getText().equals(password_confirm_passwordfield.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.PASSWORD_IS_NOT_EQUAL);
        }
        //Sending data with email address and getting answer is it exists in boolean
        boolean credentials = UserDAO.compareEmailForValidation(email_textfield.getText());

        //If theres is no same email
        if (!credentials) {
            //Creating new user
            registerToDataBase();
        } else {
            form_info_label.setText("");
            form_info_label.setStyle("-fx-text-fill: red;");
            form_info_label.setText(Constants.EMAIL_EXISTS);
            email_textfield.setStyle("-fx-text-fill: red;");
        }
    }

    public void closeRegister() { //Uzdaro prisijungimo langa
        Stage stage = (Stage) register_button.getScene().getWindow();
        stage.close();
    }

    void WarnStyle() {
        form_info_label.setText("");
        form_info_label.setStyle("-fx-text-fill: red;");
    }

    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH));
            Stage loginStage = new Stage();
            Scene scene = new Scene(root, Constants.LOGIN_WINDOW_WIDTH, Constants.LOGIN_WINDOW_HEIGHT);
            scene.getStylesheets().add(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
            loginStage.setTitle("Prisijungimas");
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.show();
            closeRegister();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void onMouseClick() {
        form_info_label.setStyle("-fx-text-fill: black;");
        // Handle TextField text changes.
        password_passwordfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= 7) {

                form_info_label.setText("Slaptažodyje turi būti :\n" +
                        "✳bent 8 simboliai.\n");
                return;

            } else {
                form_info_label.setText("");
            }
            if (!Validation.isOneUpperCaseExist(newValue)) {
                form_info_label.setText("Slaptažodyje turi būti :\n" +
                        "✳bent viena didžioji radė.\n");
                return;

            } else {
                form_info_label.setText("");
            }
            if (!Validation.isOneDigitAre(newValue)) {
                form_info_label.setText("Slaptažodyje turi būti :\n" +
                        "✳bent vienas skaičius.\n");
                return;

            } else {
                form_info_label.setText("");
            }
            if(!password_confirm_passwordfield.getText().equals(password_passwordfield.getText())) {
                form_info_label.setText("Įvesti slaptažodžiai turi sutapti.");
                return;
            }

        });
    }


    public void registerToDataBase() {

        //Create list of user where puts all text form text field
        User user = new User(
                first_name_textfield.getText(),
                last_name_textfield.getText(),
                email_textfield.getText(),
                company_name_textfield.getText(),


                //encrypting user password then calling function hasPassword
                hashPassword(password_passwordfield.getText())
        );
        //Inserting list to database
        UserDAO.insert(user);
        //back to login screen
        goToLogin();
    }
}
