package Main;

import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginScreen.fxml"));
        root.setStyle("-fx-font-family: Times New Roman;");
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();
    }
    /**This is the main method. This is the first method that gets called when the application is run and it adds in sample data */
    public static void main(String[] args) {

        JDBC.openConnection();
          launch(args);
        JDBC.closeConnection();





    }

}