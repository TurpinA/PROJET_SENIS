package controler;

import model.Rayon;
import model.Role;
import model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlerGestionUtilisateur {

    public static final String LOGGER = "logger";
    @FXML   private Label utilisateurLabel;

    @FXML   private TableView<Utilisateur> tableView;
    @FXML   private TableColumn<Utilisateur,String> nomColumn;
    @FXML   private TableColumn<Utilisateur,String> prenomColumn;
    @FXML   private TableColumn<Rayon,String> rayonColumn;
    @FXML   private TableColumn<Utilisateur,String> roleColumn;

    private ArrayList<Utilisateur> listUtilisateur = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        utilisateurLabel.setText(main.Main.utilisateurConnecte.getPrenom() + " " + main.Main.utilisateurConnecte.getNom());

        nomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
        rayonColumn.setCellValueFactory(new PropertyValueFactory<Rayon, String>("rayon"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>("role"));

        setDataTableView();
        tableView.getItems().setAll(listUtilisateur);
    }

    public void setDataTableView() throws SQLException {
        listUtilisateur.clear();

        List<Utilisateur> listUtilisateurTemp = model.ExtractionData.rechercheAllUtilisateur();
        Iterator<Utilisateur> iterator = listUtilisateurTemp.iterator();

        while(iterator.hasNext()){
            Utilisateur utilisateur = iterator.next();
            Rayon rayon = model.ExtractionData.rechercheRayonParResponsable(utilisateur);

            utilisateur.setRayon(rayon);

            listUtilisateur.add(utilisateur);
        }
    }

    public void rayonButton(ActionEvent actionEvent) throws IOException, SQLException {
        if(main.Main.utilisateurConnecte.getRole().equals(Role.VENDEUR)) {
            main.Main.rayonAffiche = model.ExtractionData.rechercheRayonParResponsable(main.Main.utilisateurConnecte);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Gestion d'un rayon.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Affichage des rayons.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public void modifierUtilisateur() {

        if(tableView.getSelectionModel().getSelectedItem() != null) {
            ControlerModifierUtilisateur.utilisateurSelectione = tableView.getSelectionModel().getSelectedItem();

            Parent root;
            Stage stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ModifierUtilisateur.fxml"));
                root = fxmlLoader.load();
                stage.setTitle("Modifier un utilisateur");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
            }

            stage.setOnHiding(event -> {
                try {
                    setDataTableView();
                    tableView.getItems().setAll(listUtilisateur);
                } catch (SQLException e) {
                    Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
                }
            });
        }
    }

    public void supprimerUtilisateur() throws SQLException {
        Utilisateur utilisateurASupprimer = tableView.getSelectionModel().getSelectedItem();

        if(utilisateurASupprimer != null && utilisateurASupprimer.getId() != main.Main.utilisateurConnecte.getId()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation suppression");
            alert.setHeaderText("Etes-vous sur de vouloir supprimer l'utilisateur : " + utilisateurASupprimer.getPrenom() + " " + utilisateurASupprimer.getNom() + " ?");
            alert.setContentText("");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ActionBD.supprimerUtilisateur(utilisateurASupprimer);
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
            }
        }
    }

    public void ajouterUtilisateur() {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AjouterUtilisateur.fxml"));
            root = fxmlLoader.load();
            stage.setTitle("Ajouter un utilisateur");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        stage.setOnHiding(event -> {
            try {
                setDataTableView();
                tableView.getItems().setAll(listUtilisateur);
            } catch (SQLException e) {
                Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
            }
        });
    }

    public void labelClick() {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AffichageUtilisateur.fxml"));
            root = fxmlLoader.load();
            stage.setTitle(main.Main.utilisateurConnecte.getPrenom() + " " + main.Main.utilisateurConnecte.getNom());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
    }

    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ConnexionTest.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
