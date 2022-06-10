package Main;

import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Reports.fxml"));
        root.setStyle("-fx-font-family: Times New Roman;");
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 1650, 900));
        primaryStage.show();
    }


    /**This is the main method. This is the first method that gets called when the application is run and it adds in sample data */
    public static void main(String[] args) {
        launch(args);
        JDBC.openConnection();

        JDBC.closeConnection();





    }

}