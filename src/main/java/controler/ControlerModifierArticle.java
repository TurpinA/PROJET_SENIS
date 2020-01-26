package controler;

import model.Article;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.sql.SQLException;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class ControlerModifierArticle {

    public static Article articleSelectione;

    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextArea description;

    @FXML   private Button modifierButton;
    @FXML   private Button annulerButton;

    @FXML
    public void initialize() throws SQLException {
        nom.setText(articleSelectione.getNom());
        reference.setText(articleSelectione.getReference());
        prix.setText(valueOf(articleSelectione.getPrix()));
        quantite.setText(valueOf(articleSelectione.getQuantite()));
        description.setText(articleSelectione.getDescription());

    }

    public void modifier(ActionEvent actionEvent) throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            Article article = new Article();
            article.setID(articleSelectione.getID());
            article.setNom(nom.getText());
            article.setReference(reference.getText());
            article.setPrix(Double.valueOf(prix.getText()));
            article.setQuantite(Integer.valueOf(quantite.getText()));
            article.setDescription(description.getText());
            article.setPhoto(null);
            article.setRayon(Main.rayonAffiche);
            controler.ActionBD.modifierArticle(article);

            Stage stage = (Stage) annulerButton.getScene().getWindow();
            stage.close();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public boolean testRegex(){
        boolean valide = true;

        if(!Pattern.matches("[A-Za-z 0-9]++$", nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(!Pattern.matches("[A-Za-z0-9]++$", reference.getText()) || reference.getText().isEmpty()) {
            reference.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            reference.setStyle("");

        if(!Pattern.matches("[0-9\\.]++$", prix.getText()) || prix.getText().isEmpty()) {
            prix.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else {
            try {
                Double.parseDouble(prix.getText());
                prix.setStyle("");
            }
            catch(NumberFormatException e)
            {
                prix.setStyle("-fx-border-color: red ;");
                valide = false;
            }
        }
        if(!Pattern.matches("[0-9]++$", quantite.getText()) || quantite.getText().isEmpty() || Integer.valueOf(quantite.getText())<0) {
            quantite.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            quantite.setStyle("");

        if(description.getText().isEmpty()) {
            description.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            description.setStyle("");

        return valide;
    }
}
