package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import sample.JPA.JPAUtil;
import sample.JPA.user.User;
import sample.JPA.user.UserDAO;
import sample.JPA.user.UserHolder;
import sample.utils.Constants;
import sample.utils.Validation;

import java.awt.*;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {

    public Button username_button;
    public Label login_info_label;
    public TextField email_textfield;
    public PasswordField password_passwordfield;
    public Hyperlink register_button;
    public Button dashboard_button;
    public CheckBox check_box_remember_me;
    public ImageView imageView;

    final String PREF_NAME = "Email";
    final String PREF_PASSWORD = "Password";
    final String PREF_CHECKBOX = "Check";
    Preferences prefs = Preferences.userNodeForPackage(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String propertyValue = prefs.get(PREF_NAME, "");
        String password = prefs.get(PREF_PASSWORD, "");
        check_box_remember_me.setSelected(prefs.getBoolean(PREF_CHECKBOX, false));
        email_textfield.setText(propertyValue);
        email_textfield.setOnKeyReleased(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        username_button.fire();
                    }
                }
        );
        password_passwordfield.setText(password);
        password_passwordfield.setOnKeyReleased(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        username_button.fire();
                    }
                }
        );
        username_button.setOnKeyReleased(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        username_button.fire();
                    }
                }
        );
    }

    public void login() {

        if (email_textfield.getText().isEmpty()
                || password_passwordfield.getText().isEmpty()) {
            login_info_label.setStyle("-fx-text-fill: red;");
            login_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
            return;
        } else if (!Validation.isValidEmail(email_textfield.getText()) || !Validation.isValidPassword(password_passwordfield.getText())) {

            login_info_label.setStyle("-fx-text-fill: red;");
            login_info_label.setText(Constants.CREDENTIALS_EMAIL_AND_PASSWORD_NOT_CORRECT);
            return;
        }
        //Comparing two objects (email)
        //If email from text field equals email from database
        //Go to dashboard

        //Getting list from UserDAO class with credentials of password and username
        User credentials = UserDAO.searchUserByEmail(email_textfield.getText());

        //checking password is it match
        try {
            assert credentials != null;
            if (checkPass(password_passwordfield.getText(), credentials.getPassword())) {


                if (check_box_remember_me.isSelected()) {
                    prefs.put(PREF_NAME, email_textfield.getText());
                    prefs.put(PREF_PASSWORD, password_passwordfield.getText());
                    prefs.putBoolean(PREF_CHECKBOX, true);
                } else {
                    prefs.put(PREF_NAME, "");
                    prefs.put(PREF_PASSWORD, "");
                    prefs.putBoolean(PREF_CHECKBOX, false);
                }

                User u = new User(credentials.getEmail(), credentials.isAdmin());
                UserHolder holder = UserHolder.getInstance();
                holder.setUser(u);
                goToDashBoard();

            } else if (!checkPass(password_passwordfield.getText(), credentials.getPassword())) {

                login_info_label.setStyle("-fx-text-fill: red;");
                login_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT);
            }
        } catch (NullPointerException npe) {
            login_info_label.setStyle("-fx-text-fill: red;");
            login_info_label.setText(Constants.EMAIL_NOT_EXIST);
        }


    }

    public void windowCloseLoginButton() { //Uzdaro prisijungimo langa
        Stage stage = (Stage) register_button.getScene().getWindow();
        stage.close();
    }

    public void register() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.REGISTER_VIEW_DIRECTORY_PATH));
            Stage registerStage = new Stage();
            Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
            scene.getStylesheets().add(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
            registerStage.setTitle("Registracija");
            registerStage.setScene(scene);
            JPAUtil.setScene(scene);
            registerStage.setResizable(false);
            registerStage.show();
            windowCloseLoginButton();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToDashBoard() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.DASHBOARD_VIEW_DIRECTORY_PATH));
            Stage dashboardStage = new Stage();
            Scene scene = new Scene(root);
            JPAUtil.setScene(scene);
            //scene.getStylesheets().add(getClass().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
            dashboardStage.setTitle("Produktų peržiūros langas");
            dashboardStage.setScene(scene);
            dashboardStage.setMinWidth(1345);

            dashboardStage.show();
            dashboardStage.setOnCloseRequest(e -> DashboardController.closeDashboard());
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