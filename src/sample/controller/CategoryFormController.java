package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.JPA.Categories;
import sample.JPA.CategoriesDAO;
import sample.Main;
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
        if (!categoryName_textField.getText().isEmpty() && !(item==null)){
            if (Validation.isValidSymbol(categoryName_textField.getText())){
                Stage stage = (Stage) createCategory_button.getScene().getWindow();
                Categories parentCategory = CategoriesDAO.displayParentCategoryById(item.getId());
                CategoriesDAO.updateCategoryLefts(parentCategory.getlft());
                CategoriesDAO.updateCategoryRights(parentCategory.getlft());
                Categories newCategory = new Categories(categoryName_textField.getText(), parentCategory.getlft()+1, parentCategory.getlft()+2);
                CategoriesDAO.insert(newCategory);
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Categories> categoryNames = CategoriesDAO.selectCategoriesForListView();
        categoryComboBox.setCellFactory(lv -> new ListCell<Categories>(){
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
}
