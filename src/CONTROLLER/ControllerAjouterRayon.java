package CONTROLLER;

import MODEL.Role;
import MODEL.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerAjouterRayon {

    @FXML   private TextField nom;
    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;
    @FXML   private ComboBox<Utilisateur> utilisateurComboBox;

    @FXML
    public void initialize() throws SQLException {
        utilisateurComboBox.getItems().addAll(MODEL.ExtractionData.rechercheAllUtilisateur());
        utilisateurComboBox.getItems().add(null);
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public void ajouter(ActionEvent actionEvent) throws SQLException {
        boolean valide = true;

        if(!Pattern.matches("[A-Za-z0-9 \\-]++$", nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(valide)
        {
            CONTROLLER.ActionBD.Createrayon(nom.getText(),utilisateurComboBox.getValue());
            Stage stage = (Stage) ajouterButton.getScene().getWindow();
            stage.close();
        }
    }
}
