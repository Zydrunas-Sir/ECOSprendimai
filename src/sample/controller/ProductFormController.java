package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.JPA.Categories;
import sample.JPA.CategoriesDAO;
import sample.JPA.ProductCatalog;
import sample.JPA.ProductCatalogDAO;
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductFormController extends Main implements Initializable {

    public Button create_product_button;
    public TextField catalog_no_textField;
    public TextField symbol_textField;
    public TextField price_textField;
    public TextField stock_textField;
    public Label form_info_label;
    public ComboBox<Categories> categoryComboBox;


    public void createProduct(ActionEvent actionEvent) {
        Categories item = categoryComboBox.getSelectionModel().getSelectedItem();
        if (catalog_no_textField.getText().isEmpty() && symbol_textField.getText().isEmpty() &&
                price_textField.getText().isEmpty() && stock_textField.getText().isEmpty() && (item == null)) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_FILLED);
        } else if (!Validation.isValidCatalogNo(catalog_no_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_CATALOG_NUMBER);
        } else if (!Validation.isValidSymbol(symbol_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_SYMBOL);
        } else if (!Validation.isValidPrice(price_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_PRICE);
        } else if (!Validation.isValidStock(stock_textField.getText())) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_STOCK);
        } else if ((item == null)) {
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CHOSEN_CATEGORY);
        } else {
            registerProduct(item);
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
        Stage stage = (Stage) create_product_button.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    void WarnStyle() {
        form_info_label.setText("");
        form_info_label.setStyle("-fx-text-fill: red;");
    }

    public void registerProduct(Categories item) {
        ProductCatalog product = new ProductCatalog(catalog_no_textField.getText(), symbol_textField.getText(),
                price_textField.getText(), Integer.parseInt(stock_textField.getText()),
                item.getId(), null);
        ProductCatalogDAO.insert(product);
        closeWindow();
    }
}
