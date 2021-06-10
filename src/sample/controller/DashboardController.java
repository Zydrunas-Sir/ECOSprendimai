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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.JPA.*;
import sample.JPA.user.User;
import sample.JPA.user.UserDAO;
import sample.JPA.user.UserHolder;
import sample.Main;
import sample.utils.Constants;
import sample.utils.Validation;

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
  public TextField listViewSearchField;
  public Label countAll;
  public TextField tableViewSearchField;
  public TitledPane leftTitledPane;
  public Label current_session_user_email;
  public Label current_session_user_status;
  public ListView<Categories> listView;


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
  @FXML
  private MenuBar menu_bar;

  public static long loggedTimeStart;
  public static long loggedTimeEnd;
  public static long loggedTimeSpent;
  public static int spentTimeInSeconds;
  List<Categories> categoryNamesForListView;
  ObservableList<Categories> observableCategoryList;
  ObservableList<ProductCatalog> observableProducts;
  List<ProductCatalog> fullProductList;
  List<Categories> fullCategoryList;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadColumnToTable();
    loadCategoriesToListView();
    currentSessionUserData();
    categoryNamesForListView = CategoriesDAO.selectCategoriesForListView();
    observableCategoryList = FXCollections.observableList(categoryNamesForListView);
    listView.setItems(observableCategoryList);
    reloadProductTableView();
    UserHolder userHolder = UserHolder.getInstance();
    UserDAO.setLastLoginTime(userHolder.getUser());
    loggedTimeStart = System.currentTimeMillis(); // Fiksuoja prisijungimo laiko pradžią

    if (!userHolder.getUser().isAdmin()) {
      menu_bar.setVisible(false);
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
    TableColumn<ProductCatalog, String> priceNet = new TableColumn<>("Kaina");
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
          ProductCatalog ProductCatalog = event.getRowValue();
          ProductCatalog.setSymbol(event.getNewValue());
          ProductCatalogDAO.updateSymbol(event.getNewValue(), ProductCatalog.getId());
        }
      });
    }

    priceNet.setCellValueFactory(new PropertyValueFactory<>("priceNet"));
    if (isAdmin) {
      try {
        priceNet.setCellFactory(TextFieldTableCell.forTableColumn());
        priceNet.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductCatalog, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ProductCatalog, String> event) {
            if (Validation.isValidPrice(event.getNewValue())) {
              ProductCatalog ProductCatalog = event.getRowValue();
              ProductCatalog.setPriceNet(event.getNewValue());
              ProductCatalogDAO.updatePrice(event.getNewValue(), ProductCatalog.getId());
              System.out.println("REGEX VALIDATION SUCCESS");
            } else {
              ProductCatalog ProductCatalog = event.getRowValue();
              ProductCatalog.setPriceNet(event.getOldValue());
              table.refresh();
              showPopupWindow("BLOGAI ĮVESTA PRODUKTO KAINA", "Tinkami formatai: \n \"30\", \"7.15\", \"15.0\".", "#f8d7da", "#842029");

              System.out.println("REGEX VALIDATION DENIED");
            }
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

  }

  public void createFilteredCategoryList() {
    FilteredList<Categories> filteredList = new FilteredList<>(observableCategoryList, p -> true);
    listViewSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredList.setPredicate(category -> {
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }
        String lowerCaseFilter = newValue.toLowerCase();
        return category.getName().toLowerCase().contains(lowerCaseFilter);
      });
    });
    listView.setItems(filteredList);
  }

  public void mouseEventForListView(MouseEvent mouseEvent) {
    Categories item;
    try {
      if (!listView.getSelectionModel().isEmpty()) {
        item = listView.getSelectionModel().getSelectedItem();
        fullCategoryList = CategoriesDAO.selectCategoryById(item.getId());
        observableProducts = FXCollections.observableList(createFilteredProductList(fullCategoryList, fullProductList));
        countTableViewObservableProducts(observableProducts);
        table.setItems(observableProducts);

      }
    } catch (IllegalStateException e) {
      System.out.println("mouseEventForListView() IllegalStateExecption");
    } catch (NullPointerException e) {
      System.out.println("mouseEventForListView() NullPointerException");
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
    reloadProductTableView();
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
    //ProductCatalogDAO.checkIfCatalogExistsIfNotCreateIt();

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
          if (dbProduct.getPriceNet() != excelProduct.getPriceNet() && dbProduct.getCatalogNo().equalsIgnoreCase(excelProduct.getCatalogNo())) {
            isNewProduct = false;
            ProductCatalogDAO.updatePrice(excelProduct.getPriceNet(), dbProduct.getId());
            countAffectedProducts++;
          } else if (dbProduct.getPriceNet() == excelProduct.getPriceNet() && dbProduct.getCatalogNo().equalsIgnoreCase(excelProduct.getCatalogNo())) {
            isNewProduct = false;
            countDBProducts = dbProducts.size() - countAffectedProducts;
          }
        }
        if (isNewProduct) {
          countNewProducts++;
          ProductCatalogDAO.insert(excelProduct);
        }
      }
      String successToPopup = "Pakeista produktų: " + countAffectedProducts + "\nFaile buvo produktų: " + countExcelProducts + "\nPridėti nauji produktai: " + countNewProducts + "\nDuomenų bazėje nepaveikti produktai: " + countDBProducts + "\n";
      showPopupWindow("FAILAS SĖKMINGAI ĮKELTAS", successToPopup, "#d1e7dd", "#0f5132");
//      createInformationPopUp(countAffectedProducts, countExcelProducts, countNewProducts, countDBProducts);
    } catch (NullPointerException e) {
      System.out.println("openFile(" + e + " )");
    } catch (RuntimeException e) {
      System.out.println("openFile(" + e + " )");
    }

  }

  //Sukuria pop up su produktų kiekių informaciją.
  public void createInformationPopUp(int countAffectedProducts, int countExcelProducts, int countNewProducts, int countDBProducts) {
    Window parent = menu_bar.getScene().getWindow();

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
  public ObservableList<ProductCatalog> createFilteredProductList(List<Categories> categories, List<ProductCatalog> products) {
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
  public void tableViewSearchFunction() {
    try {
      FilteredList<ProductCatalog> flProducts = new FilteredList(observableProducts, product -> true);
      tableViewSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
        flProducts.setPredicate(product -> product.getSymbol().toLowerCase().contains(newValue.toLowerCase().trim()));
        countTableViewFilteredProducts(flProducts);
      });
      SortedList<ProductCatalog> slProducts = new SortedList<>(flProducts);
      slProducts.comparatorProperty().bind(table.comparatorProperty());
      countTableViewSortedProducts(slProducts);
      table.setItems(slProducts);

    } catch (Exception e) {
      System.out.println("tableSearch error ( " + e + " )");
    }
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

  public void countTableViewObservableProducts(ObservableList<ProductCatalog> products){
    countAll.setText("Išviso įrašų : " + products.size());
  }

  public void countTableViewFilteredProducts(FilteredList<ProductCatalog> products){
    countAll.setText("Išviso įrašų : " + products.size());
  }

  public void countTableViewSortedProducts(SortedList<ProductCatalog> products){
    countAll.setText("Išviso įrašų : " + products.size());
  }

  // Suveikia pasirinkus item'ą vidurinėje panelėje.
  // Pirmiausia kreipiamasi į duomenų bazę, patikrinama ar egzistuoja produkto aprašymas.
  // Jei egzistuoja, ištraukiami visi duomenys ir užpildoma dešinė panelė.
  public void fillDescriptionPanel(String catalogNoImported) {
    List<ProductDescription> productByCatalogNo = ProductDescriptionDAO.searchByCatalogNo(catalogNoImported);
    if (productByCatalogNo.isEmpty()) {
      catalog_no.setText(catalogNoImported);
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
      catalog_no.setText(catalogNoImported);
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


  // Atidaro langą su vartotojų sąrašu
  public void openUserStats() {
    try {
      Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.USER_STATS_VIEW_PATH));
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
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH)));
      Stage loginStage = new Stage();
      Scene scene = new Scene(root, Constants.LOGIN_WINDOW_WIDTH, Constants.LOGIN_WINDOW_HEIGHT);
      scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
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

  public void createNewCategory(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.CATEGORY_FORM_VIEW_PATH)));
      Stage createCategoryStage = new Stage();
      Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
      scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
      createCategoryStage.setTitle("Kategorijos anketa");
      createCategoryStage.setScene(scene);
      createCategoryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
      e.getCause();
    }
  }

  //Atidaro produkto sukurimo form'a
  public void createNewProduct(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.PRODUCT_FORM_VIEW_PATH)));
      Stage LoginStage = new Stage();
      Scene scene = new Scene(root, Constants.REGISTER_WINDOW_WIDTH, Constants.REGISTER_WINDOW_HEIGHT);
      scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
      LoginStage.setTitle("Produkto anketa");
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
    //table.itemsProperty().bind(copyWorker.valueProperty());
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
  }// Loading Spinner set-up-ends


  public void reloadCategoryListView(){
    listView.setItems(observableCategoryList);
  }

  public void reloadProductTableView(){
    fullProductList = ProductCatalogDAO.displayAllItems();
    fullCategoryList = CategoriesDAO.displayAllCategories();
    observableProducts = createFilteredProductList(fullCategoryList, fullProductList);
    countTableViewObservableProducts(observableProducts);
    table.setItems(observableProducts);
  }

  public void showPopupWindow(String title, String information, String titleBackroundColor, String titleTextColor) {

    Window parent = table.getScene().getWindow();
    Popup popup = new Popup();

    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setStyle("-fx-border-width: 1; -fx-border-color: #3268a8; -fx-effect: dropshadow(two-pass-box, #000000, 10, 0.0, 1.0, 1.0);");


    HBox hBox1 = new HBox();
    hBox1.setStyle("-fx-background-color: " + titleBackroundColor + ";");
    hBox1.setAlignment(Pos.CENTER);
    Label labelTitle = new Label();
    labelTitle.setAlignment(Pos.CENTER);
    labelTitle.setMinSize(400, 50);
    labelTitle.setText(title + "\n");
    labelTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
    labelTitle.setTextFill(Paint.valueOf(titleTextColor));
    hBox1.getChildren().add(labelTitle);

    HBox hBox2 = new HBox();
    hBox2.setStyle("-fx-background-color: #FFFFFF;");
    hBox2.setAlignment(Pos.CENTER);
    Label stringInformation = new Label();
    stringInformation.setMinSize(230, 100);
    stringInformation.setStyle("-fx-font-size: 20;");
    stringInformation.setText(information);
    hBox2.getChildren().add(stringInformation);

    HBox hBox3 = new HBox();
    hBox3.setStyle("-fx-background-color: #d3d6d8;");
    hBox3.setAlignment(Pos.CENTER);
    hBox3.setMinSize(200, 70);
    Button okButton = new Button();
    okButton.setText("Gerai" + "\n");
    okButton.setStyle("-fx-font-size: 18;");
    okButton.setOnAction(event -> popup.hide());
    hBox3.getChildren().add(okButton);

    root.getChildren().add(hBox1);
    root.getChildren().add(hBox2);
    root.getChildren().add(hBox3);

    popup.getContent().addAll(root);
    popup.show(parent);


  }
}


//TODO: Užvardinti scenas , reload metodą.