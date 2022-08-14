package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.jfr.Frequency;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppt implements Initializable {
    public ComboBox ModifyApptCustComboBox;
    public ComboBox ModifyApptContactBox;
    public ComboBox ModifyapptUserComboBox;
    public ComboBox ModifyApptStartTime;

    public ComboBox ModifyApptEndTime;
    public DatePicker ModifyApptDate;
    @FXML
    private TextField ModifyApptDescription;
    @FXML
    private TextField ModifyApptID;
    @FXML
    public TextField ModifyApptUserID;
    @FXML
    private TextField ModifyApptTitle;
    @FXML
    private TextField ModifyApptCustID;
    @FXML
    private ChoiceBox ModifyApptContact;
    @FXML
    private TextField ModifyApptType;
    @FXML
    private TextField ModifyApptLocation;
    @FXML
    private DatePicker ModifyApptStartDate;
    @FXML
    private Button SaveModifyAppt;
    @FXML
    private Button ModifyAppointmentExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onModifyApptID(ActionEvent actionEvent) {
    }

    public void onModifyApptDescription(ActionEvent actionEvent) {
    }

    public void onModifyUserID(ActionEvent actionEvent) {
    }

    public void onModifyApptTitle(ActionEvent actionEvent) {
    }

    public void onModifyCustID(ActionEvent actionEvent) {
    }

    public void onModifyApptType(ActionEvent actionEvent) {
    }

    public void onModifyApptContact(MouseEvent mouseEvent) {
    }


    public void onModifyApptLocation(ActionEvent actionEvent) {
    }




    public void onModifyApptStartDate(ActionEvent actionEvent) {
    }







    public void onSaveModifyppt(ActionEvent actionEvent) {
    }

    public void onModifyAppointmentExit(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
        Scene MainScene = new Scene(parent,3800,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("All Appointments");
        MainStage.show();
        System.out.println("Logged out of Modfy Appointments tab");
    }
}
