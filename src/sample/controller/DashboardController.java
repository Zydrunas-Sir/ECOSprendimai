package sample.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import sample.JPA.*;
import sample.JPA.user.User;
import sample.JPA.user.UserDAO;
import sample.JPA.user.UserHolder;
import sample.Main;
import sample.utils.Constants;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController extends Main implements Initializable {

    public Button close_button;
    @FXML
    public TableView<ProductCatalog> table;
    public Button open_file;
    public TextField listViewSearchField;
    public Label countAll;
    public TextField tableViewSearchField;
    public TitledPane leftTitledPane;
    public Label current_session_user_email;
    public Label current_session_user_status;
    public ListView<Categories> listView;
    @FXML
    public Button createCategory_Button;
    @FXML
    public Button user_stats_button;
    @FXML
    public Button createProduct_Button;
    // Dešinės panelės label
    @FXML
    public Label catalog_no;
    @FXML
    public Label item_name;
    @FXML
    public Label base_price;
    @FXML
    public Label discount_in_percent;
    @FXML
    public Label price_with_discount;
    @FXML
    public Label delivery_time_in_days_from;
    @FXML
    public Label delivery_time_in_days_to;
    @FXML
    public Label item_package;
    @FXML
    public Label min_order_amount;
    @FXML
    public Label discount_group;
    @FXML
    public Label product_family;
    @FXML
    public Label ean_code;
    @FXML
    public ProgressIndicator loadProgress;


    public static long loggedTimeStart;
    public static long loggedTimeEnd;
    public static long loggedTimeSpent;
    public static int spentTimeInSeconds;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadColumnToTable();
        loadCategoriesToListView();
        currentSessionUserData();
        UserHolder userHolder = UserHolder.getInstance();
        UserDAO.setLastLoginTime(userHolder.getUser());
        loggedTimeStart = System.currentTimeMillis(); // Fiksuoja prisijungimo laiko pradžią
        if (!userHolder.getUser().isAdmin()) {
            unloadUsersButton();
            unloadCreateProductButton();
            unloadCreateCategoryButton();
        }

    }

    //Nusako table'o stulpelius ir jų matmenys.
    public void loadColumnToTable() {

        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        boolean isAdmin = u.isAdmin();


        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // pakeisti lietuviskai kategorijos numeri, produkto pavadinimas, kaina, kiekis.
        TableColumn number = new TableColumn("#");
        TableColumn<ProductCatalog, Integer> catalogNo = new TableColumn<>("Katalogo nr.");
        TableColumn<ProductCatalog, String> symbol = new TableColumn<>("Produkto pavadinimas");
        TableColumn<ProductCatalog, Double> priceNet = new TableColumn<>("Kaina");
        TableColumn<ProductCatalog, Integer> stock = new TableColumn<>("Kiekis");

        table.getColumns().addAll(number, catalogNo, symbol, priceNet, stock);

        number.minWidthProperty().bind(table.widthProperty().multiply(0.05));
        catalogNo.minWidthProperty().bind(table.widthProperty().multiply(0.17));
        symbol.minWidthProperty().bind(table.widthProperty().multiply(0.52));
        priceNet.minWidthProperty().bind(table.widthProperty().multiply(0.09));
        stock.minWidthProperty().bind(table.widthProperty().multiply(0.09));

        number.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ProductCatalog, ProductCatalog>, ObservableValue<ProductCatalog>>) p -> new ReadOnlyObjectWrapper(p.getValue()));

        number.setCellFactory(new Callback<TableColumn<ProductCatalog, ProductCatalog>, TableCell<ProductCatalog, ProductCatalog>>() {
            @Override
            public TableCell<ProductCatalog, ProductCatalog> call(TableColumn<ProductCatalog, ProductCatalog> param) {
                return new TableCell<ProductCatalog, ProductCatalog>() {
                    @Override
                    protected void updateItem(ProductCatalog item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex() + 1 + "");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        number.setSortable(false);
        catalogNo.setCellValueFactory(new PropertyValueFactory<>("catalogNo"));

        symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        if (isAdmin) {
            symbol.setCellFactory(TextFieldTableCell.forTableColumn());
            symbol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductCatalog, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<ProductCatalog, String> event) {
                    ProductCatalog productCatalog = event.getRowValue();
                    productCatalog.setSymbol(event.getNewValue());
                    ProductCatalogDAO.updateSymbol(event.getNewValue(), productCatalog.getId());
                }
            });
        }

        priceNet.setCellValueFactory(new PropertyValueFactory<>("priceNet"));
        if (isAdmin) {
            try {
                priceNet.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                priceNet.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductCatalog, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ProductCatalog, Double> event) {
                        ProductCatalog productCatalog = event.getRowValue();
                        productCatalog.setPriceNet(event.getNewValue());
                        ProductCatalogDAO.updatePrice(event.getNewValue(), productCatalog.getId());
                    }
                });
            } catch (NumberFormatException e) {
                System.out.println("Blogas editas");
            }

        }

        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));


        number.setResizable(true);
        catalogNo.setResizable(true);
        symbol.setResizable(true);
        priceNet.setResizable(true);
        stock.setResizable(true);

        tableViewSearchField.setPromptText("Įveskite produkto pavadinimą filtravimui ...");
    }


    public void loadCategoriesToListView() {
        List<Categories> categoryNamesForListView = CategoriesDAO.selectCategoriesForListView();
        listViewSearchField.setPromptText("Įveskite kategorijos pavadinimą filtravimui ...");
        listView.setCellFactory(lv -> new ListCell<Categories>() {
            @Override
            protected void updateItem(Categories c, boolean empty) {
                super.updateItem(c, empty);
                if (empty) {
                    setText("");
                    setStyle("");
                } else {
                    setText(c.getName());
                    if (c.getCountParents() == 3) {
                        setStyle("-fx-font-weight: bold");
                    } else if (c.getCountParents() == 2) {
                        setText(getText().toUpperCase());
                        setStyle("-fx-font-style: italic");
                    } else if (c.getCountParents() == 1) {
                        setStyle("-fx-text-fill: rgb(9, 96, 235)");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        listView.setItems(createFilteredList(categoryNamesForListView));
    }

    public FilteredList<Categories> createFilteredList(List<Categories> categoryNamesForListView) {
        ObservableList<Categories> observableList = FXCollections.observableList(categoryNamesForListView);
        FilteredList<Categories> filteredList = new FilteredList<>(observableList, p -> true);
        listViewSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return category.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        return filteredList;
    }

    public void mouseEventForListView(MouseEvent mouseEvent) {
        Categories item;
        List<Categories> categories;
        List<ProductCatalog> products;
        try {
            if (!listView.getSelectionModel().isEmpty()) {
                item = listView.getSelectionModel().getSelectedItem();
                categories = CategoriesDAO.selectCategoryById(item.getId());
                products = ProductCatalogDAO.displayAllItems();
                tableSearchFunction(createFilteredList(categories, products));
            }
        } catch (IllegalStateException e) {
            System.out.println("mouseEventForTreeView() IllegalStateExecption");
        } catch (NullPointerException e) {
            System.out.println("mouseEventForTreeView() NullPointerException");
        }

    }

    //Iš excel failo pasiema produktus.
    public void openExcelFileFromDialog() {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        loadProgress();
        // Loading Spinner start.
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openFile(file);
            loadProgress.setVisible(false); // Loading Spinner ends.
        }
        loadProgress.setVisible(false);
    }

    //Konfiguriuoja failo pasirinkimus
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Uzkrauti excel faila");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
    }

    //Tikriną excelio produktų kainas su duombasėje esamomis produkto kainomis, įkelia naujus produktus, jei jų nėra duombasėje, visai tai skaičiuoja.
    private void openFile(File file) {
        ProductCatalogDAO.checkIfCatalogExistsIfNotCreateIt();

        List<ProductCatalog> excelProducts = null;
        List<ProductCatalog> dbProducts = ProductCatalogDAO.displayAllItems();

        try {
            excelProducts = ReadExcelWithProductCatalog.readFileUsingPOI(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int countAffectedProducts = 0;
        int countExcelProducts = 0;
        int countNewProducts = 0;
        int countDBProducts = 0;

        assert excelProducts != null;
        try {
            for (ProductCatalog excelProduct : excelProducts) {
                countExcelProducts++;
                boolean isNewProduct = true;

                for (ProductCatalog dbProduct : dbProducts) {
                    if (dbProduct.getPriceNet() != excelProduct.getPriceNet() && dbProduct.getCatalogNo() == excelProduct.getCatalogNo()) {
                        isNewProduct = false;
                        ProductCatalogDAO.updatePrice(excelProduct.getPriceNet(), dbProduct.getId());
                        countAffectedProducts++;
                    } else if (dbProduct.getPriceNet() == excelProduct.getPriceNet() && dbProduct.getCatalogNo() == excelProduct.getCatalogNo()) {
                        isNewProduct = false;
                        countDBProducts = dbProducts.size() - countAffectedProducts;
                    }
                }
                if (isNewProduct) {
                    countNewProducts++;
                    ProductCatalogDAO.insert(excelProduct);
                }
            }
            createInformationPopUp(countAffectedProducts, countExcelProducts, countNewProducts, countDBProducts);
        } catch (NullPointerException e) {
            System.out.println("openFile() NullPointerException");
        } catch (RuntimeException e) {
            System.out.println("openFile() RuntimeExeception");
        }

    }

    //Sukuria pop up su produktų kiekių informaciją.
    public void createInformationPopUp(int countAffectedProducts, int countExcelProducts, int countNewProducts, int countDBProducts) {
        Window parent = open_file.getScene().getWindow();

        Label label = new Label("Pakeista produktų :" + " " + countAffectedProducts + "\n" +
                "Excel'yje yra produktų : " + countExcelProducts + "\n" +
                "Pridėta naujų produktų : " + countNewProducts + "\n" +
                "Duombazėje nepakeistų produktų : " + countDBProducts);
        final Popup popup = new Popup();
        Button hide = new Button("Ok");
        hide.setOnAction(event -> popup.hide());
        hide.setLayoutX(140);
        hide.setLayoutY(115);
        label.setStyle(" -fx-background-color: grey; -fx-text-fill: white;");
        label.setMinWidth(300);
        label.setMinHeight(150);
        label.setAlignment(Pos.CENTER);
        popup.getContent().addAll(label, hide);
        popup.show(parent);

    }

    //Surenka visus produktus turinčius pasirinktos kategorijos id
    public ObservableList<ProductCatalog> createFilteredList(List<Categories> categories, List<ProductCatalog> products) throws NullPointerException {
        ObservableList<ProductCatalog> filteredProduct = FXCollections.observableArrayList();
        for (Categories category : categories) {
            for (ProductCatalog product : products) {
                if (category.getId() == product.getGroupId()) {
                    filteredProduct.add(product);
                }
            }
        }
        return filteredProduct;
    }

    //Sukelia observablelistą į table'ą su filtravimo funkciją.
    public void tableSearchFunction(ObservableList<ProductCatalog> productCatalogs) {
        FilteredList<ProductCatalog> flProducts = new FilteredList(productCatalogs, product -> true);
        tableViewSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            flProducts.setPredicate(product -> product.getSymbol().toLowerCase().contains(newValue.toLowerCase().trim()));
            countAll.setText("Išviso įrašų : " + flProducts.size());
        });
        SortedList<ProductCatalog> slProducts = new SortedList<>(flProducts);
        slProducts.comparatorProperty().bind(table.comparatorProperty());
        countAll.setText("Išviso įrašų : " + slProducts.size());
        table.setItems(slProducts);
    }

    // Tikrinama ar vidurinėje panelėje buvo pasirinktas item, jeigu buvo, kviečiamas dešinės panelės užpildymo metodas
    // metodui perduodamas item'o katalogo numeris.
    public void mouseEventForTableView() {
        ProductCatalog tableItem;
        try {
            if (!table.getSelectionModel().isEmpty()) {
                tableItem = table.getSelectionModel().getSelectedItem();
                fillDescriptionPanel(tableItem.getCatalogNo());
                System.out.println("Item was selected.");
                System.out.println("Selected Catalog No: " + tableItem.getCatalogNo());
            }
        } catch (IllegalStateException e) {
            System.out.println("mouseEventForTreeView() IllegalStateExecption");
        } catch (NullPointerException e) {
            System.out.println("mouseEventForTreeView() NullPointerException");
        }


    }

    // Suveikia pasirinkus item'ą vidurinėje panelėje.
    // Pirmiausia kreipiamasi į duomenų bazę, patikrinama ar egzistuoja produkto aprašymas.
    // Jei egzistuoja, ištraukiami visi duomenys ir užpildoma dešinė panelė.
    public void fillDescriptionPanel(int catalogNoImported) {
        List<ProductDescription> productByCatalogNo = ProductDescriptionDAO.searchByCatalogNo(catalogNoImported);
        if (productByCatalogNo.isEmpty()) {
            catalog_no.setText(String.valueOf(catalogNoImported));
            item_name.setText("PREKĖS APRAŠYMAS NERASTAS");
            base_price.setText("-");
            discount_in_percent.setText("-");
            delivery_time_in_days_from.setText("-");
            delivery_time_in_days_to.setText("-");
            item_package.setText("-");
            min_order_amount.setText("-");
            discount_group.setText("-");
            product_family.setText("-");
            ean_code.setText("-");
        } else {
            ProductDescription selectedProductDescription = productByCatalogNo.get(0);
            catalog_no.setText(String.valueOf(catalogNoImported));
            item_name.setText(selectedProductDescription.getItemName());
            base_price.setText(String.valueOf(selectedProductDescription.getBasePrice()));
            discount_in_percent.setText(String.valueOf(selectedProductDescription.getDiscountInPercent()));
            delivery_time_in_days_from.setText(String.valueOf(selectedProductDescription.getDeliveryTimeInDaysFrom()));
            delivery_time_in_days_to.setText(String.valueOf(selectedProductDescription.getDeliveryTimeInDaysTo()));
            item_package.setText(selectedProductDescription.getItemPackage());
            min_order_amount.setText(String.valueOf(selectedProductDescription.getMinOrderAmount()));
            discount_group.setText(selectedProductDescription.getDiscountGroup());
            product_family.setText(selectedProductDescription.getProductFamily());
            ean_code.setText(selectedProductDescription.getEanCode());
        }
    }

    private void currentSessionUserData() {

        //Getting data from the last scene calling a class UserHolder
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String email = u.getEmail();
        boolean isAdmin = u.isAdmin();


        if (isAdmin) {
            current_session_user_status.setText(Constants.CURRENT_SESSION_STATUS_ADMIN);
        } else {
            current_session_user_status.setText(Constants.CURRENT_SESSION_STATUS_USER);
        }

        current_session_user_email.setText(email);
    }

    public void unloadUsersButton() {
        user_stats_button.setVisible(false);

    }

    public void unloadCreateProductButton() {
        createProduct_Button.setVisible(false);

    }

    public void unloadCreateCategoryButton() {
        createCategory_Button.setVisible(false);

    }

    // Atidaro langą su vartotojų sąrašu
    public void openUserStats() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.USER_STATS_VIEW_PATH));
            Stage statsStage = new Stage();
            Scene scene = new Scene(root);
            statsStage.setMinWidth(500);
            statsStage.setMinHeight(400);
            statsStage.setTitle("Registruotų vartotojų sąrašas");
            statsStage.setScene(scene);
            statsStage.setResizable(true);
            statsStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


    //Grizta i prisi
    public void goBackToLogin(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH)));
            Stage loginStage = new Stage();
            Scene scene = new Scene(root, Constants.LOGIN_WINDOW_WIDTH, Constants.LOGIN_WINDOW_HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
            loginStage.setTitle("Prisijungimas");
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.show();
            windowClose();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createNewCategory(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.CATEGORY_FORM_VIEW_PATH)));
            Stage LoginStage = new Stage();
            Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
            LoginStage.setTitle("");
            LoginStage.setScene(scene);
            LoginStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Atidaro produkto sukurimo form'a
    public void createNewProduct(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.PRODUCT_FORM_VIEW_PATH)));
            Stage LoginStage = new Stage();
            Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
            LoginStage.setTitle("");
            LoginStage.setScene(scene);
            LoginStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void windowClose() { //Uzdaro prisijungimo langa
        Stage stage = (Stage) close_button.getScene().getWindow();
        calculateSpentTimeLoggedIn();
        stage.close();
    }

    public static void closeDashboard() {
        calculateSpentTimeLoggedIn();
    }

    public static void calculateSpentTimeLoggedIn() {
        loggedTimeEnd = System.currentTimeMillis();
        loggedTimeSpent = loggedTimeEnd - loggedTimeStart;
        spentTimeInSeconds = (int) loggedTimeSpent / 1000;
        System.out.println("Session time: " + spentTimeInSeconds + " seconds");
        UserHolder userHolder = UserHolder.getInstance();
        UserDAO.updateUserTimeSpent(userHolder.getUser(), spentTimeInSeconds);
    }

    private void loadProgress() {
        Task copyWorker = createWorker();
        loadProgress.progressProperty().bind(copyWorker.progressProperty());
        table.itemsProperty().bind(copyWorker.valueProperty());
        new Thread(copyWorker).start();
        loadProgress.setVisible(true);

    }

    // Loading Spinner move
    public Task createWorker() {
        return new Task() {
            @Override
            protected TabPane call() throws Exception {
                TabPane tabPane = new TabPane();
                final int count = 1000 - 1;
                for (int i = 1; i <= count; i++) {
                    Thread.sleep(100000);
                }
                return tabPane;
            }
        };
    }
    // Loading Spinner set-up-ends
}