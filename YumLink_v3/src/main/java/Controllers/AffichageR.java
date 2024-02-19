package Controllers;

import Entities.Recettes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import utiles.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AffichageR {

    @FXML
    private TableColumn<Recettes, Integer> Calorie_col;

    @FXML
    private TableColumn<Recettes, String> Nom_Col;

    @FXML
    private TableColumn<Recettes, Integer> Prot_col;

    @FXML
    private TableColumn<Recettes, String> desc_Col;

    @FXML
    private TableColumn<Recettes, Integer>   id_Col;

    @FXML
    private TableColumn<Recettes, String> imgS_col;

    @FXML
    private TableColumn<Recettes, String>vid_Col;

    @FXML
    public void initialize() {


    }



}
