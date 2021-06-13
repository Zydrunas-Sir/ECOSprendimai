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
        } else if (!Validation.isValidSymbol(categoryName_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_CATEGORY_NAME);
        } else if ((item == null)) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CHOSEN_CATEGORY);
        } else {
            registerCategory(item);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Categories> categoryNames = CategoriesDAO.selectCategoriesForListView();
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
        categoryComboBox.getItems().addAll(categoryNames);
    }

    public void closeWindow() {
        Stage stage = (Stage) createCategory_button.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void registerCategory(Categories item) {
        Categories parentCategory = CategoriesDAO.displayParentCategoryById(item.getId());
        CategoriesDAO.updateCategoryLefts(parentCategory.getlft());
        CategoriesDAO.updateCategoryRights(parentCategory.getlft());
        Categories newCategory = new Categories(categoryName_textField.getText(), parentCategory.getlft() + 1, parentCategory.getlft() + 2);
        CategoriesDAO.insert(newCategory);
        closeWindow();
    }

    void WarnStyle() {
        form_info_label.setText("");
        form_info_label.setStyle("-fx-text-fill: red;");
    }
}
