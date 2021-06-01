package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import org.eclipse.fx.ui.controls.tree.FilterableTreeItem;
import org.eclipse.fx.ui.controls.tree.TreeItemPredicate;
import sample.JPA.*;
import sample.JPA.user.User;
import sample.JPA.user.UserHolder;
import sample.Main;
import sample.utils.Constants;

import javax.swing.*;
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
    public TextField treeViewSearchField;
    public Label countAll;
    public TextField tableViewSearchField;
    public TitledPane leftTitledPane;
    public Label current_session_user_email;
    public Label current_session_user_status;
    TreeView<CategoryItem> treeView = new TreeView<>();

    // Dešinės panelės label
    @FXML
    public Label catalogNo;
    @FXML
    public Label itemName;
    @FXML
    public Label basePrice;
    @FXML
    public Label discountInPercent;
    @FXML
    public Label priceWithDiscount;
    @FXML
    public Label deliveryTimeInDaysFrom;
    @FXML
    public Label deliveryTimeInDaysTo;
    @FXML
    public Label itemPackage;
    @FXML
    public Label minOrderAmount;
    @FXML
    public Label discountGroup;
    @FXML
    public Label productFamily;
    @FXML
    public Label eanCode;

    public static long loggedTimeStart;
    public static long loggedTimeElapsed;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadColumnToTable();
        createContents();
        currentSessionUserData();
        //loggedTimeStart = System.nanoTime();
    }

    //Nusako table'o stulpelius ir jų matmenys.
    public void loadColumnToTable() {

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
        symbol.setCellFactory(TextFieldTableCell.forTableColumn());
        symbol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductCatalog, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductCatalog, String> event) {
                ProductCatalog productCatalog = event.getRowValue();
                productCatalog.setSymbol(event.getNewValue());
                ProductCatalogDAO.updateSymbol(event.getNewValue(), productCatalog.getId());
            }
        });
        priceNet.setCellValueFactory(new PropertyValueFactory<>("priceNet"));
        priceNet.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceNet.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductCatalog, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductCatalog, Double> event) {
                ProductCatalog productCatalog = event.getRowValue();
                productCatalog.setPriceNet(event.getNewValue());
                ProductCatalogDAO.updatePrice(event.getNewValue(), productCatalog.getId());
            }
        });
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));


        number.setResizable(true);
        catalogNo.setResizable(true);
        symbol.setResizable(true);
        priceNet.setResizable(true);
        stock.setResizable(true);

        tableViewSearchField.setPromptText("Įveskite produkto pavadinimą filtravimui ...");
    }

    //Iš excel failo pasiema produktus.
    public void openExcelFileFromDialog() {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            JOptionPane.showMessageDialog(null, "Prašome palaukti. Nuskaitomas Excel Failas: " + file.getName());
            openFile(file);
        }
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
        Window parent = open_file.getScene().getWindow();

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

    //Sukuria vbox elementą, kuriame ir yra treeView su filtravimo funkcija.
    public void createContents() {
        VBox vBoxElement = new VBox(6);
        vBoxElement.getChildren().add(createFilterPane());
        Node pane = createPane();
        VBox.setVgrow(pane, Priority.ALWAYS);
        vBoxElement.getChildren().add(pane);
    }

    //Redaguoja ir sujungia titled pane su text field.
    private Node createFilterPane() {
        treeViewSearchField.setPromptText("Įveskite kategorijos pavadinimą filtravimui ...");
        TitledPane pane = new TitledPane("Filter", treeViewSearchField);
        pane.setCollapsible(false);
        return pane;
    }

    //Sukuria hbox elementą, kuris laiko treeview.
    private Node createPane() {
        HBox hBoxElement = new HBox(6);
        Node filteredTree = createFilteredTree();
        HBox.setHgrow(filteredTree, Priority.ALWAYS);
        hBoxElement.getChildren().add(filteredTree);
        leftTitledPane.setContent(filteredTree);
        return new BorderPane(hBoxElement);
    }

    //Sukuria treeview su filtravimu.
    private Node createFilteredTree() {
        FilterableTreeItem<CategoryItem> root = loadsProductsToCatalogTree();
        root.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            if (treeViewSearchField.getText() == null || treeViewSearchField.getText().isEmpty())
                return null;
            return TreeItemPredicate.create(categoryItem -> categoryItem.toString().toLowerCase().contains(treeViewSearchField.getText().toLowerCase()));
        }, treeViewSearchField.textProperty()));
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        mouseEventForTreeView();
        mouseEventForTableView();
        return treeView;
    }

    //Filtruoja treeviewitem ,kad sutaptu su duombazės pavadinimų.
    public String homeFilter(String itemName) {
        if (itemName.equals("Visos kategorijos")) {
            return "Home";
        }
        return itemName;
    }

    //Sukuria filtruojama treeview item.
    private FilterableTreeItem<CategoryItem> createFolder(String name) {
        return new FilterableTreeItem<>(new CategoryItem(name));
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

    //Paspaudus ant treeView item'o, table'ę atsiras visi jam priskirti produktai.
    public void mouseEventForTreeView() {
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            TreeItem<CategoryItem> item;
            List<Categories> categories;
            List<ProductCatalog> products;
            ProductCatalog tableItem;
            try {
                if (!treeView.getSelectionModel().isEmpty()) {
                    item = treeView.getSelectionModel().getSelectedItem();
                    categories = CategoriesDAO.selectCategory(homeFilter(item.getValue().getName()));
                    products = ProductCatalogDAO.displayAllItems();
                    tableSearchFunction(createFilteredList(categories, products));
                }
            } catch (IllegalStateException e) {
                System.out.println("mouseEventForTreeView() IllegalStateExecption");
            } catch (NullPointerException e) {
                System.out.println("mouseEventForTreeView() NullPointerException");
            }


        });
    }

    // Tikrinama ar vidurinėje panelėje buvo pasirinktas item, jeigu buvo, kviečiamas dešinės panelės užpildymo metodas
    // metodui perduodamas item'o katalogo numeris.
    public void mouseEventForTableView() {
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
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

        });
    }

    // Suveikia pasirinkus item'ą vidurinėje panelėje.
    // Pirmiausia kreipiamasi į duomenų bazę, patikrinama ar egzistuoja produkto aprašymas.
    // Jei egzistuoja, ištraukiami visi duomenys ir užpildoma dešinė panelė.
    public void fillDescriptionPanel(int catalogNoImported) {
        List<ProductDescription> productByCatalogNo = ProductDescriptionDAO.searchByCatalogNo(catalogNoImported);
        if (productByCatalogNo.isEmpty()) {
            catalogNo.setText(String.valueOf(catalogNoImported));
            itemName.setText("PREKĖS APRAŠYMAS NERASTAS");
            basePrice.setText("-");
            discountInPercent.setText("-");
            deliveryTimeInDaysFrom.setText("-");
            deliveryTimeInDaysTo.setText("-");
            itemPackage.setText("-");
            minOrderAmount.setText("-");
            discountGroup.setText("-");
            productFamily.setText("-");
            eanCode.setText("-");
        } else {
            ProductDescription selectedProductDescription = productByCatalogNo.get(0);
            catalogNo.setText(String.valueOf(catalogNoImported));
            itemName.setText(selectedProductDescription.getItemName());
            basePrice.setText(String.valueOf(selectedProductDescription.getBasePrice()));
            discountInPercent.setText(String.valueOf(selectedProductDescription.getDiscountInPercent()));
            deliveryTimeInDaysFrom.setText(String.valueOf(selectedProductDescription.getDeliveryTimeInDaysFrom()));
            deliveryTimeInDaysTo.setText(String.valueOf(selectedProductDescription.getDeliveryTimeInDaysTo()));
            itemPackage.setText(selectedProductDescription.getItemPackage());
            minOrderAmount.setText(String.valueOf(selectedProductDescription.getMinOrderAmount()));
            discountGroup.setText(selectedProductDescription.getDiscountGroup());
            productFamily.setText(selectedProductDescription.getProductFamily());
            eanCode.setText(selectedProductDescription.getEanCode());
        }
    }

    private void currentSessionUserData() {

        //Getting data from the last scene calling a class UserHolder
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        String email = u.getEmail();
        Boolean isAdmin = u.isAdmin();


        if (isAdmin == true) {
            current_session_user_status.setText(Constants.CURRENT_SESSION_STATUS_ADMIN);
        } else {
            current_session_user_status.setText(Constants.CURRENT_SESSION_STATUS_USER);
        }

        current_session_user_email.setText(email);
    }

    // Atidaro langą su vartotojų sąrašu
    public void openUserStats() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.USER_STATS_VIEW_PATH));
            Stage registerStage = new Stage();
            Scene scene = new Scene(root);
            registerStage.setTitle("Informacija apie programos vartotojus");
            registerStage.setScene(scene);
            registerStage.setResizable(true);
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void goBackToLogin(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH)));
            Stage LoginStage = new Stage();
            Scene scene = new Scene(root, Constants.LOGIN_WINDOW_WIDTH, Constants.LOGIN_WINDOW_HEIGHT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Constants.CSS_DIRECTORY_PATH)).toExternalForm());
            LoginStage.setTitle("");
            LoginStage.setScene(scene);
            LoginStage.show();
            windowClose();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createNewProduct(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.PRODUCTFORM_VIEW_PATH)));
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
        //loggedTimeElapsed = System.nanoTime() - loggedTimeStart;
        //UserDAO.updateUserTimeSpent((int) loggedTimeElapsed);
        stage.close();
    }

    //Surašomi visi treeview item'imai ir jų relationship'ai
    public FilterableTreeItem<CategoryItem> loadsProductsToCatalogTree() {


        FilterableTreeItem<CategoryItem> root = new FilterableTreeItem<>(new CategoryItem("Root"));


        FilterableTreeItem<CategoryItem> home = createFolder("Visos kategorijos");

        home.setExpanded(true);

        FilterableTreeItem<CategoryItem> skydai = createFolder("Skydai");
        FilterableTreeItem<CategoryItem> apsvietimas = createFolder("Apšvietimas");
        FilterableTreeItem<CategoryItem> kabeliai = createFolder("Kabeliai");
        FilterableTreeItem<CategoryItem> vamzdziaiIrGofra = createFolder("Vamzdžiai Ir Gofra");
        FilterableTreeItem<CategoryItem> instaliacinesPrekes = createFolder("Instaliacinės prekės");
        FilterableTreeItem<CategoryItem> metalinesKonstrukcijos = createFolder("Metalinės konstrukcijos");
        FilterableTreeItem<CategoryItem> izeminimasIrZaibosauga = createFolder("Įžeminimas ir žaibosauga");
        FilterableTreeItem<CategoryItem> elektromechanika = createFolder("Elektromechanika");

        root.getInternalChildren().add(home);

        home.getInternalChildren().add(skydai);
        home.getInternalChildren().add(apsvietimas);
        home.getInternalChildren().add(kabeliai);
        home.getInternalChildren().add(vamzdziaiIrGofra);
        home.getInternalChildren().add(instaliacinesPrekes);
        home.getInternalChildren().add(metalinesKonstrukcijos);
        home.getInternalChildren().add(izeminimasIrZaibosauga);
        home.getInternalChildren().add(elektromechanika);

        //Skydai
        FilterableTreeItem<CategoryItem> laukoSkydai = createFolder("Lauko skydai");
        FilterableTreeItem<CategoryItem> vidausSkydai = createFolder("Vidaus skydai");

        //Apšvietimas
        FilterableTreeItem<CategoryItem> laukoApsvietimas = createFolder("Lauko apšvietimas");
        FilterableTreeItem<CategoryItem> vidausApsvietimas = createFolder("Vidaus apšvietimas");

        //Kabeliai
        FilterableTreeItem<CategoryItem> instaliaciniaiKabeliai = createFolder("Instaliaciniai kabeliai");
        FilterableTreeItem<CategoryItem> jegosKabeliai = createFolder("Jėgos kabeliai");
        FilterableTreeItem<CategoryItem> behalogeniniaiKabeliai = createFolder("Behalogeniniai kabeliai");
        FilterableTreeItem<CategoryItem> kontroliniaiKabeliai = createFolder("Kontroliniai kabeliai");
        FilterableTreeItem<CategoryItem> laidai = createFolder("Laidai");
        FilterableTreeItem<CategoryItem> internetiniaiKabeliai = createFolder("Internetinai kabeliai");
        FilterableTreeItem<CategoryItem> antgaliaiKabeliui = createFolder("Antgaliai kabeliui");
        FilterableTreeItem<CategoryItem> movosKabeliui = createFolder("Movos kabeliui");

        //Vamzdžiai ir gofra
        FilterableTreeItem<CategoryItem> lauko = createFolder("Lauko");
        FilterableTreeItem<CategoryItem> vidaus = createFolder("Vidaus");

        //Instaliacinės prekės
        FilterableTreeItem<CategoryItem> jungikliaiIrKistukiniaiLizdai = createFolder("Jungikliai ir kištukiniai lizdai");
        FilterableTreeItem<CategoryItem> potinkinesDezutes = createFolder("Potinkinės dėžutės");
        FilterableTreeItem<CategoryItem> sildymoElementai = createFolder("Šildymo elementai");
        FilterableTreeItem<CategoryItem> judesioIrBuvioJutikliai = createFolder("Judesio ir būvio jutikliai");
        FilterableTreeItem<CategoryItem> virstinkinesInstaliacinėsDezutes = createFolder("Virštinkinės instaliacinės dėžutės");
        FilterableTreeItem<CategoryItem> grindinesDezutes = createFolder("Grindinės dėžutės");
        FilterableTreeItem<CategoryItem> modulineSistema = createFolder("Modulinė 45x45 sistema");


        //metalines Konstrukcijos
        FilterableTreeItem<CategoryItem> kopecios = createFolder("Kopėčios");
        FilterableTreeItem<CategoryItem> loveliai = createFolder("Loveliai");
        FilterableTreeItem<CategoryItem> apsvietimoLovelis = createFolder("Apšvietimo lovelis");


        //Įžeminimas ir žaibosauga
        FilterableTreeItem<CategoryItem> izeminimoStrypai = createFolder("Įžeminimo strypai");
        FilterableTreeItem<CategoryItem> cinkuotaJuosta = createFolder("Cinkuota juosta");
        FilterableTreeItem<CategoryItem> cinkuotaViela = createFolder("Cinkuota viela");
        FilterableTreeItem<CategoryItem> jungtys = createFolder("Jungtys");
        FilterableTreeItem<CategoryItem> laikikliai = createFolder("Laikikliai");
        FilterableTreeItem<CategoryItem> aktyviZaibosauga = createFolder("Aktyvi žaibosauga");
        FilterableTreeItem<CategoryItem> pasyviZaibosauga = createFolder("Pasyvi žaibosauga");
        FilterableTreeItem<CategoryItem> virsItampioRibotuvai = createFolder("Virš įtampio ribotuvai");
        FilterableTreeItem<CategoryItem> priedai = createFolder("Priedai");

        //Elektromechanika
        FilterableTreeItem<CategoryItem> automatiniaiJungikliai = createFolder("Automatiniai jungikliai");
        FilterableTreeItem<CategoryItem> srovesNuotekioReles = createFolder("Srovės nuotekio relės");
        FilterableTreeItem<CategoryItem> srovesNuotekioRelesKartuSuAutomatiniuJungikliu = createFolder("Srovės nuotekio relės kartu su automatiniu jungikliu");
        FilterableTreeItem<CategoryItem> valdymoReles = createFolder("Valdymo relės");
        FilterableTreeItem<CategoryItem> kontaktoriai = createFolder("Kontaktoriai");
        FilterableTreeItem<CategoryItem> kirtikliai = createFolder("Kirtikliai");
        FilterableTreeItem<CategoryItem> moduliniaiJungikliai = createFolder("Moduliniai jungikliai");
        FilterableTreeItem<CategoryItem> saugikliai = createFolder("Saugikliai");

        //Skydai add
        skydai.getInternalChildren().add(laukoSkydai);
        skydai.getInternalChildren().add(vidausSkydai);

        //Apsvietimas add
        apsvietimas.getInternalChildren().add(laukoApsvietimas);
        apsvietimas.getInternalChildren().add(vidausApsvietimas);

        //Kabeliai add
        kabeliai.getInternalChildren().add(instaliaciniaiKabeliai);
        kabeliai.getInternalChildren().add(jegosKabeliai);
        kabeliai.getInternalChildren().add(behalogeniniaiKabeliai);
        kabeliai.getInternalChildren().add(kontroliniaiKabeliai);
        kabeliai.getInternalChildren().add(laidai);
        kabeliai.getInternalChildren().add(internetiniaiKabeliai);
        kabeliai.getInternalChildren().add(antgaliaiKabeliui);
        kabeliai.getInternalChildren().add(movosKabeliui);

        //VamzdziaiIrGofra add
        vamzdziaiIrGofra.getInternalChildren().add(lauko);
        vamzdziaiIrGofra.getInternalChildren().add(vidaus);

        //Instaliacinės prekės add
        instaliacinesPrekes.getInternalChildren().add(jungikliaiIrKistukiniaiLizdai);
        instaliacinesPrekes.getInternalChildren().add(potinkinesDezutes);
        instaliacinesPrekes.getInternalChildren().add(sildymoElementai);
        instaliacinesPrekes.getInternalChildren().add(judesioIrBuvioJutikliai);
        instaliacinesPrekes.getInternalChildren().add(virstinkinesInstaliacinėsDezutes);
        instaliacinesPrekes.getInternalChildren().add(grindinesDezutes);
        instaliacinesPrekes.getInternalChildren().add(modulineSistema);

        //Metalinės konstrukcijos add
        metalinesKonstrukcijos.getInternalChildren().add(kopecios);
        metalinesKonstrukcijos.getInternalChildren().add(loveliai);
        metalinesKonstrukcijos.getInternalChildren().add(apsvietimoLovelis);

        //Įžeminimas ir žaibosauga add
        izeminimasIrZaibosauga.getInternalChildren().add(izeminimoStrypai);
        izeminimasIrZaibosauga.getInternalChildren().add(cinkuotaJuosta);
        izeminimasIrZaibosauga.getInternalChildren().add(cinkuotaViela);
        izeminimasIrZaibosauga.getInternalChildren().add(jungtys);
        izeminimasIrZaibosauga.getInternalChildren().add(laikikliai);
        izeminimasIrZaibosauga.getInternalChildren().add(aktyviZaibosauga);
        izeminimasIrZaibosauga.getInternalChildren().add(pasyviZaibosauga);
        izeminimasIrZaibosauga.getInternalChildren().add(virsItampioRibotuvai);
        izeminimasIrZaibosauga.getInternalChildren().add(priedai);

        //Elektromechanika add
        elektromechanika.getInternalChildren().add(automatiniaiJungikliai);
        elektromechanika.getInternalChildren().add(srovesNuotekioReles);
        elektromechanika.getInternalChildren().add(srovesNuotekioRelesKartuSuAutomatiniuJungikliu);
        elektromechanika.getInternalChildren().add(valdymoReles);
        elektromechanika.getInternalChildren().add(kontaktoriai);
        elektromechanika.getInternalChildren().add(kirtikliai);
        elektromechanika.getInternalChildren().add(moduliniaiJungikliai);
        elektromechanika.getInternalChildren().add(saugikliai);

        //Skydai - Vidaus skydai
        FilterableTreeItem<CategoryItem> metalinesDezes = createFolder("Metalinės dėžės");
        FilterableTreeItem<CategoryItem> potinkiniaiSkydeliai = createFolder("Potinkiniai skydeliai");
        FilterableTreeItem<CategoryItem> virstinkiniaiSkydeliai = createFolder("Virštinkiniai skydeliai");
        FilterableTreeItem<CategoryItem> remontiniaiSkydeliai = createFolder("Remontiniai skydeliai");

        //Skydai - Vid add
        vidausSkydai.getInternalChildren().add(metalinesDezes);
        vidausSkydai.getInternalChildren().add(potinkiniaiSkydeliai);
        vidausSkydai.getInternalChildren().add(virstinkiniaiSkydeliai);
        vidausSkydai.getInternalChildren().add(remontiniaiSkydeliai);

        //Skydai - potinkiniaiSkydeliai
        FilterableTreeItem<CategoryItem> potinkiniaiMetaliniaiSkydeliai = createFolder("Metaliniai");
        FilterableTreeItem<CategoryItem> potinkiniaiPlastikiniaiSkydeliai = createFolder("Plastikiniai");


        //Skydai - virstinkiniaiSkydeliai
        FilterableTreeItem<CategoryItem> virstinkiniaiMetaliniaiSkydeliai = createFolder("Metaliniai");
        FilterableTreeItem<CategoryItem> virstinkiniaiPlastikiniaiSkydeliai = createFolder("Plastikiniai");

        //Skydai - potinkiniaiSkydeliai add
        potinkiniaiSkydeliai.getInternalChildren().add(potinkiniaiMetaliniaiSkydeliai);
        potinkiniaiSkydeliai.getInternalChildren().add(potinkiniaiPlastikiniaiSkydeliai);

        //Skydai - virstinkiniaiSkydeliai add
        virstinkiniaiSkydeliai.getInternalChildren().add(virstinkiniaiMetaliniaiSkydeliai);
        virstinkiniaiSkydeliai.getInternalChildren().add(virstinkiniaiPlastikiniaiSkydeliai);

        //Skydai - virstinkiniaiSkydeliai - Plastikiniai
        FilterableTreeItem<CategoryItem> virstinkiniaiPlastikinioSkydelioIp40 = createFolder("IP40");
        FilterableTreeItem<CategoryItem> virstinkiniaiPlastikinioSkydelioIp65 = createFolder("IP65");

        //Skydai - virstinkiniaiSkydeliai - Plastikiniai add
        virstinkiniaiPlastikiniaiSkydeliai.getInternalChildren().add(virstinkiniaiPlastikinioSkydelioIp40);
        virstinkiniaiPlastikiniaiSkydeliai.getInternalChildren().add(virstinkiniaiPlastikinioSkydelioIp65);

        //Apsvietimas - Lauko apsvietimas
        FilterableTreeItem<CategoryItem> gatviniaiSviestuvai = createFolder("Gatviniai šviestuvai");
        FilterableTreeItem<CategoryItem> laukoprozektoriai = createFolder("Lauko prožektoriai");
        FilterableTreeItem<CategoryItem> parkiniaisviestuvai = createFolder("Parkiniai šviestuvaii");
        FilterableTreeItem<CategoryItem> atramosgembes = createFolder("Atramos gembės");
        FilterableTreeItem<CategoryItem> priedailaukoApsvietimui = createFolder("Priedai lauko apšvietimui");

        //Apsvietimas - Vidaus apšvietimas
        FilterableTreeItem<CategoryItem> LEDPanelesA = createFolder("LED panelės 60x60");
        FilterableTreeItem<CategoryItem> LEDPaneles = createFolder("LED panelės");
        FilterableTreeItem<CategoryItem> downlight = createFolder("Downlight");
        FilterableTreeItem<CategoryItem> lubiniai = createFolder("Lubiniai IP65");
        FilterableTreeItem<CategoryItem> sieniniai = createFolder("Sieniniai");
        FilterableTreeItem<CategoryItem> pakabinami = createFolder("Pakabinami");
        FilterableTreeItem<CategoryItem> avarinisApsvietimas = createFolder("Avarinis apšvietimas");
        FilterableTreeItem<CategoryItem> highBay = createFolder("High Bay");

        //Apsvietimas - Lauko apsvietimas add
        laukoApsvietimas.getInternalChildren().add(gatviniaiSviestuvai);
        laukoApsvietimas.getInternalChildren().add(laukoprozektoriai);
        laukoApsvietimas.getInternalChildren().add(parkiniaisviestuvai);
        laukoApsvietimas.getInternalChildren().add(atramosgembes);
        laukoApsvietimas.getInternalChildren().add(priedailaukoApsvietimui);

        //Apsvietimas - Vidaus apšvietimas add
        vidausApsvietimas.getInternalChildren().add(LEDPanelesA);
        vidausApsvietimas.getInternalChildren().add(LEDPaneles);
        vidausApsvietimas.getInternalChildren().add(downlight);
        vidausApsvietimas.getInternalChildren().add(lubiniai);
        vidausApsvietimas.getInternalChildren().add(sieniniai);
        vidausApsvietimas.getInternalChildren().add(pakabinami);
        vidausApsvietimas.getInternalChildren().add(avarinisApsvietimas);
        vidausApsvietimas.getInternalChildren().add(highBay);

        //Apsvietimas - Lauko apsvietimas - Atramos gembes
        FilterableTreeItem<CategoryItem> atramos = createFolder("Atramos");
        FilterableTreeItem<CategoryItem> gembes = createFolder("Gembės");
        FilterableTreeItem<CategoryItem> pamatai = createFolder("Pamatai");

        //Apsvietimas - Lauko apsvietimas - Atramos gembes add
        atramosgembes.getInternalChildren().add(atramos);
        atramosgembes.getInternalChildren().add(gembes);
        atramosgembes.getInternalChildren().add(pamatai);

        //Apsvietimas - Vidaus apšvietimas - LED panelės
        FilterableTreeItem<CategoryItem> ipa = createFolder("IP20");
        FilterableTreeItem<CategoryItem> ipb = createFolder("IP44");

        //Apsvietimas - Vidaus apšvietimas - LED panelės add
        LEDPaneles.getInternalChildren().add(ipa);
        LEDPaneles.getInternalChildren().add(ipb);

        //Apsvietimas - Vidaus apšvietimas - Downlight
        FilterableTreeItem<CategoryItem> ipaa = createFolder("IP20");
        FilterableTreeItem<CategoryItem> ipbb = createFolder("IP44");

        //Apsvietimas - Vidaus apšvietimas - Downlight add
        downlight.getInternalChildren().add(ipaa);
        downlight.getInternalChildren().add(ipbb);

        //Apsvietimas - Vidaus apšvietimas - sieniniai add
        FilterableTreeItem<CategoryItem> ipaaa = createFolder("IP44");
        FilterableTreeItem<CategoryItem> ipbbb = createFolder("IP65");

        //Apsvietimas - Vidaus apšvietimas - sieniniai add
        downlight.getInternalChildren().add(ipaaa);
        downlight.getInternalChildren().add(ipbbb);

        //Įžeminimas ir žaibosauga - Įžeminimo strypai
        FilterableTreeItem<CategoryItem> variuotiStrypai = createFolder("Variuoti strypai");
        FilterableTreeItem<CategoryItem> cinkuotiStrypai = createFolder("Cinkuoti strypai");

        //Kabeliai - Jėgos kabeliai
        FilterableTreeItem<CategoryItem> nyyj = createFolder("NYY-J");
        FilterableTreeItem<CategoryItem> cykyj = createFolder("CYKY-J");
        FilterableTreeItem<CategoryItem> r2vSuXLPE = createFolder("R2v su XLPE");

        //Kabeliai - Jėgos kabeliai add
        jegosKabeliai.getInternalChildren().add(nyyj);
        jegosKabeliai.getInternalChildren().add(cykyj);
        jegosKabeliai.getInternalChildren().add(r2vSuXLPE);

        //Kabeliai - Behalogeniniai kabeliai
        FilterableTreeItem<CategoryItem> dca = createFolder("Dca");
        FilterableTreeItem<CategoryItem> cca = createFolder("Cca");
        FilterableTreeItem<CategoryItem> b2ca = createFolder("B2ca");
        FilterableTreeItem<CategoryItem> ekranuotasB2ca = createFolder("Ekranuotas B2ca");
        FilterableTreeItem<CategoryItem> nedegusKabelis = createFolder("Nedegus kabelis");

        //Kabeliai - Behalogeniniai kabeliai add
        behalogeniniaiKabeliai.getInternalChildren().add(dca);
        behalogeniniaiKabeliai.getInternalChildren().add(cca);
        behalogeniniaiKabeliai.getInternalChildren().add(b2ca);
        behalogeniniaiKabeliai.getInternalChildren().add(ekranuotasB2ca);
        behalogeniniaiKabeliai.getInternalChildren().add(nedegusKabelis);

        //Kabeliai - Internetinai kabeliai
        FilterableTreeItem<CategoryItem> cat5 = createFolder("Cat5");
        FilterableTreeItem<CategoryItem> cat6 = createFolder("Cat6");
        FilterableTreeItem<CategoryItem> cat6a = createFolder("Cat6a");

        //Kabeliai - Internetinai kabeliai add
        internetiniaiKabeliai.getInternalChildren().add(cat5);
        internetiniaiKabeliai.getInternalChildren().add(cat6);
        internetiniaiKabeliai.getInternalChildren().add(cat6a);

        //Kabeliai - Internetinai kabeliai - cat5
        FilterableTreeItem<CategoryItem> cat5utp = createFolder("UTP");
        FilterableTreeItem<CategoryItem> cat5ftp = createFolder("FTP");

        //Kabeliai - Internetinai kabeliai - cat6
        FilterableTreeItem<CategoryItem> cat6utp = createFolder("UTP");
        FilterableTreeItem<CategoryItem> cat6ftp = createFolder("FTP");

        //Kabeliai - Internetinai kabeliai - cat5 add
        cat5.getInternalChildren().add(cat5utp);
        cat5.getInternalChildren().add(cat5ftp);

        //Kabeliai - Internetinai kabeliai - cat6 add
        cat6.getInternalChildren().add(cat6utp);
        cat6.getInternalChildren().add(cat6ftp);

        //Vamzdžiai ir gofra - lauko
        FilterableTreeItem<CategoryItem> ape = createFolder("APE");
        FilterableTreeItem<CategoryItem> gofros = createFolder("Gofros");
        FilterableTreeItem<CategoryItem> prakalimoVamzdis = createFolder("Prakalimo vamzdis");
        FilterableTreeItem<CategoryItem> sudedamasVazdis = createFolder("Sudedamas vazdis");

        //Vamzdžiai ir gofra - lauko add
        lauko.getInternalChildren().add(ape);
        lauko.getInternalChildren().add(gofros);
        lauko.getInternalChildren().add(prakalimoVamzdis);
        lauko.getInternalChildren().add(sudedamasVazdis);

        //Vamzdžiai ir gofra - vidaus
        FilterableTreeItem<CategoryItem> vGofros = createFolder("Gofros");
        FilterableTreeItem<CategoryItem> behalogeninėsGofros = createFolder("Behalogeninės gofros");
        FilterableTreeItem<CategoryItem> vamzdžiai = createFolder("Vamzdžiai");
        FilterableTreeItem<CategoryItem> behalogeniniaiVamzdžiai = createFolder("Behalogeniniai vamzdžiai");
        FilterableTreeItem<CategoryItem> gofrosSuKabeliu = createFolder("Gofros su kabeliu");
        FilterableTreeItem<CategoryItem> gofrosSuLaidu = createFolder("Gofros su laidu");

        //Vamzdžiai ir gofra - vidaus add
        vidaus.getInternalChildren().add(vGofros);
        vidaus.getInternalChildren().add(behalogeninėsGofros);
        vidaus.getInternalChildren().add(vamzdžiai);
        vidaus.getInternalChildren().add(behalogeniniaiVamzdžiai);
        vidaus.getInternalChildren().add(gofrosSuKabeliu);
        vidaus.getInternalChildren().add(gofrosSuLaidu);

        //Vamzdžiai ir gofra - lauko
        FilterableTreeItem<CategoryItem> g450N = createFolder("g450N");
        FilterableTreeItem<CategoryItem> g750N = createFolder("g750N");
        FilterableTreeItem<CategoryItem> g1250N = createFolder("g1250N");

        //Vamzdžiai ir gofra - vidaus
        FilterableTreeItem<CategoryItem> vG320N = createFolder("320N");
        FilterableTreeItem<CategoryItem> vG750N = createFolder("750N");

        //Vamzdžiai ir gofra - lauko add
        gofros.getInternalChildren().add(g450N);
        gofros.getInternalChildren().add(g750N);
        gofros.getInternalChildren().add(g1250N);

        //Vamzdžiai ir gofra - vidaus add
        vGofros.getInternalChildren().add(vG320N);
        vGofros.getInternalChildren().add(vG750N);

        //Vamzdžiai ir gofra - vidaus - Behalogeninės gofros
        FilterableTreeItem<CategoryItem> bg320N = createFolder("320N");
        FilterableTreeItem<CategoryItem> bg750N = createFolder("750N");

        //Vamzdžiai ir gofra - vidaus - Behalogeninės gofros add
        behalogeninėsGofros.getInternalChildren().add(bg320N);
        behalogeninėsGofros.getInternalChildren().add(bg750N);


        //Instaliacinės prekės - Jungikliai ir kištukiniai lizdai
        FilterableTreeItem<CategoryItem> potinkiniaiJungikliai = createFolder("Potinkiniai jungikliai ir kištukiniai lizdai");
        FilterableTreeItem<CategoryItem> virstinkiniaiJungikliai = createFolder("Virštinkiniai jungikliai ir kištukiniai lizdai");
        FilterableTreeItem<CategoryItem> pramoniniaiLizdai = createFolder("Pramoniniai lizdai ir kištukai");

        //Instaliacinės prekės - Jungikliai ir kištukiniai lizdai add
        jungikliaiIrKistukiniaiLizdai.getInternalChildren().add(potinkiniaiJungikliai);
        jungikliaiIrKistukiniaiLizdai.getInternalChildren().add(virstinkiniaiJungikliai);
        jungikliaiIrKistukiniaiLizdai.getInternalChildren().add(pramoniniaiLizdai);

        //Instaliacinės prekės - Jungikliai ir kištukiniai lizdai - Virštinkiniai jungikliai ir kištukiniai lizdai
        FilterableTreeItem<CategoryItem> ip20 = createFolder("IP20");
        FilterableTreeItem<CategoryItem> ip44 = createFolder("IP44");

        //Instaliacinės prekės - Jungikliai ir kištukiniai lizdai - Virštinkiniai jungikliai ir kištukiniai lizdai add
        virstinkiniaiJungikliai.getInternalChildren().add(ip20);
        virstinkiniaiJungikliai.getInternalChildren().add(ip44);


        //Instaliacinės prekės - Potinkinės dėžutės
        FilterableTreeItem<CategoryItem> muroDezute = createFolder("Dėžutė į mūrą");
        FilterableTreeItem<CategoryItem> gipsoDezute = createFolder("Dėžutė į gipsą");

        //Instaliacinės prekės - Potinkinės dėžutės add
        potinkinesDezutes.getInternalChildren().add(muroDezute);
        potinkinesDezutes.getInternalChildren().add(gipsoDezute);

        //Instaliacinės prekės - Šildymo elementai
        FilterableTreeItem<CategoryItem> sildymoKilimėliai = createFolder("Šildymo kilimėliai");
        FilterableTreeItem<CategoryItem> sildymoKabeliai = createFolder("Šildymo kabeliai");
        FilterableTreeItem<CategoryItem> sildymoĮranga = createFolder("Šildymo įranga");

        //Instaliacinės prekės - Šildymo elementai add
        sildymoElementai.getInternalChildren().add(sildymoKilimėliai);
        sildymoElementai.getInternalChildren().add(sildymoKabeliai);
        sildymoElementai.getInternalChildren().add(sildymoĮranga);

        //Instaliacinės prekės - Grindinės dėžutės
        FilterableTreeItem<CategoryItem> gridninesPlastikinesDezutes = createFolder("Plastikinės");
        FilterableTreeItem<CategoryItem> grindinesMetalinesDezutes = createFolder("Metalinės");

        //Instaliacinės prekės - Grindinės dėžutės add
        grindinesDezutes.getInternalChildren().add(gridninesPlastikinesDezutes);
        grindinesDezutes.getInternalChildren().add(grindinesMetalinesDezutes);

        //Instaliacinės prekės - Judesio ir būvio jutikliai
        FilterableTreeItem<CategoryItem> judesio = createFolder("Judesio");
        FilterableTreeItem<CategoryItem> buvio = createFolder("Būvio");

        //Instaliacinės prekės - Judesio ir būvio jutikliai add
        judesioIrBuvioJutikliai.getInternalChildren().add(judesio);
        judesioIrBuvioJutikliai.getInternalChildren().add(buvio);

        //Instaliacinės prekės - Judesio ir būvio jutikliai Judesio
        FilterableTreeItem<CategoryItem> virstinkiniai = createFolder("Virštinkiniai");
        FilterableTreeItem<CategoryItem> potinkiniai = createFolder("Potinkiniai");

        //Instaliacinės prekės - Judesio ir būvio jutikliai Būvio
        FilterableTreeItem<CategoryItem> virstinkiniaiB = createFolder("Virštinkiniai");
        FilterableTreeItem<CategoryItem> potinkiniaiB = createFolder("Potinkiniai");

        //Instaliacinės prekės - Judesio ir būvio jutikliai Judesio add
        judesio.getInternalChildren().add(virstinkiniai);
        judesio.getInternalChildren().add(potinkiniai);
        //Instaliacinės prekės - Judesio ir būvio jutikliai Būvio add
        buvio.getInternalChildren().add(virstinkiniaiB);
        buvio.getInternalChildren().add(potinkiniaiB);

        //Įžeminimas ir žaibosauga - Įžeminimo strypai
        izeminimoStrypai.getInternalChildren().add(variuotiStrypai);
        izeminimoStrypai.getInternalChildren().add(cinkuotiStrypai);

        //Metalinės konstrukcijos - kopecios
        FilterableTreeItem<CategoryItem> kopKarstoCinkavimo = createFolder("Karšto cinkavimo");
        FilterableTreeItem<CategoryItem> kopSaltoCinkavimo = createFolder("Šalto cinkavimo");

        //Metalinės konstrukcijos - loveliai
        FilterableTreeItem<CategoryItem> lovKarstoCinkavimo = createFolder("Karšto cinkavimo");
        FilterableTreeItem<CategoryItem> lovSaltoCinkavimo = createFolder("Šalto cinkavimo");

        //Metalinės konstrukcijos - kopecios
        kopecios.getInternalChildren().add(kopKarstoCinkavimo);
        kopecios.getInternalChildren().add(kopSaltoCinkavimo);

        //Metalinės konstrukcijos - loveliai
        loveliai.getInternalChildren().add(lovKarstoCinkavimo);
        loveliai.getInternalChildren().add(lovSaltoCinkavimo);

        //Elektromechanika - Automatiniai jungikliai
        FilterableTreeItem<CategoryItem> e6kA = createFolder("6kA");
        FilterableTreeItem<CategoryItem> e10kA = createFolder("10kA");

        //Elektromechanika - Kirtikliai
        FilterableTreeItem<CategoryItem> moduliniai = createFolder("Moduliniai");
        FilterableTreeItem<CategoryItem> paneliniai = createFolder("Paneliniai");

        //Elektromechanika - Automatiniai jungikliai
        automatiniaiJungikliai.getInternalChildren().add(e6kA);
        automatiniaiJungikliai.getInternalChildren().add(e10kA);

        //Elektromechanika - Kirtikliai
        kirtikliai.getInternalChildren().add(moduliniai);
        kirtikliai.getInternalChildren().add(paneliniai);

        return root;
    }


}


