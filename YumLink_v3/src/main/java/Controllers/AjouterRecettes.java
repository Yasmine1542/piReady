package Controllers;

import Entities.Recettes;
import Services.RecettesS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utiles.DataBase;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AjouterRecettes implements Initializable {
    @FXML
    private TextField tf_Calorie;

    @FXML
    private TextField tf_desc;

    @FXML
    private TextField tf_image;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prot;

    @FXML
    private TextField tf_video;
    @FXML
    private Button Btn_update;

    @FXML
    private TableColumn<Recettes, Integer> Col_Cal;

    @FXML
    private TableColumn<Recettes, String> Col_Desc;

    @FXML
    private TableColumn<Recettes, String> Col_Img;

    @FXML
    private TableColumn<Recettes, String> Col_Nom;

    @FXML
    private TableColumn<Recettes, Integer> Col_Prot;

    @FXML
    private TableColumn<Recettes, String> Col_Vid;

    @FXML
    private TableColumn<Recettes, Integer> Col_id;

    @FXML
    private TableView<Recettes> Table;

    @FXML
    private Button btn_delete;
  Connection  conn= DataBase.getInstance().getConn() ;


    @FXML
    public ObservableList<Recettes> getRecettes () {
         ObservableList <Recettes> recettes = FXCollections.observableArrayList();
         String query = "SELECT * FROM recettes" ;
         Statement st ;
        ResultSet rs ;

          //  st = conn.prepareStatement(query);
            try{
                st = conn.createStatement();
                rs = st.executeQuery(query);
                Recettes recette ;


            while (rs.next())
            {
                Recettes r = new Recettes() ;
                r.setId_r(rs.getInt("id_r"));
                r.setNom(rs.getString("nom"));
                r.setDescription(rs.getString("description"));
                r.setCalorie(rs.getInt("calorie"));
                r.setProtein(rs.getInt("protein"));
                r.setImgSrc(rs.getString("imgSrc"));
                r.setVideoSrc(rs.getString("videoSrc"));
                recettes.add(r) ;
            }
               }
        catch (SQLException e){
            throw new RuntimeException(e) ;
        }
        return recettes ;
    }
    @FXML
    void Ajouter(ActionEvent event) {
        Recettes r = new Recettes(tf_nom.getText(), tf_desc.getText(), tf_image.getText(), tf_video.getText(), Integer.parseInt(tf_Calorie.getText()),Integer.parseInt(tf_prot.getText()))  ;
        RecettesS rs = new RecettesS() ;
        try {
            rs.ajouter(r);
            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
            alert.setContentText("ifadhalek omk");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR) ;
            alert.setContentText(e.getMessage());
            alert.show();
        }


    }

    public void showRecettes()  {

        ObservableList<Recettes> list = getRecettes();

        Col_id.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("id_r"));
        Col_Nom.setCellValueFactory(new PropertyValueFactory<Recettes , String>("nom"));
        Col_Desc.setCellValueFactory(new PropertyValueFactory<Recettes , String>("description"));
        Col_Img.setCellValueFactory(new PropertyValueFactory<Recettes , String>("imgSrc"));
        Col_Vid.setCellValueFactory(new PropertyValueFactory<Recettes , String>("videoSrc"));
        Col_Cal.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("calorie"));
        Col_Prot.setCellValueFactory(new PropertyValueFactory<Recettes , Integer>("protein"));



        Table.setItems(list);
    }


    private void handleRowClick(MouseEvent event) {
        if (!Table.getSelectionModel().isEmpty()) {
            Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();
            tf_nom.setText(selectedRecette.getNom());
            tf_desc.setText(selectedRecette.getDescription());
            tf_image.setText(selectedRecette.getImgSrc());
            tf_video.setText(selectedRecette.getVideoSrc());
            tf_Calorie.setText(Integer.toString(selectedRecette.getCalorie()));
            tf_prot.setText(Integer.toString(selectedRecette.getProtein()));

        }
    }

    @FXML
    void Delete(ActionEvent event) {
        Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            // Delete the selected item from the database
            try {
                RecettesS rs = new RecettesS();
                rs.supprimer(selectedRecette.getId_r());

                // Remove the item from the table's data
                Table.getItems().remove(selectedRecette);
            } catch (SQLException e) {
                // Handle any SQL exception that occurs during deletion
                e.printStackTrace();
            }
        } else {
            // If no item is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recette to delete.");
            alert.show();
        }
    }

    @FXML
    void Update(ActionEvent event) {
        Recettes selectedRecette = Table.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            // Update the selected item's properties with the values entered in the text fields
            selectedRecette.setNom(tf_nom.getText());
            selectedRecette.setDescription(tf_desc.getText());
            selectedRecette.setCalorie(Integer.parseInt(tf_Calorie.getText()));
            selectedRecette.setProtein(Integer.parseInt(tf_prot.getText()));
            selectedRecette.setImgSrc(tf_image.getText());
            selectedRecette.setVideoSrc(tf_video.getText());

            try {
                // Call the modifier method to update the record in the database
                RecettesS rs = new RecettesS();
                rs.modifier(selectedRecette);

                // Refresh the table view to reflect the changes
                showRecettes();
            } catch (SQLException e) {
                // Handle any SQL exception that occurs during update
                e.printStackTrace();
            }
        } else {
            // If no item is selected, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a recette to update.");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


            showRecettes();
        Table.setOnMouseClicked(this::handleRowClick);
    }
}
