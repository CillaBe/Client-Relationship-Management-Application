package Controllers;

import Helper.JDBC;
import Helper.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreen implements Initializable {
    @FXML
    private Label UserIDText;
    @FXML
    private Label PasswordText;
    @FXML
    private Button EnterText;
    @FXML
    private Label LocationDetectedText;
    @FXML
    private Button ExitButtonText;
    @FXML
    private Label Location;
    @FXML
    private TextField PasswordBox;
    @FXML
    private TextField locationBox;
    @FXML
    private Button ExitButton;
    @FXML
    private TextField UserIdBox;

    LocalDateTime CurrentTime;
    Timestamp timeinDB;
    Locale currentLocale;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId zoneId = ZoneId.systemDefault();
        Location.setText(String.valueOf(zoneId));
        currentLocale = Locale.getDefault();
        String language = currentLocale.getDisplayLanguage();
         if(language == "French") {
             UserIDText.setText("Identifiant d'utilisateur");
             PasswordText.setText("Mot de passe");
             LocationDetectedText.setText("Emplacement détecté");
             ExitButtonText.setText("Sortir");
         }

    }

    /**This method exits the program
     * @param actionEvent exits the program.
     */
    public void onClickExitLogin(ActionEvent actionEvent) {
        System.exit(0);
    }
    /**This checks if the username and password are correct
     * @param actionEvent if username and password aren't correct displays error, if correct, logs in to the program.
     */


    public void onClickEnterButton(ActionEvent actionEvent) throws SQLException, IOException {
        String  UserName = UserIdBox.getText();
        String Password = PasswordBox.getText();

        UserDatabase DBConnect = new UserDatabase();
        boolean validate = DBConnect.checkUser(UserName,Password);
        if (!validate)
        { Alert newAlert = new Alert(Alert.AlertType.ERROR);
            newAlert.setContentText("Error, Username and password are not correct, please try again");
            newAlert.showAndWait();
        } else {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setContentText("Login Succesful!");
            newAlert.showAndWait();
            Parent parent = FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
            Scene MainScene = new Scene(parent,1900,1000);
            Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            MainStage.setScene(MainScene);
            parent.setStyle("-fx-font-family: Times New Roman;");
            MainStage.setTitle("Appointments");
            MainStage.show();
        }

    }
    public void onClickLocationBox(ActionEvent actionEvent) {
    }
}
