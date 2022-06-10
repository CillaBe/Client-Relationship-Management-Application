package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * * This class creates a CustomerList Class where we can store all customers and perform actions on them
 */
public class CustomerList {
    private static ObservableList <Customer> allCustomers = FXCollections.observableArrayList();
}
