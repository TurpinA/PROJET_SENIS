package CONTROLLER;

import MODEL.Rayon;
import MODEL.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ControllerAfficherUtilisateur {

    @FXML   private Label nomPrenom;
    @FXML   private Label id;
    @FXML   private Label age;
    @FXML   private Label role;
    @FXML   private Label mail;

    @FXML
    public void initialize() throws SQLException {
        nomPrenom.setText(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());
        id.setText(String.valueOf(Main.utilisateurConnecte.getID()));
        age.setText(String.valueOf(Main.utilisateurConnecte.getAge()));
        role.setText(String.valueOf(Main.utilisateurConnecte.getRole()));
        mail.setText(Main.utilisateurConnecte.getMail());

    }
}
