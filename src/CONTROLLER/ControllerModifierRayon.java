package CONTROLLER;

import MODEL.Article;
import MODEL.Rayon;
import MODEL.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerModifierRayon {

    public static Rayon rayonSelectione;

    @FXML   private TextField nom;
    @FXML   private Button modifierButton;
    @FXML   private Button annulerButton;
    @FXML   private ComboBox<Utilisateur> utilisateurComboBox;

    @FXML
    public void initialize() throws SQLException {
        utilisateurComboBox.getItems().addAll(MODEL.ExtractionData.rechercheAllUtilisateur());
        nom.setText(rayonSelectione.getNom());
        utilisateurComboBox.setValue(rayonSelectione.getResponsable());
    }

    public void modifier(ActionEvent actionEvent) throws SQLException {
        boolean valide = true;

        if(!Pattern.matches("[A-Za-z0-9 \\-]++$", nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(utilisateurComboBox.getValue() == null){
            utilisateurComboBox.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            utilisateurComboBox.setStyle("");

        if(valide)
        {
            Rayon rayon = new Rayon();
            rayon.setNom(nom.getText());
            rayon.setID(rayonSelectione.getID());
            rayon.setMagasin(rayonSelectione.getMagasin());
            rayon.setResponsable(utilisateurComboBox.getValue());
            CONTROLLER.ActionBD.modifierRayon(rayon);
            Stage stage = (Stage) modifierButton.getScene().getWindow();
            stage.close();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }
}
