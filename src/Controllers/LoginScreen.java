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


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Login Screen
 */
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
    @FXML
    private LocalDateTime CurrentTime;
    @FXML
    private Timestamp timeinDB;
    @FXML
    private Locale currentLocale;
    @FXML
    private String language;
    @FXML
    private String filename = "login_activity.txt.", login;
    @FXML
    private PrintWriter outputfile;
    @FXML
    private FileWriter newfile;


    /**
     * Initializes Screen
     * @param url intialzes screen
     * @param resourceBundle resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ZoneId zoneId = ZoneId.systemDefault();
        String id = zoneId.getId();
        Location.setText(id);
        System.out.println("Zone Id in login " + id + " ");
        currentLocale = Locale.getDefault();
        language = currentLocale.getLanguage();
        System.out.println(" Language is " + language + " ");
        if (language == "fr") {
            UserIDText.setText("Identifiant d'utilisateur");
            PasswordText.setText("Mot de passe");
            LocationDetectedText.setText("Emplacement détecté");
            ExitButtonText.setText("Sortir");
            EnterText.setText("Entrer");

        }

/**
 * This method creates a log for storing user login attempts
 */
    }

    public void createLog() throws IOException {
        newfile = new FileWriter(filename, true);
        outputfile = new PrintWriter(newfile);


    }

    /**
     * This method exits the program
     *
     * @param actionEvent exits the program.
     */
    public void onClickExitLogin(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * If username and password aren't correct displays error, if correct, logs in to the program.
     * @param actionEvent if username and password aren't correct displays error, if correct, logs in to the program.
     * @throws SQLException Exception
     * @throws IOException exception
     */


    public void onClickEnterButton(ActionEvent actionEvent) throws SQLException, IOException {
        createLog();
        String UserName = UserIdBox.getText();
        String Password = PasswordBox.getText();
        LocalDateTime now = LocalDateTime.now();

        UserDatabase DBConnect = new UserDatabase();
        boolean validate = DBConnect.checkUser(UserName, Password);
        if (!validate) {
            Alert newAlert = new Alert(Alert.AlertType.ERROR);
            if (language == "fr") {
                newAlert.setContentText("Erreur, le nom d'utilisateur et le mot de passe ne sont pas corrects, veuillez réessayer");
            }
            if ( language == "en") {
                newAlert.setContentText("Error, Username and password are not correct, please try again");
            }
            newAlert.showAndWait();
            login = (UserName + " " + now + " " + "Unsuccessful Login Attempt");
            outputfile.println(login);
            outputfile.close();
            System.out.print("File Written");


        } else {

            login = (UserName + " " + now + " " + "Successful Login Attempt");
            outputfile.println(login);
            outputfile.close();
            System.out.print("File Written");
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            if (language == "fr") {
                newAlert.setContentText("Connexion réussie!");
            }
            if ( language == "en") {

                newAlert.setContentText("Login Succesful!");
            }
            newAlert.showAndWait();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/AppointmentTable.fxml")));

            Scene MainScene = new Scene(parent, 3800, 1900);
            Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            MainStage.setScene(MainScene);
            parent.setStyle("-fx-font-family: Times New Roman;");
            MainStage.setTitle("Appointments");
            MainStage.show();
            System.out.println("Appointments launched successfully!");
        }
        /**This method was not used in this application, auto generated through SceneBuilder.
         @param actionEvent not used in this application, auto generated through SceneBuilder
         */
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onClickLocationBox(ActionEvent actionEvent) {
    }


}
