package sample.JPA;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.stage.Window;
import sample.utils.Constants;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static EntityManagerFactory factory = null;
    public static Scene loginScene;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (RuntimeException exception) {
                System.out.println("JPAUtil RuntimeException ");
                showPopupWindow("NEPAVYKO PRISIJUNGTI", "• Nepavyko prisijungti prie duomenų bazės\n• Patikrinkite ar turite interneto ryšį. \n• Priešingu atveju kreipkitės: į ECOSprendimai\n• Klaidos kodas: JPAUtil RuntimeException\n• Programos versija: " + Constants.PROGRAM_VERSION, "#f8d7da", "#842029", getScene());

            }
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    public static void setScene(Scene scene) {
        loginScene = scene;
    }
    public static Scene getScene() {
        return loginScene;
    }


    public static void showPopupWindow(String title, String information, String titleBackroundColor, String titleTextColor, Scene scene) {

        Window parent = scene.getWindow();
        javafx.stage.Popup popup = new Popup();

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-border-width: 1; -fx-border-color: #3268a8; -fx-effect: dropshadow(two-pass-box, #000000, 10, 0.0, 1.0, 1.0);");


        HBox hBox1 = new HBox();
        hBox1.setStyle("-fx-background-color: " + titleBackroundColor + ";");
        hBox1.setAlignment(Pos.CENTER);
        Label labelTitle = new Label();
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setMinSize(450, 60);
        labelTitle.setText(title + "\n");
        labelTitle.setStyle("-fx-font-size: 17; -fx-font-weight: bold");
        labelTitle.setTextFill(Paint.valueOf(titleTextColor));
        hBox1.getChildren().add(labelTitle);

        HBox hBox2 = new HBox();
        hBox2.setStyle("-fx-background-color: #FFFFFF;");
        hBox2.setAlignment(Pos.CENTER);
        Label stringInformation = new Label();
        stringInformation.setMinSize(230, 100);
        stringInformation.setStyle("-fx-font-size: 16;");
        stringInformation.setText(information);
        hBox2.getChildren().add(stringInformation);

        HBox hBox3 = new HBox();
        hBox3.setStyle("-fx-background-color: #d3d6d8;");
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setMinSize(200, 60);
        Button okButton = new Button();
        okButton.setText("Gerai" + "\n");
        okButton.setStyle("-fx-font-size: 14;");
        okButton.setOnAction(event -> popup.hide());
        hBox3.getChildren().add(okButton);

        root.getChildren().add(hBox1);
        root.getChildren().add(hBox2);
        root.getChildren().add(hBox3);

        popup.getContent().addAll(root);
        popup.show(parent);


    }
}

