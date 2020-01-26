package controler;

import model.Article;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class ControlerModifierArticle {

    public static final String FX_BORDER_COLOR_RED = "-fx-border-color: red ;";
    public static Article articleSelectione;

    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextArea description;

    @FXML   private Button modifierButton;
    @FXML   private Button annulerButton;

    @FXML
    public void initialize(){
        nom.setText(articleSelectione.getNom());
        reference.setText(articleSelectione.getReference());
        prix.setText(valueOf(articleSelectione.getPrix()));
        quantite.setText(valueOf(articleSelectione.getQuantite()));
        description.setText(articleSelectione.getDescription());

    }

    public void modifier() throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            Article article = new Article();
            article.setId(articleSelectione.getId());
            article.setNom(nom.getText());
            article.setReference(reference.getText());
            article.setPrix(Double.valueOf(prix.getText()));
            article.setQuantite(Integer.valueOf(quantite.getText()));
            article.setDescription(description.getText());
            article.setPhoto(null);
            article.setRayon(main.Main.rayonAffiche);
            ActionBD.modifierArticle(article);

            Stage stage = (Stage) annulerButton.getScene().getWindow();
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
        return Pattern.matches("[A-Za-z 0-9]++$", nom);
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
