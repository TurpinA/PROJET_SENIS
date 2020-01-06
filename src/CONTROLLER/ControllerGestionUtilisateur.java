package CONTROLLER;

import MODEL.Rayon;
import MODEL.Role;
import MODEL.Utilisateur;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class ControllerGestionUtilisateur {

    @FXML   private Label utilisateurLabel;

    @FXML   private TableView<Utilisateur> tableView;
    @FXML   private TableColumn<Utilisateur,String> nomColumn;
    @FXML   private TableColumn<Utilisateur,String> prenomColumn;
    @FXML   private TableColumn<Rayon,String> rayonColumn;
    @FXML   private TableColumn<Utilisateur,String> roleColumn;

    private ArrayList<Utilisateur> listUtilisateur = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        utilisateurLabel.setText(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());

        nomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        rayonColumn.setCellValueFactory(new PropertyValueFactory<Rayon, String>("rayon"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("role"));

        setDataTableView();
        tableView.getItems().setAll(listUtilisateur);
    }

    public void setDataTableView() throws SQLException {
        listUtilisateur.clear();

        ArrayList<Utilisateur> listUtilisateurTemp = MODEL.ExtractionData.rechercheAllUtilisateur();
        Iterator<Utilisateur> iterator = listUtilisateurTemp.iterator();

        while(iterator.hasNext()){
            Utilisateur utilisateur = iterator.next();
            Rayon rayon = MODEL.ExtractionData.rechercheRayonParResponsable(utilisateur);

            utilisateur.setRayon(rayon);

            listUtilisateur.add(utilisateur);
        }
    }

    public void rayonButton(ActionEvent actionEvent) throws IOException, SQLException {
        if(Main.utilisateurConnecte.getRole().equals(Role.Vendeur)) {
            Main.rayonAffiche = MODEL.ExtractionData.rechercheRayonParResponsable(Main.utilisateurConnecte);

            FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/Gestion d'un rayon.fxml").toURI().toURL());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/Affichage des rayons.fxml").toURI().toURL());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public void modifierUtilisateur(ActionEvent actionEvent) {

        if(tableView.getSelectionModel().getSelectedItem() != null) {
            ControllerModifierUtilisateur.utilisateurSelectione = tableView.getSelectionModel().getSelectedItem();

            Parent root;
            Stage stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/ModifierUtilisateur.fxml").toURI().toURL());
                root = fxmlLoader.load();
                stage.setTitle("Modifier un utilisateur");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage.setOnHiding(event -> {
                try {
                    setDataTableView();
                    tableView.getItems().setAll(listUtilisateur);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void supprimerUtilisateur(ActionEvent actionEvent) throws SQLException {
        Utilisateur utilisateurASupprimer = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Etes-vous sur de vouloir supprimer l'utilisateur : " + utilisateurASupprimer.getPrenom() + " " + utilisateurASupprimer.getNom() + " ?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ActionBD.supprimerUtilisateur(utilisateurASupprimer);
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
        }
    }

    public void ajouterUtilisateur(ActionEvent actionEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/AjouterUtilisateur.fxml").toURI().toURL());
            root = fxmlLoader.load();
            stage.setTitle("Ajouter un utilisateur");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        stage.setOnHiding(event -> {
            try {
                setDataTableView();
                tableView.getItems().setAll(listUtilisateur);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void labelClick(MouseEvent mouseEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/AffichageUtilisateur.fxml").toURI().toURL());
            root = fxmlLoader.load();
            stage.setTitle(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/ConnexionTest.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
