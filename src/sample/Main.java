package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.JPA.JPAUtil;
import sample.utils.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.LOGIN_VIEW_DIRECTORY_PATH));
        primaryStage.setTitle("Prisijungimas");

        Scene scene = new Scene(root, 500, 300);
        JPAUtil.setScene(scene);

        scene.getStylesheets().add(getClass().getClassLoader().getResource(Constants.CSS_DIRECTORY_PATH).toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}