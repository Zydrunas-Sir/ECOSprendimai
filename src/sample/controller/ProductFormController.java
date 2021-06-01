package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.JPA.Categories;
import sample.JPA.CategoriesDAO;
import sample.JPA.ProductCatalog;
import sample.JPA.ProductCatalogDAO;
import sample.Main;
import sample.utils.Validation;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductFormController extends Main implements Initializable {

    public Button create_product_button;
    public Button close;
    public TextField catalog_no_textField;
    public TextField symbol_textField;
    public TextField price_textField;
    public TextField stock_textField;
    public Label form_info_label;
    public ComboBox<String> categoryComboBox;




    public void createProduct(ActionEvent actionEvent) {
        if (!catalog_no_textField.getText().isEmpty() && !symbol_textField.getText().isEmpty() &&
                !price_textField.getText().isEmpty() && !stock_textField.getText().isEmpty()) {
            if (Validation.isValidCatalogNo(catalog_no_textField.getText())) {
                if (Validation.isValidSymbol(symbol_textField.getText())) {
                    if (Validation.isValidPrice(price_textField.getText())) {
                        if (Validation.isValidStock(stock_textField.getText())) {
                            Stage stage = (Stage) create_product_button.getScene().getWindow();
                            ProductCatalog product = new ProductCatalog(Integer.parseInt(catalog_no_textField.getText()), symbol_textField.getText(),
                                    Double.parseDouble(price_textField.getText()), Integer.parseInt(stock_textField.getText()),
                                    categoryComboBox.getSelectionModel().getSelectedIndex() + 2, null);
                            ProductCatalogDAO.insert(product);
                            stage.close();
                        } else {
                            form_info_label.setText("");
                            form_info_label.setStyle("-fx-text-fill: red;");
                            form_info_label.setText("Neveikia stock");
                        }
                    } else {
                        form_info_label.setText("");
                        form_info_label.setStyle("-fx-text-fill: red;");
                        form_info_label.setText("Neveikia price");
                    }
                } else {
                    form_info_label.setText("");
                    form_info_label.setStyle("-fx-text-fill: red;");
                    form_info_label.setText("Neveikia symbol");
                }
            } else {
                form_info_label.setText("");
                form_info_label.setStyle("-fx-text-fill: red;");
                form_info_label.setText("Neveikia catalogNo");
            }


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> categories = CategoriesDAO.selectAllCategoryNames();
        int i = 0;
        for (String categoryName : categories) {
            if (categoryName.equals("Home")) {
                //nothing;
            } else {
                categoryComboBox.getItems().add(i, categoryName);
                i++;
            }

        }

    }
}
