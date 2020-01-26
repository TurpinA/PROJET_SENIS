package controler;

import model.*;
import main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ControlerListeRayons {

    @FXML private Label utilisateurLabel;

    @FXML   private TableView<Rayon> tableView;
    @FXML   private TableColumn<Rayon,String> nomColumn;
    @FXML   private TableColumn<Utilisateur,String> responsableColumn;

    @FXML
    public void initialize() throws SQLException {
        utilisateurLabel.setText(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());

        nomColumn.setCellValueFactory(new PropertyValueFactory<Rayon, String>("nom"));
        responsableColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("responsable"));

        tableView.getItems().setAll(model.ExtractionData.rechercheAllRayon());
    }
    public void gestionUtilisateurButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Gestion utilisateur.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ConnexionTest.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void labelClick(MouseEvent mouseEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AffichageUtilisateur.fxml"));
            root = fxmlLoader.load();
            stage.setTitle(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterRayon(ActionEvent actionEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AjouterRayon.fxml"));
            root = fxmlLoader.load();
            stage.setTitle("Ajouter un rayon");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        stage.setOnHiding(event -> {
            try {
                tableView.getItems().setAll(model.ExtractionData.rechercheAllRayon());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void modifierRayon(ActionEvent actionEvent) {

        if(tableView.getSelectionModel().getSelectedItem() != null) {
            ControlerModifierRayon.rayonSelectione = tableView.getSelectionModel().getSelectedItem();

            Parent root;
            Stage stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ModifierRayon.fxml"));
                root = fxmlLoader.load();
                stage.setTitle("Modifier un rayon");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.setOnHiding(event -> {
                try {
                    tableView.getItems().setAll(model.ExtractionData.rechercheAllRayon());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void supprimerRayon(ActionEvent actionEvent) throws SQLException {
        Rayon rayonASupprimer = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Etes-vous sur de vouloir supprimer le rayon : " + rayonASupprimer.getNom() + " ?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ActionBD.supprimerRayon(rayonASupprimer);
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
        }
    }

    public void accederRayon(ActionEvent actionEvent) throws SQLException, IOException {
        Main.rayonAffiche = model.ExtractionData.rechercheRayonParID(tableView.getSelectionModel().getSelectedItem().getID());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Gestion d'un rayon.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}