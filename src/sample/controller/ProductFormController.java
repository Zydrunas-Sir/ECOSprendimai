package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.JPA.*;
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;

import java.net.URL;
import java.util.ArrayList;
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
    public CategoryParameters categoryParameters;


    TextField height_field = new TextField();
    TextField depth_field = new TextField();
    TextField width_field = new TextField();
    TextField ip_class_field = new TextField();
    TextField color_field = new TextField();
    TextField type_field = new TextField();
    TextField body_field = new TextField();
    TextField power_field = new TextField();
    TextField resistance_class_field = new TextField();
    TextField light_wave_field = new TextField();
    TextField temperature_field = new TextField();
    TextField dimensions_field = new TextField();
    TextField nominal_voltage_field = new TextField();

    @FXML
    public VBox vbox;

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
        } else if (categoryParameters.isAukstis() && !Validation.isValidAukstis(height_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_AUKSTIS);
        } else if (categoryParameters.isPlotis() && !Validation.isValidPlotis(width_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_PLOTIS);
        } else if (categoryParameters.isGylis() && !Validation.isValidGylis(depth_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_GYLIS);
        } else if (categoryParameters.isIp_klase() && !Validation.isValidIpKlase(ip_class_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_IP_KLASE);
        } else if (categoryParameters.isSpalva() && !Validation.isValidSpalva(color_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_SPALVA);
        } else if (categoryParameters.isKorpusas() && !Validation.isValidKorpusas(body_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_KORPUSAS);
        } else if (categoryParameters.isTipas() && !Validation.isValidTipas(type_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_TIPAS);
        } else if (categoryParameters.isVardine_itampa() && !Validation.isValidVardineItampa(nominal_voltage_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_VARDINE_ITAMPA);
        } else if (categoryParameters.isGalia() && !Validation.isValidGalia(power_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_GALIA);
        } else if (categoryParameters.isSviesos_srautas() && !Validation.isValidSviesosSrautas(light_wave_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_SVIESOS_SRAUTAS);
        } else if (categoryParameters.isAtsparumo_klase() && !Validation.isValidAtsparumas(resistance_class_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_ATSPARUMO_KLASE);
        } else if (categoryParameters.isMatmenys() && !Validation.isValidMatmenys(dimensions_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_MATMENYS);
        } else if (categoryParameters.isDarbine_temperatura() && !Validation.isValidDarbineTemperatura(temperature_field.getText())){
            WarnStyle();
            form_info_label.setText(Constants.CREDENTIALS_IS_NOT_CORRECT_PRODUCT_DARBINE_TEMPERATURA);
        } else {
            registerProduct(item);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<Categories> categoryNames = CategoriesDAO.selectCategoriesForListView();
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
        Stage stage = (Stage) create_product_button.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    void WarnStyle() {
        form_info_label.setText("");
        form_info_label.setStyle("-fx-text-fill: red;");
    }

    public void registerProduct(Categories item) {
        ProductCatalog product = new ProductCatalog(catalog_no_textField.getText(), symbol_textField.getText(),
                price_textField.getText(), Integer.parseInt(nullCheckerForNumbers(stock_textField.getText())),
                item.getId(), Double.parseDouble(nullCheckerForNumbers(height_field.getText())), Double.parseDouble(nullCheckerForNumbers(width_field.getText())),
                Double.parseDouble(nullCheckerForNumbers(depth_field.getText())), nullCheckerForText(ip_class_field.getText()),nullCheckerForText(color_field.getText()) ,
                 nullCheckerForText(body_field.getText()), nullCheckerForText(type_field.getText()), Double.parseDouble(nullCheckerForNumbers(nominal_voltage_field.getText())),
                Double.parseDouble(nullCheckerForNumbers(power_field.getText())), Double.parseDouble(nullCheckerForNumbers(light_wave_field.getText())),
                nullCheckerForText(resistance_class_field.getText()), nullCheckerForText(dimensions_field.getText()), Integer.parseInt(nullCheckerForNumbers(temperature_field.getText())),
                null, null);
        ProductCatalogDAO.insert(product);
        closeWindow();
    }

    public String nullCheckerForNumbers(String value){
        if (value.equals("")){
            return "0";
        }
        return value;
    }

    public String nullCheckerForText(String value){
        if (value.equals("")){
            return null;
        }
        return value;
    }


    public void mouseEventForTableView(ActionEvent event) {
        vbox.getChildren().clear();
        Categories tableItem;
        try {
            if (!categoryComboBox.getSelectionModel().isEmpty()) {
                tableItem = categoryComboBox.getSelectionModel().getSelectedItem();
                if (tableItem.getCategory_parameter_id() != 0){
                    addParametersForProduct(tableItem.getCategory_parameter_id());
                }
                else{
                    WarnStyle();
                    form_info_label.setText("Pasirinkite kategorija turinčia parametrus");
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("mouseEventForTreeView( " + e + " )");
        } catch (NullPointerException e) {
            System.out.println("mouseEventForTreeView(" + e + " )");
        }
    }


    public double formBoxY;

    private void setformBoxY(double y) {
        this.formBoxY = y;
    }

    private double getformBoxY() {
        return formBoxY = this.formBoxY + 20;
    }


    public void addParametersForProduct(int category_parameter_id) {

        double SIZE = 13;

        categoryParameters = CategoryParametersDAO.getParametersByCategoryParameterId(category_parameter_id);

        HBox hBox1 = new HBox();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();



        vBox1.setPadding(new Insets(5, 5, 10, 9));
        vBox2.setPadding(new Insets(5, 20, 10, 5));
        setformBoxY(40);
        vBox2.setSpacing(6);
        vBox1.setSpacing(12);
        vBox1.setAlignment(Pos.CENTER_RIGHT);

        if (categoryParameters.isAukstis()) { //aukstis
            Label label = new Label();
            label.setLayoutX(20);
            label.setLayoutY(getformBoxY());
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Aukštis:");

            vbox.setSpacing(10);
            height_field.setLayoutX(60);
            height_field.setPrefWidth(250);
            height_field.setLayoutY(getformBoxY());
            height_field.setId("aukstis");
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(height_field);
        }
        if (categoryParameters.isPlotis()) { //plotis
            Label label = new Label();
            label.setLayoutX(230);
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setLayoutY(getformBoxY());
            label.setText("Plotis:");


            width_field.setLayoutX(60);
            width_field.setPrefWidth(250);
            width_field.setLayoutY(getformBoxY());
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(width_field);
        }
        if (categoryParameters.isGylis()) { //gylis
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Gylis:");

            depth_field.setLayoutX(60);
            depth_field.setPrefWidth(250);
            depth_field.setLayoutY(getformBoxY());
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(depth_field );
        }
        if (categoryParameters.isIp_klase()) { //ip_klase
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Ip klasė:");

            ip_class_field.setLayoutX(60);
            ip_class_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(ip_class_field);
        }
        if (categoryParameters.isSpalva()) { //spalva
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Spalva:");

            color_field.setLayoutX(60);
            color_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(color_field);
        }
        if (categoryParameters.isKorpusas()) { //korpusas
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Korpusas:");

            body_field.setLayoutX(60);
            body_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(body_field);
        }
        if (categoryParameters.isTipas()) { //tipas
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Tipas:");

            type_field.setLayoutX(60);
            type_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(type_field);
        }
        if (categoryParameters.isVardine_itampa()) { //vardine itampa
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Vardinė įtampa:");

            nominal_voltage_field.setLayoutX(60);
            nominal_voltage_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(nominal_voltage_field);
        }
        if (categoryParameters.isGalia()) { //galia
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Galia:");

            power_field.setLayoutX(60);
            power_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(power_field);
        }
        if (categoryParameters.isAtsparumo_klase()) {
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Atsparumo klasė:");

            resistance_class_field.setLayoutX(60);
            resistance_class_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(resistance_class_field);
        }
        if (categoryParameters.isSviesos_srautas()) {
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Šviesos srautas:");

            light_wave_field.setLayoutX(60);
            light_wave_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(light_wave_field);
        }
        if (categoryParameters.isMatmenys()) {
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Matmenys:");
            dimensions_field.setLayoutX(60);
            dimensions_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(dimensions_field);
        }

        if (categoryParameters.isDarbine_temperatura()) {
            Label label = new Label();
            label.setFont(new Font("Segoe UI Light", SIZE));
            label.setText("Darbinė temperatūra:");

            temperature_field.setLayoutX(60);
            temperature_field.setPrefWidth(250);
            vBox1.getChildren().add(label);
            vBox2.getChildren().add(temperature_field);
        }

        hBox1.getChildren().add(vBox1);
        hBox1.getChildren().add(vBox2);

        vbox.getChildren().add(hBox1);

       System.out.println(temperature_field.getText());
    }


}
