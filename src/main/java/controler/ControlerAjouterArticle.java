package controler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerAjouterArticle {

    public static final String FX_BORDER_COLOR_RED = "-fx-border-color: red ;";
    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextArea description;

    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;

    public void ajouter() throws SQLException {

        boolean champValide = testRegex();

        if(champValide) {
            ActionBD.createArticle(nom.getText(), reference.getText(), Double.valueOf(prix.getText()), Integer.valueOf(quantite.getText()), description.getText(), null, main.Main.rayonAffiche);

            Stage stage = (Stage) ajouterButton.getScene().getWindow();
            stage.close();
        }
    }

    public void annuler() {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public boolean testRegex(){
        boolean valide = true;

        if(!testRegexNom(nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            nom.setStyle("");

        if(!testRegexReference(reference.getText()) || reference.getText().isEmpty()) {
            reference.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            reference.setStyle("");

        if(!testRegexPrix(prix.getText()) || prix.getText().isEmpty()) {
            prix.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            prix.setStyle("");

        if(!testRegexQuantite(quantite.getText()) || quantite.getText().isEmpty()) {
            quantite.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            quantite.setStyle("");

        if(description.getText().isEmpty()) {
            description.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            description.setStyle("");

        return valide;
    }

    public boolean testRegexNom(String nom){
        return Pattern.matches("[A-Za-z 0-9\\-']++$", nom);
    }

    public boolean testRegexReference(String reference){
        return Pattern.matches("[A-Za-z0-9]++$", reference);
    }
    public boolean testRegexPrix(String prix){
        if(Pattern.matches("[0-9\\.]++$", prix)) {
            try {
                Double.parseDouble(prix);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

    public boolean testRegexQuantite(String quantite){
        if(Pattern.matches("[0-9]++$", quantite)) {
                return Integer.valueOf(quantite) >= 0;
        }
        return false;
    }
}
