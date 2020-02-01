package controler;

import model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerAjouterRayon {

    @FXML   private TextField nom;
    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;
    @FXML   private ComboBox<Utilisateur> utilisateurComboBox;

    @FXML
    public void initialize() throws SQLException {
        utilisateurComboBox.getItems().addAll(model.ExtractionData.rechercheAllUtilisateur());
        utilisateurComboBox.getItems().add(null);
    }

    public void annuler() {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public void ajouter() throws SQLException {
        boolean valide = true;

        if(!testRegexNom(nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(valide)
        {
            ActionBD.createRayon(nom.getText(),utilisateurComboBox.getValue());
            Stage stage = (Stage) ajouterButton.getScene().getWindow();
            stage.close();
        }
    }

    public boolean testRegexNom(String nom){
        return Pattern.matches("[A-Za-z 0-9\\-']++$", nom);
    }
}
