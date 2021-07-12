package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.JPA.Categories;
import sample.JPA.CategoriesDAO;
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryFormController extends Main implements Initializable {
    public Label form_info_label;
    public Button createCategory_button;
    public TextField categoryName_textField;
    public ComboBox<Categories> categoryComboBox;


    public void createCategory(ActionEvent actionEvent) {
        Categories item = categoryComboBox.getSelectionModel().getSelectedItem();
        if (categoryName_textField.getText().isEmpty() && (item == null)) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
        } else if (!Validation.isValidCategoryName(categoryName_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_CATEGORY_NAME);
        } else if ((item == null)) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CHOSEN_CATEGORY);
        }else if (item.getCategory_parameter_id() == 0){
            WarnStyle();
            form_info_label.setText("Pasirinkta tėvinė kategorija neturi parametrų.");
        } else {
            registerCategory(item);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Categories> categoryNames = CategoriesDAO.selectCategoriesForComboBox();
        List<Categories> categoriesList = new ArrayList<>();
        for (Categories category : categoryNames){
            if( !category.getName().equals("   Visos kategorijos")){
                categoriesList.add(category);
            }
        }
        categoryComboBox.setCellFactory(lv -> new ListCell<Categories>() {
            public void updateItem(Categories item, boolean empty) {

                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                        setText(item.getName());
                }
            }
        });
        categoryComboBox.getItems().addAll(categoriesList);
    }

    public void closeWindow() {
        Stage stage = (Stage) createCategory_button.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void registerCategory(Categories item) {
        CategoriesDAO.updateCategoryLefts(item.getlft());
        CategoriesDAO.updateCategoryRights(item.getlft());
        Categories newCategory = new Categories(categoryName_textField.getText(), item.getlft() + 1, item.getlft() + 2, item.getCategory_parameter_id());
        CategoriesDAO.insert(newCategory);
        closeWindow();
    }

    void WarnStyle() {
        form_info_label.setText("");
        form_info_label.setStyle("-fx-text-fill: red;");
    }
}
