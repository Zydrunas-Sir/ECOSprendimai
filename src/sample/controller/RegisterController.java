package sample.controller;


import javafx.fxml.FXMLLoader;
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
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;


public class RegisterController extends Main {

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

    //Checks all register fields are correct
    public void register() {
        if (first_name_textfield.getText().isEmpty() == false
               &&  last_name_textfield.getText().isEmpty() == false
                && email_textfield.getText().isEmpty()  == false
                 && company_name_textfield.getText().isEmpty()  == false
                  && password_passwordfield.getText().isEmpty()  == false
                   && password_confirm_passwordfield.getText().isEmpty()  == false
        ) {

            if (Validation.isValidFirstName(first_name_textfield.getText())) {

                if (Validation.isValidLastName(last_name_textfield.getText())) {

                    if (Validation.isValidEmail(email_textfield.getText())) {

                        if (Validation.isValidCompanyName(company_name_textfield.getText())) {

                            if (Validation.isValidPassword(password_passwordfield.getText())) {

                                if (Validation.isValidPassword(password_confirm_passwordfield.getText())) {
                                    if (password_passwordfield.getText().equals(password_confirm_passwordfield.getText())) {
                                        //Sending data with email address and getting answer is it exists in boolean
                                        boolean credentials = UserDAO.compareEmailForValidation(email_textfield.getText());


                                        //If theres is no same email
                                        if (credentials == false) {
                                            //Creating new user
                                            registerToDataBase();
                                        }

                                        form_info_label.setText("");
                                        form_info_label.setStyle("-fx-text-fill: red;");
                                        form_info_label.setText(Constants.EMAIL_EXISTS);
                                        email_textfield.setStyle("-fx-text-fill: red;");


                                    } else {

                                        form_info_label.setText("");
                                        form_info_label.setStyle("-fx-text-fill: red;");
                                        form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PASSWORD);
                                    }
                                } else {
                                    form_info_label.setText("");
                                    form_info_label.setStyle("-fx-text-fill: red;");
                                    form_info_label.setText(Constants.PASSWORD_IS_NOT_EQUAL);
                                }
                            } else {
                                form_info_label.setText("");
                                form_info_label.setStyle("-fx-text-fill: red;");
                                form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PASSWORD);

                            }

                        } else {
                            form_info_label.setText("");
                            form_info_label.setStyle("-fx-text-fill: red;");
                            //  company_name_textfield.setStyle("-fx-text-fill: red;");
                            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_COMPANY_NAME);
                        }
                    } else {
                        form_info_label.setText("");
                        form_info_label.setStyle("-fx-text-fill: red;");
                        // email_textfield.setStyle("-fx-text-fill: red;");
                        form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED_EMAIL);
                    }
                } else {
                    form_info_label.setText("");
                    form_info_label.setStyle("-fx-text-fill: red;");
                    // last_name_textfield.setStyle("-fx-text-fill: red;");
                    form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_LAST_NAME);

                }
            } else {
                form_info_label.setText("");
                form_info_label.setStyle("-fx-text-fill: red;");
                //  first_name_textfield.setStyle("-fx-text-fill: red;");
                form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_FIRST_NAME);
            }
        } else {
            form_info_label.setText("");
            form_info_label.setStyle("-fx-text-fill: red;");
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
        }
    }

    public void closeRegister() { //Uzdaro prisijungimo langa
        Stage stage = (Stage) register_button.getScene().getWindow();
        stage.close();
    }

    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH));
            Stage loginStage = new Stage();
            Scene scene = new Scene(root, Constants.LOGIN_WINDOW_WIDTH, Constants.LOGIN_WINDOW_HEIGHT);
            scene.getStylesheets().add(getClass().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
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
